package com.intellij.librarymanager.model;


import java.time.LocalDate;

public class Emprunt {
    private Integer id;
    private Integer idMembre;
    private Integer idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(){}
    public Emprunt(Integer idMembre, Integer idLivre,
                    LocalDate dateEmprunt, LocalDate dateRetour)
    {
        this();
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Emprunt(Integer id, Integer idMembre, Integer idLivre,
                   LocalDate dateEmprunt, LocalDate dateRetour)
    {
        this(idMembre, idLivre, dateEmprunt, dateRetour);
        this.id = id;
    }

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}
    public Integer getIdMembre(){return idMembre;}
    public void setIdMembre(Integer idMembre){this.idMembre = idMembre;}
    public Integer getIdLivre(){return idLivre;}
    public void setIdLivre(Integer idLivre){this.idLivre = idLivre;}
    public LocalDate getDateEmprunt(){return dateEmprunt;}
    public void setDateEmprunt(LocalDate dateEmprunt){this.dateEmprunt = dateEmprunt;}
    public LocalDate getDateRetour(){return dateRetour;}
    public void setDateRetour(LocalDate dateRetour){this.dateRetour = dateRetour;}

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"{"
                +"id: "+id+","
                +"idMembre: " +idMembre+","
                +"idLibre: "+idLivre+","
                +"dateEmprunt: "+dateEmprunt+","
                +"dateRetour: "+dateRetour+"}";
    }
}
