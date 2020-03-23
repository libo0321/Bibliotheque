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
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.service.MembreService;
import com.intellij.librarymanager.service.impl.MembreSviceImpl;

public class MembreListServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService MembreService = MembreSviceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = MembreService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("membres", membres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_list.jsp");
        dispatcher.forward(request, response);

    }

}