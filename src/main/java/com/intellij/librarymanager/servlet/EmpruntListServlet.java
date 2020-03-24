package com.intellij.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.impl.EmpruntServiceImpl;

public class EmpruntListServlet extends HttpServlet{

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService EmpruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = EmpruntService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/emprunt_list.jsp");
        request.setAttribute("emprunts", emprunts);
        dispatcher.forward(request, response);
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException{
        doGet(request,response);
    }

}