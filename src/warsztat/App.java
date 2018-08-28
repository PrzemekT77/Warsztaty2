package warsztat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class App {

	
	private static final String CREATE_DATABASE = "CREATE DATABASE Warsztaty2;";
	
	private static final String CREATE_TABEL_USER_GROUPS = "CREATE TABLE Warsztaty2.user_groups (" + 
			"id INT AUTO_INCREMENT NOT NULL," + 
			"name VARCHAR(255) NOT NULL," + 
			"PRIMARY KEY(id));";
			
		private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE Warsztaty2.exercise (" + 
			"id INT AUTO_INCREMENT NOT NULL," + 
			"title VARCHAR(255)," + 
			"description TEXT," + 
			"PRIMARY KEY(id));";
		
		private static final String CREATE_TABLE_SOLUTIONS = "CREATE TABLE Warsztaty2.solutions (" + 
				"id INT auto_increment, " + 
				"created datetime," + 
				"updated datetime," + 
				"description varchar(255)," + 
				"exercise int," + 
				"users_id bigint," + 
				"primary key (id)," + 
				"FOREIGN KEY (users_id) REFERENCES Warsztaty2.users(id));";
		
		private static final String CEATE_TABLE_USERS =  "CREATE TABLE Warsztaty2.users("
		         + "id BIGINT(20) NOT NULL AUTO_INCREMENT, "
		         + "username VARCHAR(255) NOT NULL, "
		         + "email VARCHAR(255) NOT NULL UNIQUE , "
		         + "password VARCHAR(245) NOT NULL, "
		         + "user_group_id INT(11) NOT NULL, "
		         + "PRIMARY KEY (id), "
		         + "FOREIGN KEY (user_group_id) REFERENCES Warsztaty2.user_groups(id));";
	
	
	public static void main(String[] args) {
	
		try (Connection conn =  DBUtil.createConnection();
			Statement statement = conn.createStatement();) {
			
		 statement.execute(CREATE_DATABASE);
		 
		 statement.execute(CREATE_TABEL_USER_GROUPS);
		 statement.execute(CREATE_TABLE_EXERCISE);
		 statement.execute(CEATE_TABLE_USERS);
		 statement.execute(CREATE_TABLE_SOLUTIONS);
			
		}catch (SQLException ex) {
	        ex.printStackTrace();
	      }
	}
}
