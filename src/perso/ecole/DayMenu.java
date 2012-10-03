package perso.ecole;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class DayMenu {

  @Persistent
  private String day;
  @Persistent
  private String menuEntry;
  @Persistent
  private String menuMainDish;
  @Persistent
  private String menuVegetables;
  @Persistent
  private String menuCheese;
  @Persistent
  private String menuDessert;

  public DayMenu(String day, String menuEntry, String menuMainDish, String menuVegetables, String menuCheese,
      String menuDessert) {
    super();
    this.day = day;
    this.menuEntry = menuEntry;
    this.menuMainDish = menuMainDish;
    this.menuVegetables = menuVegetables;
    this.menuCheese = menuCheese;
    this.menuDessert = menuDessert;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getMenuEntry() {
    return menuEntry;
  }

  public void setMenuEntry(String menuEntry) {
    this.menuEntry = menuEntry;
  }

  public String getMenuMainDish() {
    return menuMainDish;
  }

  public void setMenuMainDish(String menuMainDish) {
    this.menuMainDish = menuMainDish;
  }

  public String getMenuDessert() {
    return menuDessert;
  }

  public void setMenuDessert(String menuDessert) {
    this.menuDessert = menuDessert;
  }

  public String getMenuVegetables() {
    return menuVegetables;
  }

  public void setMenuVegetables(String menuVegetables) {
    this.menuVegetables = menuVegetables;
  }

  public String getMenuCheese() {
    return menuCheese;
  }

  public void setMenuCheese(String menuCheese) {
    this.menuCheese = menuCheese;
  }
}
