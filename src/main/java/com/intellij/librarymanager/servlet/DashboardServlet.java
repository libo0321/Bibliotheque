package com.intellij.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.*;
import com.intellij.librarymanager.service.*;
import com.intellij.librarymanager.service.impl.*;

public class DashboardServlet extends HttpServlet{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        LivreService livreService = LivreServiceImpl.getInstance();
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        int numberOfLivre = -1,numberOfMember = -1,numberOfEmprunt = -1;
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            numberOfLivre = livreService.count();
            numberOfMember = membreService.count();
            numberOfEmprunt = empruntService.count();
            emprunts = empruntService.getList();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        request.setAttribute("numberOfLivre", numberOfLivre);
        request.setAttribute("numberOfmember", numberOfMember);
        request.setAttribute("numberOfEmprunt", numberOfEmprunt);
        request.setAttribute("emprunts", emprunts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }

}
