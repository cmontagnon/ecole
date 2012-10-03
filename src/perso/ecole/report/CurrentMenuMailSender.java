package perso.ecole.report;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import perso.ecole.PMF;

public class CurrentMenuMailSender extends HttpServlet {

  private static final Logger log = Logger.getLogger(CurrentMenuMailSender.class.getName());

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
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
    String currentDay =
        cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    String msgBody = "No menu found";
    for (DayMenu dayMenu : dayMenus) {
      if (dayMenu.getDay().equals(currentDay)) {
        msgBody += "<html>";
        msgBody += "<body>";
        msgBody += "<table border=\"1px solid #DFDFDF\">";
        msgBody += "<thead>";
        msgBody += "<th>Day</th><th>Entry</th><th>Main dish</th><th>Vegetables</th><th>Cheese</th><th>Dessert</th>";
        msgBody += "</thead>";
        msgBody += "<tbody>";
        msgBody += "<tr>" + dayMenu.getMenuEntry() + "</tr>";
        msgBody += "<tr>" + dayMenu.getMenuMainDish() + "</tr>";
        msgBody += "<tr>" + dayMenu.getMenuVegetables() + "</tr>";
        msgBody += "<tr>" + dayMenu.getMenuCheese() + "</tr>";
        msgBody += "<tr>" + dayMenu.getMenuDessert() + "</tr>";
        msgBody += "</tbody>";
        msgBody += "</table>";
        msgBody += "</body>";
        msgBody += "</html>";
      }
    }
    pm.close();

    try {
      log.log(Level.INFO, "Trying to send mail to ...");
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("cyrilmontagnon@gmail.com"));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress("cyril.montagnon@natixis.com"));
      msg.setSubject("Damien : menu du jour");
      msg.setText(msgBody);
      Transport.send(msg);
    } catch (MessagingException e) {
      log.log(Level.WARNING, e.getMessage());
      e.printStackTrace();
    }

  }
}
