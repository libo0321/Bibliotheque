package com.intellij.librarymanager.servlet;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.LivreService;
import com.intellij.librarymanager.service.impl.EmpruntServiceImpl;
import com.intellij.librarymanager.service.impl.LivreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LivreDetailsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        Livre livre = new Livre();
        List<Emprunt> emprunts = new ArrayList<>();
        try{
            String id = request.getParameter("id");
            int ID = Integer.parseInt(id);
            System.out.println(ID);
            livre = livreService.getById(ID);
            emprunts = empruntService.getListCurrentByLivre(ID);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        request.setAttribute("livre",livre);
        request.setAttribute("emprunts",emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/livre_details.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();
        try{
            String titre = request.getParameter("titre");
            String auteur = request.getParameter("auteur");
            String isbn = request.getParameter("isbn");
            Livre livre = new Livre(titre,auteur,isbn);
            livreService.update(livre);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        response.sendRedirect("livre_list");
    }
}
