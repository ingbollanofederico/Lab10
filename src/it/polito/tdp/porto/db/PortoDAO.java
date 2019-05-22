package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import it.polito.tdp.porto.model.Adiacenza;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {
	
	public List<Author> listAutori(Map<Integer,Author> idMap){

			final String sql = "SELECT * FROM author";
			List<Author> risultato = new ArrayList<Author>();

			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);

				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					
					if(idMap.get(rs.getInt("id"))==null) {
					Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
					idMap.put(rs.getInt("id"), autore);
					risultato.add(autore);
					
					}else {
						risultato.add(idMap.get(rs.getInt("id")));
					}
					
				}
				
				conn.close();
				return risultato;
				

			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}
		}
	

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
		
			}
			
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Adiacenza> listAdiacenze(Map<Integer,Author> idMap) {

		final String sql = "SELECT c1.authorid AS a1,c2.authorid AS a2, p1.eprintid, p1.title, p1.issn, p1.publication, p1.type, p1.types" + 
				" FROM creator c1,creator c2, paper p1 " + 
				" WHERE c1.eprintid = c2.eprintid AND c2.authorid>c1.authorid AND p1.eprintid= c2.eprintid ";

		List<Adiacenza> risultato = new ArrayList<Adiacenza>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				risultato.add(new Adiacenza(idMap.get(rs.getInt("a1")),idMap.get(rs.getInt("a2")), new Paper(rs.getInt("eprintid"), rs.getString("title"),
								rs.getString("issn"),rs.getString("publication"),
								rs.getString("type"), rs.getString("types"))));
			}

			return risultato;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	
	
}