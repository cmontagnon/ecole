<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="perso.ecole.DayMenu" %>
<%@ page import="perso.ecole.PMF" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>
    <form action="/addMenu" method="post">
      <div>Day :<input name="day" /></div>
      <div>Menu entry :<input name="menuEntry" /></div>
      <div>Menu main dish :<input name="menuMainDish" /></div>
      <div>Menu vegetables :<input name="menuVegetables" /></div>
      <div>Menu cheese :<input name="menuCheese" /></div>
      <div>Menu dessert :<input name="menuDessert" /></div>
      <div><input type="submit" value="Post Menu" /></div>
    </form>
  </body>
</html>