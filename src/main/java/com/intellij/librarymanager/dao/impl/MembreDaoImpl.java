package com.intellij.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import com.intellij.librarymanager.persistence.ConnectionManager;
import com.intellij.librarymanager.exception.DaoException;
import com.intellij.librarymanager.model.Abonnement;
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.dao.MembreDao;

public class MembreDaoImpl implements MembreDao{
	
	private static MembreDaoImpl instance;
	private MembreDaoImpl() { }	
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	
	private static final String CREATE_QUERY = "INSERT INTO Membre (nom, prenom, address, email, telephone, abonnement) VALUES (?, ?, ?, ? ,?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM Membre WHERE id=?;";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Membre;";
	private static final String UPDATE_QUERY = "UPDATE Membre SET nom=?, prenom=?, address=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private static final String DELETE_QUERY = "DELETE FROM Membre WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
	
	@Override
	public Membre getById(int id) throws DaoException{
		Membre membre = new Membre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));		
				membre.setAdresse(res.getString("address"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
				membre.setAbonnement(abonnement);					
				
			}
			
			System.out.println("GET: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la ... du membre: id=" + id, e);
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
		return membre;
	}

	@Override
	public int create(String nom, String prenom, String address, String email, String telephone, Abonnement abonnement) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, prenom);
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, telephone);
			preparedStatement.setString(6, abonnement.toString());
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE a membre");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation du membre", e);
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
		return id;
	}
	
	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
				Membre f = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), 
							res.getString("address"),res.getString("email"),res.getString("telephone"), abonnement);
				membres.add(f);
			}
			System.out.println("GET: " + membres);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des membres", e);
		}
		return membres;
	}
	
	@Override
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE the member " );
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la suppression du membre ", e);
		}  finally {
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
	public void update(Membre membre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getAdresse());
			preparedStatement.setString(4, membre.getEmail());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setString(6, membre.getAbonnement().toString());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise a jour du livre: " + membre, e);
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