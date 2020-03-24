<!-- Utilisatiion uniquement de Tags JSP -->
<!-- Noter que si vous inspecter la page html du coté de votre navigateur (F12 -> inspecter)-->
<!-- Ce commentaire sera visible -->
<%-- Ce commentaire ne sera pas visible--%>

<%-- Déclaration des classes que nous alors utiliser ici
	Rappel: Une JSP après compilation n'est rien d'autre 
	qu'une Servlet qui écrit une page html --%>
	
<%-- Il se peut que le design ne soit pas convaincant sur Google Chrome --%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.app.model.Film" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%! private List<Film> films = new ArrayList<>();%>
<% films = (List) request.getAttribute("films"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des films</title>
</head>
<body>
	<div style="height: 20em; display: flex; flex-direction: column; justify-content: space-around; align-items: center;">
		<h1>Film Management</h1>
		
		<div style="width: 20%; display: flex; flex-direction: row; justify-content: space-around;">
			<h3><a href="new">Add New Films</a></h3>
			<h3><a href="">List All Films</a></h3>
		</div>	
	     <div>
			<table border="1">
				<tr>
					<th>Id</th>
					<th>Titre</th>
					<th>Réalisateur</th>
					<th>Options</th>
				</tr>
				<%-- Ici on ne peut pas utiliser les EL pour les films "f" car ils sont déclarés
					uniquement dans la scriptlet, et non via request.setAttribute() --%>
				<% if(!films.isEmpty()) {
					for(Film f : films) { %>
						<tr>
							<td><%= f.getId() %></td>
							<td><%= f.getTitre() %></td>
							<td><%= f.getRealisateur() %></td>
							<td><a href="edit?id=<%= f.getId() %>">edit</a> | <a href="delete?id=<%= f.getId() %>">delete</a></td>													
						</tr>
					<% }
				} %>
			</table>
		</div>
	</div>
</body>
</html>