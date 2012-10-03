package perso.ecole;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class DayMenu {

  @Persistent
  private String day;
  @Persistent
  private String menu;

  public DayMenu(String day, String menu) {
    super();
    this.day = day;
    this.menu = menu;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getMenu() {
    return menu;
  }

  public void setMenu(String menu) {
    this.menu = menu;
  }
}
