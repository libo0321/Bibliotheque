package com.intellij.librarymanager.model;


import java.time.LocalDate;

public class Emprunt {
    private Integer id;
    private Membre membre;
    private Livre livre;
    private Integer idMembre;
    private Integer idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(){}

    public Emprunt(Integer idMembre, Integer idLivre,
                    LocalDate dateEmprunt)
    {
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
    }

    public Emprunt( Integer id, Integer idMembre, Integer idLivre,
                    LocalDate dateEmprunt)
    {
        this(idMembre, idLivre, dateEmprunt);
        this.id = id;
    }

    public Emprunt( Integer id, Integer idMembre, Integer idLivre,
                   LocalDate dateEmprunt, LocalDate dateRetour)
    {
        this(id, idMembre, idLivre, dateEmprunt);
        this.dateRetour = dateRetour;
    }

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}
    public Membre getMembre(){return membre;}
    public Livre getLivre(){return livre;}
    public void setMembre(Membre membre){this.membre = membre;}
    public void setLivre(Livre livre){this.livre = livre;}
    public Integer getIdMembre(){return idMembre;}
    public void setIdMembre(Integer idMembre){this.idMembre = idMembre;}
    public Integer getIdLivre(){return idLivre;}
    public void setIdLivre(Integer idLivre){this.idLivre = idLivre;}
    public LocalDate getDateEmprunt(){return dateEmprunt;}
    public void setDateEmprunt(LocalDate dateEmprunt){this.dateEmprunt = dateEmprunt;}
    public LocalDate getDateRetour(){return dateRetour;}
    public void setDateRetour(LocalDate dateRetour){this.dateRetour = dateRetour;}

    public void setMembre(int id, String nom, String prenom, String adresse, String email, String telephone, String abonne){
        Abonnement abonnement = Abonnement.valueOf(abonne);
        Membre membre = new Membre(id, nom, prenom, adresse, email, telephone, abonnement);
        this.membre = membre;
    }

    public void setLivre(int id, String titre, String auteur, String isbn){
        Livre livre = new Livre(id, titre, auteur, isbn);
        this.livre = livre;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"{"
                +"id: "+id+","
                +"Membre: ["+membre+"],"
                +"Livre: ["+livre+"],"
                +"idMembre: " +idMembre+","
                +"idLivre: "+idLivre+","
                +"dateEmprunt: "+dateEmprunt+","
                +"dateRetour: "+dateRetour+"}";
    }
}
