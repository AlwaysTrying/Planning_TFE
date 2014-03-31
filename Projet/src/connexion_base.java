import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.NamingException;

public class connexion_base {
	  Connection connect = null;
	  Statement st = null;
	  public void connexion_base() throws ClassNotFoundException, SQLException{
	  
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager.getConnection("jdbc:mysql://localhost/base?" + "user=root");
		      System.out.println("conexion etablie avec la base");
  
	  }
	  
	  public Statement getst() throws SQLException{
		  st =connect.createStatement();
	
		  return st;
	  }
  
}
