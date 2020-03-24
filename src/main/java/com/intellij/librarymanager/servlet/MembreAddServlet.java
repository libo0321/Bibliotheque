package com.intellij.librarymanager.servlet;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.service.MembreService;
import com.intellij.librarymanager.service.impl.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MembreAddServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        MembreService membreService = MembreServiceImpl.getInstance();
        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            //String abonnement = request
            membreService.create(nom,prenom,adresse,email,telephone);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/membre_add.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        doGet(request,response);
    }
}
