package com.intellij.librarymanager.service.impl;

import com.intellij.librarymanager.dao.LivreDao;
import com.intellij.librarymanager.dao.impl.LivreDaoImpl;
import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.exception.DaoException;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.LivreService;

import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl implements LivreService {
    //le dign pattern Singleton
    private static LivreServiceImpl instance = new LivreServiceImpl();
    private LivreServiceImpl(){}
    public static LivreService getInstance(){return instance;}

    @Override
    public List<Livre> getList(){
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try{
            livres = livreDao.getList();
        }catch (DaoException e1)
        {
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            for (int id=0;id<livreDao.getList().size();id++)
            {
                if(empruntService.isLivreDispo(id)==true)
                    livres.add(livreDao.getById(id));
            }

        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return livres;
    }

    @Override
    public Livre getById(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = livreDao.getById(id);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        return livre;
    }
    @Override
    public int create(String titre, String auteur, String isbn)  throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        try{
            if(titre==null)
                throw new ServiceException("Le titre est vide!");
            i = livreDao.create(titre,auteur,isbn);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }
        catch (ServiceException e2){
            System.out.println(e2.getMessage());
        }
        return i;
    }
    @Override
    public void update(Livre livre) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try{
            if(livre.getTitre()==null)
                throw new ServiceException("le titre est vide!");
            livreDao.update(livre);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }catch (ServiceException e2){
            System.out.println(e2.getMessage());
        }
        return;
    }
    @Override
    public void delete(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        try {
            livreDao.delete(id);
        }catch (DaoException e1){
            System.out.println(e1.getMessage());
        }catch (NumberFormatException e2){
            throw new ServiceException("Erreur lors du id"+id,e2);
        }
        return;
    }
    @Override
    public int count() throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i =-1;
        try{
            i = livreDao.count();
        }catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return i;
    }
}
