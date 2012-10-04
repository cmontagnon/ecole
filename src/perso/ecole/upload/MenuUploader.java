package perso.ecole.upload;

import java.io.IOException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class MenuUploader {

  public static void main(String[] args) throws IOException {
    RemoteApiOptions options =
        new RemoteApiOptions().server("cyril-ecole.appspot.com", 443).credentials("cyrilmontagnon@gmail.com", "roumph");

    System.setProperty("proxySet", "true");
    System.setProperty("proxyHost", "proxyusers.intranet");
    System.setProperty("proxyPort", "8080");

    RemoteApiInstaller installer = new RemoteApiInstaller();
    installer.install(options);

    try {
      DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
      Entity menu = new Entity("DayMenu");
      menu.setProperty("day", "2012-09-06");
      menu.setProperty("menuEntry", "Carottes r�p�es");
      menu.setProperty("menuMainDish", "Carottes r�p�es");
      menu.setProperty("menuVegetables", "Carottes r�p�es");
      menu.setProperty("menuCheese", "Carottes r�p�es");
      menu.setProperty("menuDessert", "Carottes r�p�es");
      System.out.println("Key of new entity is " + ds.put(menu));
    } finally {
      installer.uninstall();
    }

  }
}
