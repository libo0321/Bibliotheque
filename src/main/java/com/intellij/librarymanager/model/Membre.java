package com.intellij.librarymanager.model;

public class Membre {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;
    public Membre(){
        this.abonnement=Abonnement.BASIC;
    }
    public Membre(Integer id, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement){
        this();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }
    public Membre(Integer id, String nom, String prenom, String adresse, String email, String telephone){
        this(id, nom, prenom, adresse, email, telephone, Abonnement.BASIC);    
    }
    
    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}
    public String getNom(){return nom;}
    public void setNom(String nom){this.nom = nom;}
    public String getPrenom(){return prenom;}
    public void setPrenom(String prenom){this.prenom = prenom;}
    public String getAdresse(){return adresse;}
    public void setAdresse(String adresse){this.adresse = adresse;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getTelephone(){return  telephone;}
    public void setTelephone(String telephone){this.telephone = telephone;}
    public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}
    public String getAbonnement() {
    	return abonnement.toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{"
                + "id " + id + ","
                + "nom " + nom + ","
                + "prenom " + prenom + ","
                + "adresse " + adresse + ","
                + "email " + email + ","
                + "telephone " + telephone + "}";
    }
}
