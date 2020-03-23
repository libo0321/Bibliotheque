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
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.service.LivreService;
import com.intellij.librarymanager.service.impl.LivreServiceImpl;

public class LivreListServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService LivreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = LivreService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_list.jsp");
        dispatcher.forward(request, response);

    }

}