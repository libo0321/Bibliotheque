<%@ page import="com.intellij.librarymanager.model.Emprunt" %>
<%@ page import="java.util.List" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Liste des emprunts</h1>
      </div>
      <div class="row">
        <div class="container">
	        <div class="col s12">
	          <table class="striped">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Membre emprunteur</th>
                        <th>Date d'emprunt</th>
                        <th>Retour</th>
                    </tr>
                </thead>
                <tbody id="results">
                
                    <tr>
                        <td>Titre du livre, <em>de Nom de l'auteur</em></td>
                        <td>Pr?nom et nom du membre emprunteur</td>
                        <td>Date de l'emprunt</td>
                        <td>
                            <a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></ion-icon></a>
                        </td>
                    </tr>

                    <% List<Emprunt> emprunts1 = (List) request.getAttribute("emprunts");
                        for(int i=0; i<emprunts1.size(); i++)
                        {%>
                    <tr>
                        <td><%=emprunts1.get(i).getLivre().getTitre() %></td>
                        <td><%=emprunts1.get(i).getMembre().getNom()+" "+emprunts1.get(i).getMembre().getPrenom() %></td>
                        <td><%=emprunts1.get(i).getDateEmprunt() %></td>

                        <% if (emprunts1.get(i).getDateRetour()!=null) { %>
                            <td><%=emprunts1.get(i).getDateRetour() %></td>
                        <% } else { %>
                            <td><a href="emprunt_return?id=+<%=emprunts1.get(i).getId()%>">retourner</a></td>
                        <% } %>
                    </tr>
                    <%}
                    %>
                </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
