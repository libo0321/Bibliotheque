package com.intellij.librarymanager.servlet;

import com.intellij.librarymanager.exception.ServiceException;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivreAddServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        LivreService livreService = LivreServiceImpl.getInstance();
        try{
            String titre = request.getParameter("titre");
            String auteur = request.getParameter("auteur");
            String isbn = request.getParameter("isbn");
            LocalDate localDate = LocalDate.now();
            livreService.create(titre,auteur,isbn);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/livre_add.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        doGet(request,response);
        response.sendRedirect("livre_list.jsp");
    }
}
