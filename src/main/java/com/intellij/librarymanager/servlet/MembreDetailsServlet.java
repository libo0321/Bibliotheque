package com.intellij.librarymanager.servlet;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Abonnement;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.LivreService;
import com.intellij.librarymanager.service.MembreService;
import com.intellij.librarymanager.service.impl.EmpruntServiceImpl;
import com.intellij.librarymanager.service.impl.LivreServiceImpl;
import com.intellij.librarymanager.service.impl.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MembreDetailsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        Membre membre = new Membre();
        List<Emprunt> emprunts = new ArrayList<>();
        try{
            String id = request.getParameter("id");
            int ID = Integer.parseInt(id);
            membre = membreService.getById(ID);
            emprunts = empruntService.getListCurrentByMembre(ID);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        request.setAttribute("membre",membre);
        request.setAttribute("emprunts",emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/membre_details.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        MembreService membreService = MembreServiceImpl.getInstance();
        try{
            String ID = request.getParameter("id");
            int id = Integer.parseInt(ID);
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String adresse = request.getParameter("adresse");
            String email = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String abonnement = request.getParameter("abonnement");
            Abonnement abonnement1 = Abonnement.valueOf(abonnement);

            Membre membre = new Membre(id,nom,prenom,adresse,email,telephone,abonnement1);
            membreService.update(membre);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        response.sendRedirect("membre_list");
    }
}
