package com.intellij.librarymanager.service.impl;

import com.intellij.librarymanager.dao.LivreDao;
import com.intellij.librarymanager.dao.MembreDao;
import com.intellij.librarymanager.dao.impl.LivreDaoImpl;
import com.intellij.librarymanager.dao.impl.MembreDaoImpl;
import com.intellij.librarymanager.exception.DaoException;
import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Abonnement;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.MembreService;

import java.util.ArrayList;
import java.util.List;

public class MembreSviceImpl implements MembreService {
    private static MembreSviceImpl instance = new MembreSviceImpl();
    private MembreSviceImpl(){}
    public static MembreService getInstance(){
        return instance;
    }

    @Override
    public List<Membre> getList() {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try{
            membres = membreDao.getList();
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            for(int id=0;id<membreDao.getList().size();id++)
            {
                if(empruntService.isEmpruntPossible(membreDao.getById(id))==true)
                {
                    membres.add(membreDao.getById(id));
                }
            }
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return membres;
    }

    @Override
    public Membre getById(int id) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        Membre membre = new Membre();
        try {
            membre = membreDao.getById(id);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email,
                      String telephone) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        Membre membre = new Membre(nom, prenom, adresse, email, telephone,Abonnement.PREMIUM);
        int i =-1;
        try {
            if(membre.getNom()==null||membre.getPrenom()==null)
                throw new ServiceException("le nom ou le prenom ne peut pas vide.");
            i = membreDao.create(membre.getNom().toUpperCase(),membre.getPrenom(),membre.getAdresse(),
                                membre.getEmail(),membre.getTelephone(),Abonnement.PREMIUM);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }catch (ServiceException e2){
            System.out.println(e2.getMessage());
        }
        return i;
    }
    @Override
    public void update(Membre membre) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        Membre membre1 = new Membre(membre.getId(),membre.getNom().toUpperCase(),
                                membre.getPrenom(),membre.getAdresse(),membre.getEmail(),membre.getTelephone(),
                                membre.getAbonnement());
        try{
            if(membre.getNom()==null||membre.getPrenom()==null)
                throw new ServiceException("le nom ou le prenom ne peut pas vide.");
            membreDao.update(membre1);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }catch (ServiceException e2){
            System.out.println(e2.getMessage());
        }
        return;
    }
    @Override
    public void delete(int id) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            membreDao.delete(id);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        } catch (NumberFormatException e2){
            throw new ServiceException("Erreur lors du id"+id,e2);
        }
        return;
    }
    @Override
    public int count() throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int i = -1;
        try {
            i = membreDao.count();
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return i;
    }
}
