<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="perso.ecole.DayMenu" %>
<%@ page import="perso.ecole.DayUtil" %>
<%@ page import="perso.ecole.PMF" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>

<table id="table-3">
	<thead>
		<th><b>Date</b></th>
		<th><b>Arrondissement</b></th>
		<th><b>Quartier</b></th>
		<th><b>Prix</b></th>
		<th><b>Superficie</b></th>
		<th><b>Annonce</b></th>
		<th><b>reference</b></th>
	</thead>
	<tbody>
<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + DayMenu.class.getName() + " order by day";
    List<DayMenu> dayMenus = (List<DayMenu>) pm.newQuery(query).execute();
    String currentDay = DayUtil.getCurrentDayAsString();
    for (DayMenu dayMenu : dayMenus) {
    	if (dayMenu.getDay().equals(currentDay)) {
%>
<tr class="current-day">
<td><%= dayMenu.getDay()%></td>
<%   	
    	} else {
%>
<tr>
<td><%= dayMenu.getDay()%></td>
<%
    	}
%>
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
  </body>
</html>