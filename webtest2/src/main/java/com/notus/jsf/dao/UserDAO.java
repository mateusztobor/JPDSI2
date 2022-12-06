package com.notus.jsf.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.NoneScoped;
import javax.inject.Named;
import com.notus.jsf.e.User;
import com.notus.jsf.db.DataConnect;

@Named
@RequestScoped
public class UserDAO {

	public User getUserFromDatabase(String email, String password) {
		User u = null;
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = DataConnect.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM users WHERE email='"+email+"' and password='"+password+"' LIMIT 1;");
			while(rs.next()) {
				u = new User();
				u.setId(rs.getInt("id"));
				u.setEmail(email);
				u.setPassword(password);
				u.setNick(rs.getString("nick"));
				u.setType(rs.getInt("type"));
				break;
			}
		}
		catch(Exception ex) {
			System.out.println("Błąd połączenia z bazą danych: " + ex.getMessage());
		} finally {
			DataConnect.close(c);
		}
		return u;
	}

	// simulate retrieving roles of a User from DB
	public List<String> getUserRolesFromDatabase(User user) {
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		ArrayList<String> roles = new ArrayList<String>();
		
		try {
			c = DataConnect.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM users WHERE email='"+user.getEmail()+"' and password='"+user.getPassword()+"' LIMIT 1;");
			while(rs.next()) {
				if(rs.getInt("type") == 1) roles.add("user");
				else if(rs.getInt("type") == 2) roles.add("admin");
				break;
			}
		}
		catch(Exception ex) {
			System.out.println("Błąd połączenia z bazą danych: " + ex.getMessage());
		} finally {
			DataConnect.close(c);
		}
		
		return roles;
	}
}
