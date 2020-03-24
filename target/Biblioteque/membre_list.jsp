<%@ page import="com.intellij.librarymanager.model.Membre" %>
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
        <h1 class="page-announce-text valign">Liste des membres</h1>
      </div>
      <div class="row">
	    <div class="col s12">
	      <table class="striped no-padding">
            <thead>
              <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th class="hide-on-small-only">Adresse</th>
                <th class="hide-on-small-only">E-mail</th>
                <th class="hide-on-small-only">Téléphone</th>
                <th>Détails</th>
              </tr>
            <tbody>
            </thead>

              <tr>
                <td>Nom du membre</td>
                <td>Prenom du membre</td>
                <td class="hide-on-small-only">Adresse du membre</td>
                <td class="hide-on-small-only">E-mail du membre</td>
                <td class="hide-on-small-only">Telephone du membre</td>
                <td class="center"><a href="membre_details?id=idDuMembre"><ion-icon class="details" name="information-circle-outline"></ion-icon></a></td>
              </tr>
              <% List<Membre> membres = (List) request.getAttribute("membres");
                for(int i=0; i<membres.size(); i++)
                {%>
              <tr>
                <td><%=membres.get(i).getNom() %></td>
                <td><%=membres.get(i).getPrenom() %></td>
                <td><%=membres.get(i).getAdresse() %></td>
                <td><%=membres.get(i).getEmail() %></td>
                <td><%=membres.get(i).getTelephone() %></td>
                <td class="center"><a href="membre_details?id=idDuMembre">detail</a></td>
              </tr>
              <%}
              %>

            </tbody>
          </table>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
