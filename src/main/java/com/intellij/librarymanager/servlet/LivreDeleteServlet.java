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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivreDeleteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre>livres = new ArrayList<>();
        List<Emprunt>emprunts = new ArrayList<>();
        try{
            emprunts =  empruntService.getListCurrent();
            for(int i = 0;i<emprunts.size();i++)
            {
                Livre livre = livreService.getById(emprunts.get(i).getIdLivre());
                livres.add(livre);
            }
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        request.setAttribute("livre",livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/livre_delete.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();
        try{
            String id = request.getParameter("id");
            int ID = Integer.parseInt(id);
            livreService.delete(ID);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        response.sendRedirect("livre_list");
    }
}
