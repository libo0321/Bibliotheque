<!-- Utilisatiion de Tags JSTL -->
<!-- Noter que si vous inspecter la page html du coté de votre navigateur (F12 -> inspecter)-->
<!-- Ce commentaire sera visible -->
<%-- Ce commentaire ne sera pas visible--%>

<%-- Ici, généralement pas besoin d'import. Les objets qui ont été set dans la Servlet (request.setAttribute)
	sont directement visible via les EL (Expression Languages) et cela suffit généralemment--%>
	
<%-- Il se peut que le design ne soit pas convaincant sur Google Chrome --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des films version JSTL</title>
</head>
<body>
	<div style="height: 20em; display: flex; flex-direction: column; justify-content: space-around; align-items: center;">
		<h1>Film Management (Version JSTL)</h1>
		
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
				<c:if test="${! empty films }">
					<c:forEach var="f" items="${films}">
						<tr>
							<td><c:out value="${f.id}" /></td>
							<td><c:out value="${f.titre}" /></td>
							<td><c:out value="${f.realisateur}" /></td>
							<td><a href="edit?id=${f.id}">edit</a> | <a href="delete?id=${f.id}">delete</a></td>													
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</div>
</body>
</html>