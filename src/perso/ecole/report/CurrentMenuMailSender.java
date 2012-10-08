package perso.ecole.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import perso.ecole.DayMenu;
import perso.ecole.DayUtil;
import perso.ecole.PMF;

import com.google.api.server.spi.IoUtil;

public class CurrentMenuMailSender extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final String MAIL_TITLE = "Damien : menu du jour";
  private static final Logger log = Logger.getLogger(CurrentMenuMailSender.class.getName());
  private List<String> addressees = Arrays.asList("cyrilmontagnon@gmail.com", "audewoehl@gmail.com",
      "jamontagnon@gmail.com", "michelemontagnon@yahoo.fr");

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doPost(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + DayMenu.class.getName();
    List<DayMenu> dayMenus = (List<DayMenu>) pm.newQuery(query).execute();
    String currentDay = DayUtil.getCurrentDayAsString();

    log.log(Level.INFO, "Trying to find a menu with day : " + currentDay);

    String msgBody = null;
    for (DayMenu dayMenu : dayMenus) {
      log.log(Level.INFO, "current menu day : " + dayMenu.getDay());
      if (dayMenu.getDay().equals(currentDay)) {
        log.log(Level.INFO, "Found menu of the day : " + dayMenu.getDay());
        msgBody = generateHTMLMail(msgBody, dayMenu);
      }
    }
    pm.close();

    if (msgBody != null) {
      try {
        log.log(Level.INFO, "Trying to send mail to :" + addressees);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("cyrilmontagnon@gmail.com"));

        InternetAddress[] addressesTo = new InternetAddress[addressees.size()];
        for (int i = 0; i < addressees.size(); i++) {
          addressesTo[i] = new InternetAddress(addressees.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, addressesTo);
        message.setSubject(MAIL_TITLE);

        message.setDataHandler(new DataHandler(new HTMLDataSource(msgBody)));
        message.setHeader("MIME-Version", "1.0");
        message.setHeader("X-Mailer", "My own custom mailer");

        message.saveChanges();
        Transport.send(message);
      } catch (MessagingException e) {
        log.log(Level.WARNING, e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /*
   * Inner class to act as a JAF datasource to send HTML e-mail content
   */
  static class HTMLDataSource implements DataSource {
    private String html;

    public HTMLDataSource(String htmlString) {
      html = htmlString;
    }

    // Return html string in an InputStream.
    // A new stream must be returned each time.
    public InputStream getInputStream() throws IOException {
      if (html == null)
        throw new IOException("Null HTML");
      return new ByteArrayInputStream(html.getBytes());
    }

    public OutputStream getOutputStream() throws IOException {
      throw new IOException("This DataHandler cannot write HTML");
    }

    public String getContentType() {
      return "text/html";
    }

    public String getName() {
      return "JAF text/html dataSource to send e-mail only";
    }
  }

  private static String generateHTMLMail(String msgBody, DayMenu dayMenu) throws FileNotFoundException, IOException {
    msgBody = "<html>";
    msgBody += "<head>";
    msgBody += "<style type=\"text/css\">";
    msgBody += IoUtil.readFile(new File("stylesheets/main.css"));
    msgBody += "</style>";
    msgBody += "</head>";
    msgBody += "<body>";
    msgBody += "<table id=\"table-3\">";
    msgBody += "<tbody>";
    msgBody += "<tr><td><b>Entr&eacute;e</b></td><td>" + dayMenu.getMenuEntry() + "</td></tr>";
    msgBody += "<tr><td><b>Repas</b></td><td>" + dayMenu.getMenuMainDish() + "</td></tr>";
    msgBody += "<tr><td><b>Accompagnement</b></td><td>" + dayMenu.getMenuVegetables() + "</td></tr>";
    msgBody += "<tr><td><b>Fromage/Yaourt</b></td><td>" + dayMenu.getMenuCheese() + "</td></tr>";
    msgBody += "<tr><td><b>Dessert</b></td><td>" + dayMenu.getMenuDessert() + "</td></tr>";
    msgBody += "</tbody>";
    msgBody += "</table>";
    msgBody += "</body>";
    msgBody += "</html>";
    return msgBody;
  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    System.out.println(generateHTMLMail("", new DayMenu("2012-10-04", "tomate", "poulet", "frites", "gouda",
        "babaorhum")));
  }
}
