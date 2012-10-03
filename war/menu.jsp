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

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with greetings you post.</p>
<%
    }
%>

<table id="table-3">
	<thead>
		<th>Day</th>
		<th>Entry</th>
		<th>Main dish</th>
		<th>Vegetables</th>
		<th>Cheese</th>
		<th>Dessert</th>
	</thead>
	<tbody>
<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + DayMenu.class.getName();
    List<DayMenu> dayMenus = (List<DayMenu>) pm.newQuery(query).execute();
    for (DayMenu dayMenu : dayMenus) {
%>
<tr>
<td><%= dayMenu.getDay()%></td>
<td><%= dayMenu.getMenuEntry()%></td>
<td><%= dayMenu.getMenuMainDish()%></td>
<td><%= dayMenu.getMenuVegetables()%></td>
<td><%= dayMenu.getMenuCheese()%></td>
<td><%= dayMenu.getMenuDessert()%></td>
</tr>
<%
	
    }
    pm.close();
%>
	</tbody>
</table>

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