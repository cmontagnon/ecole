package perso.ecole;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddMenuServlet extends HttpServlet {
  private static final Logger log = Logger.getLogger(AddMenuServlet.class.getName());

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String day = req.getParameter("day");
    String menu = req.getParameter("menu");
    DayMenu greeting = new DayMenu(day, menu);

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(greeting);
    } finally {
      pm.close();
    }

    resp.sendRedirect("/menu.jsp");
  }
}
