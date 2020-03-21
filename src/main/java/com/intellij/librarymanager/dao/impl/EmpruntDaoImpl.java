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
	
	private static final String CREATE_QUERY = "INSERT INTO Membre (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\n" +
			"dateRetour" +
			"FROM emprunt AS e" +
			"INNER JOIN membre ON membre.id = e.idMembre" +
			"INNER JOIN livre ON livre.id = e.idLivre" +
			"WHERE e.id = ?;";

	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt," +
			"dateRetour" +
			"FROM emprunt AS e" +
			"INNER JOIN membre ON membre.id = e.idMembre" +
			"INNER JOIN livre ON livre.id = e.idLivre" +
			"ORDER BY dateRetour DESC;";

	private static final String SELECT_NON_RENDU_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt," +
			"dateRetour" +
			"FROM emprunt AS e" +
			"INNER JOIN membre ON membre.id = e.idMembre" +
			"INNER JOIN livre ON livre.id = e.idLivre" +
			"WHERE dateRetour IS NULL;";

	private static final String SELECT_NON_RENDU_BY_MEMBRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\n" +
			"dateRetour" +
			"FROM emprunt AS e" +
			"INNER JOIN membre ON membre.id = e.idMembre" +
			"INNER JOIN livre ON livre.id = e.idLivre" +
			"WHERE dateRetour IS NULL AND membre.id = ?;";

	private static final String SELECT_NON_RENDU_BY_LIVRE_QUERY= "SELECT e.id AS id, idMembre, nom, prenom, adresse, email," +
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\n" +
			"dateRetour" +
			"FROM emprunt AS e" +
			"INNER JOIN membre ON membre.id = e.idMembre" +
			"INNER JOIN livre ON livre.id = e.idLivre" +
			"WHERE dateRetour IS NULL AND livre.id = ?;";

	private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre=?, idLivre=?, dateEmprunt=?,dateRetour=? WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";


	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
		){
			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")), LocalDate.parse(res.getString("dateRetour")));
				emprunts.add(f);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
		){
			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")), LocalDate.parse(res.getString("dateRetour")));
				emprunts.add(f);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des non rendu", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_BY_MEMBRE_QUERY);
			 preparedStatement.setInt(1, idMembre);
			 ResultSet res = preparedStatement.executeQuery();
		){
			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")), LocalDate.parse(res.getString("dateRetour")));
				emprunts.add(f);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des non rendu", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NON_RENDU_BY_LIVRE_QUERY);
			 preparedStatement.setInt(1, idLivre);
			 ResultSet res = preparedStatement.executeQuery();
		){
			while(res.next()) {
				Emprunt f = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"),
						LocalDate.parse(res.getString("dateEmprunt")), LocalDate.parse(res.getString("dateRetour")));
				emprunts.add(f);
			}
			System.out.println("GET: " + emprunts);
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
				emprunt.setDateEmprunt(LocalDate.parse(res.getString("dataEmprunt")));
				emprunt.setDateRetour(LocalDate.parse(res.getString("dataRetour")));
				/**这里也许可以加上所有列的内容，如果有必要 **/
			}
			
			System.out.println("GET: " + emprunt);
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
			System.out.println("Compter le nombre des membres");
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