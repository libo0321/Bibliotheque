package com.intellij.librarymanager.servlet;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Emprunt;
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

public class EmpruntReturnServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt>emprunts = new ArrayList<>();
        try{
            emprunts = empruntService.getListCurrent();
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/emprunt_return.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{

        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try{
            String id = request.getParameter("id");
            int Id = Integer.parseInt(id);
            empruntService.returnBook(Id);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        response.sendRedirect("emprunt_list");
    }
}
