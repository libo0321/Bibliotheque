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
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.impl.EmpruntServiceImpl;

public class EmpruntListServlet extends HttpServlet{

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService EmpruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = EmpruntService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
        dispatcher.forward(request, response);

    }

}