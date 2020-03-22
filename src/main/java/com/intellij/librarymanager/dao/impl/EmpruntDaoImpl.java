package com.intellij.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.intellij.librarymanager.persistence.ConnectionManager;
import com.intellij.librarymanager.exception.DaoException;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.dao.EmpruntDao;

public class EmpruntDaoImpl implements EmpruntDao{
	
	private static EmpruntDaoImpl instance;
	private EmpruntDaoImpl() { }	
	public static EmpruntDao getInstance() {
		if(instance == null) {

			instance = new EmpruntDaoImpl();
		}
		return instance;
	}

	private static final String CREATE_QUERY = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, " +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, " +
			"dateRetour " +
			"FROM emprunt AS e " +
			"INNER JOIN membre ON membre.id = e.idMembre " +
			"INNER JOIN livre ON livre.id = e.idLivre " +
			"WHERE e.id = ?;";

	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt," +
			"dateRetour " +
			"FROM emprunt AS e " +
			"INNER JOIN membre ON membre.id = e.idMembre " +
			"INNER JOIN livre ON livre.id = e.idLivre " +
			"ORDER BY dateRetour DESC;";

	private static final String SELECT_NON_RENDU_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt," +
			"dateRetour " +
			"FROM emprunt AS e " +
			"INNER JOIN membre ON membre.id = e.idMembre " +
			"INNER JOIN livre ON livre.id = e.idLivre " +
			"WHERE dateRetour IS NULL;";

	private static final String SELECT_NON_RENDU_BY_MEMBRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\n" +
			"dateRetour " +
			"FROM emprunt AS e " +
			"INNER JOIN membre ON membre.id = e.idMembre " +
			"INNER JOIN livre ON livre.id = e.idLivre " +
			"WHERE dateRetour IS NULL AND membre.id = ?;";

	private static final String SELECT_NON_RENDU_BY_LIVRE_QUERY= "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt," +
			"dateRetour " +
			"FROM emprunt AS e " +
			"INNER JOIN membre ON membre.id = e.idMembre " +
			"INNER JOIN livre ON livre.id = e.idLivre " +
			"WHERE dateRetour IS NULL AND livre.id = ?;";

	private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre=?, idLivre=?, dateEmprunt=?,dateRetour=? WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";


	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			ResultSet res = preparedStatement.executeQuery();

			while(res.next()) {
				Emprunt f;
				String retour = res.getString("dateRetour");
				if (retour!=null){
					f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
							LocalDate.parse(res.getString("dateEmprunt")), LocalDate.parse(retour));
				}
				else
					f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")));
				f.setMembre(res.getInt("idMembre"),
						res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
						res.getString("email"), res.getString("telephone"), res.getString("abonnement"));
				f.setLivre(res.getInt("idLivre"),
						res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
				emprunts.add(f);

			}
			System.out.println("GET all emprunts : " );
			for(int i=0; i<emprunts.size(); i++)
			{
				System.out.println(emprunts.get(i));
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_QUERY);
			ResultSet res = preparedStatement.executeQuery();

			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")));
				f.setMembre(res.getInt("idMembre"),
						res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
						res.getString("email"), res.getString("telephone"), res.getString("abonnement"));
				f.setLivre(res.getInt("idLivre"),
						res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
				emprunts.add(f);

			}
			System.out.println("GET all emprunts non rendu : " );
			for(int    i=0; i<emprunts.size(); i++)
			{
				System.out.println(emprunts.get(i));
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des non rendu", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_BY_MEMBRE_QUERY);
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")));
				f.setMembre(res.getInt("idMembre"),
						res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
						res.getString("email"), res.getString("telephone"), res.getString("abonnement"));
				f.setLivre(res.getInt("idLivre"),
						res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
				emprunts.add(f);

			}
			System.out.println("GET all emprunts non rendu by member : " + idMembre);
			for(int    i=0; i<emprunts.size(); i++)
			{
				System.out.println(emprunts.get(i));
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des non rendu", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_BY_LIVRE_QUERY);

			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")));
				f.setMembre(res.getInt("idMembre"),
						res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
						res.getString("email"), res.getString("telephone"), res.getString("abonnement"));
				f.setLivre(res.getInt("idLivre"),
						res.getString("titre"),res.getString("auteur"),res.getString("isbn"));
				emprunts.add(f);
			}
			System.out.println("GET all emprunts non rendu by book : " +idLivre);
			for(int    i=0; i<emprunts.size(); i++)
			{
				System.out.println(emprunts.get(i));
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des non rendu", e);
		}
		return emprunts;
	}
	
	@Override
	public Emprunt getById(int id) throws DaoException{
		Emprunt emprunt = new Emprunt();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				emprunt.setId(res.getInt("id"));
				emprunt.setIdLivre(res.getInt("idLivre"));
				emprunt.setIdMembre(res.getInt("idMembre"));
				emprunt.setDateEmprunt(LocalDate.parse(res.getString("dateEmprunt")));
				String retour = res.getString("dateRetour");
				if (retour!=null){
					emprunt.setDateRetour(LocalDate.parse(retour));
				}

				emprunt.setMembre(res.getInt("idMembre"),
						res.getString("nom"), res.getString("prenom"), res.getString("adresse"),
						res.getString("email"), res.getString("telephone"), res.getString("abonnement"));
				emprunt.setLivre(res.getInt("idLivre"),
						res.getString("titre"),res.getString("auteur"),res.getString("isbn"));

			}
			
			System.out.println("GET one emprunt: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation du livre: id=" + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
			preparedStatement.setInt(2, idLivre);
			preparedStatement.setString(3, dateEmprunt.toString());
			preparedStatement.setString(4,null); //assume one book can be kept for 3 months
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();

			System.out.println("CREATE a emprunt, idMembre : " + idMembre+"  idLivre :"+idLivre+"  dateEmprunt : "+dateEmprunt);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation emprunt", e);
		} finally {

			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void update(Emprunt emprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, emprunt.getIdMembre());
			preparedStatement.setInt(2, emprunt.getIdLivre());
			preparedStatement.setString(3, emprunt.getDateEmprunt().toString());
			preparedStatement.setString(4, emprunt.getDateRetour().toString());
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise a jour emprunt: " + emprunt, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int count() throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultat = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(COUNT_QUERY);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				resultat = res.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors du compte des membres", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultat;
	}
}