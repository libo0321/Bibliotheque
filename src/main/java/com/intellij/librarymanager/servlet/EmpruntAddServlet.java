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
import javax.sound.midi.MidiMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntAddServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        LivreService livreService = LivreServiceImpl.getInstance();
        MembreService membreService = MembreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        List<Membre> membres = new ArrayList<>();
        List<Livre> livres1 = new ArrayList<>();
        List<Membre> membres1 = new ArrayList<>();
        try {
            livres = livreService.getListDispo();
            membres = membreService.getListMembreEmpruntPossible();
            for(int i=0;i<livres.size();i++){
                if(livres.get(i).getId()!=null)
                {
                    livres1.add(livres.get(i));
                }
            }
            for(int i=0;i<membres.size();i++)
            {
                if(membres.get(i).getId()!=null)
                {
                    membres1.add(membres.get(i));
                }
            }

        } catch (ServiceException e1) {
            e1.printStackTrace();
        }

        request.setAttribute("livres", livres1);
        request.setAttribute("membres", membres1);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/emprunt_add.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try{
            String idLivre = request.getParameter("idLivre");
            String idMembre = request.getParameter("idMembre");
            int idlivre = Integer.parseInt(idLivre);
            int idmembre = Integer.parseInt(idMembre);
            LocalDate localDate = LocalDate.now();
            empruntService.create(idmembre,idlivre,localDate);
        }catch (ServiceException e1){
            e1.printStackTrace();
        }
        response.sendRedirect("emprunt_list");
    }

}
