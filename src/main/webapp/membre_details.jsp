<%@ page import="com.intellij.librarymanager.model.*" %>
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
        <h1 class="page-announce-text valign">Fiche membre</h1>
      </div>
      <div class="row">
      <div class="container">
		  <% Membre membre = (Membre) request.getAttribute("membre");%>
      <h5>Details du membre <%=membre.getId()%></h5>
        <div class="row">
	      <form action="/Biblioteque_war_exploded/membre_details?id=<%=membre.getId()%>" method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s4">
	            <input id="nom" type="text" value="<%=membre.getNom()%>" name="nom">
	            <label for="nom">Nom</label>
	          </div>
	          <div class="input-field col s4">
	            <input id="prenom" type="text" value="<%=membre.getPrenom()%>" name="prenom">
	            <label for="prenom">Pr?nom</label>
	          </div>
	          <div class="input-field col s4">
	            <select name="abonnement" class="browser-default">
	              <!-- TODO : faire en sorte que l'option correspondant ? l'abonnement du membre soit s?lectionn?e par d?faut -->
	              <!-- Pour cela, vous devez rajouter l'attribut selecter sur la balise <option> concern?e -->
	              <option value="BASIC" ${(membre.abonnement == "BASIC") ? " selected" : ""}>Abonnement BASIC</option>
	              <option value="PREMIUM" ${(membre.abonnement == "PREMIUM") ? " selected" : ""}>Abonnement PREMIUM</option>
	              <option value="VIP" ${(membre.abonnement == "VIP") ? " selected" : ""}>Abonnement VIP</option>
	            </select>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s12">
	            <input id="adresse" type="text" value="<%=membre.getAdresse()%>" name="adresse">
	            <label for="adresse">Adresse</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6">
	            <input id="email" type="email" value="<%=membre.getEmail()%>" name="email">
	            <label for="email">E-mail</label>
	          </div>
	          <div class="input-field col s6">
	            <input id="telephone" type="tel" value="<%=membre.getTelephone()%>" name="telephone">
	            <label for="telephone">T?l?phone</label>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	      
	      <form action="/Biblioteque_war_exploded/membre_delete" method="get" class="col s12">
	        <input type="hidden" value="<%=membre.getId()%>" name="id">
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit">Supprimer le membre
	            <i class="material-icons right">delete</i>
	          </button>
	        </div>
	      </form>
	    </div>
        <div class="row">
          <div class="col s12">
	        <table class="striped">
              <thead>
                <tr>
                  <th>Livre</th>
                  <th>Date d'emprunt</th>
                  <th>Retour</th>
                </tr>
              </thead>
              <tbody id="results">

                <c:forEach var="emprunt" items="${emprunts}">
                <tr>
                  <td>Pr?nom et nom du membre emprunteur</td>
                  <td>Date de l'emprunt</td>
                  <td>
                    <a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></a>
                  </td>
                </tr>
					<% List<Emprunt> emprunts1 = (List) request.getAttribute("emprunts");
						for(int i=0; i<emprunts1.size(); i++)
						{%>
					<tr>
						<td><%=emprunts1.get(i).getMembre().getNom()+" "+emprunts1.get(i).getMembre().getPrenom() %></td>
						<td><%=emprunts1.get(i).getDateEmprunt() %></td>

						<% if (emprunts1.get(i).getDateRetour()!=null) { %>
						<td><%=emprunts1.get(i).getDateRetour() %></td>
						<% } else { %>
						<td><a href="emprunt_return?id=<%=emprunts1.get(i).getId()%>">retourner</a></td>
						<% } %>
					</tr>
					<%}
					%>

                </c:forEach>



				<!-- TODO : parcourir la liste des emprunts en cours pour ce membre et les afficher selon la structure d'exemple ci-dessus -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
