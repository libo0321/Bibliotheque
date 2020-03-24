<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
    <div align="center">
    	<form action="insert" method="post">
			<table border="1">
				<tr>
					<th>Titre :</th>
					<td><input type="text" name="titre" size="45" value="" />
					</td>
				</tr>
				<tr>
					<th>Réalisateur :</th>
					<td><input type="text" name="realisateur" size="45" value="" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="Enregistrer" />
						<%-- A noter qu'intéger du code CSS comme ici pour la balise <a> (dans la propriété "style")
							est une mauvaise pratique. On préfèrera donner des propriétés html "class" et importer
							un fichier css dans cette page--%>
						<a href="list" 
							style="align-items: flex-start; text-align: center; cursor: default;
    								color: buttontext; background-color: buttonface; box-sizing: border-box;
    								padding: 2px 6px 3px; border-width: 2px; border-style: outset; border-color: buttonface; 
    								border-image: initial; text-decoration: none;">
							Retour
						</a>
					</td>
				</tr>
			</table>
		</form>
    </div>   
</body>
</html>