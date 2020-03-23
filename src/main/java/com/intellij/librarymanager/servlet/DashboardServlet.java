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

    private String message;

    public void init() throws ServletException
    {
        // 执行必需的初始化
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {



    }

}
