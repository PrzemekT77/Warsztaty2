package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import warsztat.DBUtil;
import org.mindrot.jbcrypt.BCrypt;


public class User {

	private static long id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;
	
	private static final String ID_COLUMN_NAME = "id";
	private static final String USERNAME_COLUMN_NAME = "username";
	private static final String PASSWORD_COLUMN_NAME = "password";
	private static final String EMAIL_COLUMN_NAME = "email";
	
	
	private static final String INSERT_USER_STATEMENT = "INSERT INTO Warsztaty2.users(username, email, password) "
			+ "VALUES (?, ?, ?)";
	private static final String UPDATE_USER_STATEMENT =
		      "UPDATE warsztaty2.Users SET username=?, email=?, password=? where id = ?";

	public User( String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.setPassword (password);
	}
	
	public User() {
		
	}
		
	public void setPassword (String passsword) {	
		this.password = BCrypt.hashpw(passsword, BCrypt.gensalt());
	}
	
	//zapisywanie nowego obiektu do bazy
	
	public void save(Connection conn) throws SQLException {
		  if (this.id == 0) {saveNewUser(conn);
		  }else {
			  updateUser(conn);
		  }
		}
	
	private void saveNewUser (Connection conn) throws SQLException {
		String generatedColumns[] = {ID_COLUMN_NAME};
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(INSERT_USER_STATEMENT, generatedColumns);
	    preparedStatement.setString(1, this.username);
	    preparedStatement.setString(2, this.email);
	    preparedStatement.setString(3, this.password);
	    preparedStatement.executeUpdate();

	    ResultSet rs = preparedStatement.getGeneratedKeys();
	    if (rs.next()) {
	      this.id = rs.getLong(1);
	    }
	}
	
	private void updateUser (Connection conn) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USER_STATEMENT);
		preparedStatement.setString(1, this.username);
		preparedStatement.setString(2, this.email);
		preparedStatement.setString(3, this.password);
		preparedStatement.setLong(4, this.id);
		preparedStatement.executeUpdate();
	}
	
	public static void main(String[] args) throws SQLException {
		
	//podaj id dla użytkownika
	//	long id = 1;
		User jan = new User ("Jan", "jan@gmail.com", "codersLab");
		try (Connection conn = DBUtil.createConnection()) {
		User user = User.loadUserById(conn, id);
	//	 loadAllUsers
		User[]users = User.loadAllUsers(conn);
		System.out.println(Arrays.toString(users));
		}
	}
	//wczytywanie użytkownika z bazy
	static public User loadUserById(Connection conn, long id) throws SQLException {
	    String sql = "SELECT * FROM Warsztaty2.users where id=?";
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setLong(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (resultSet.next()) {
	    	return createUser (resultSet);
	        }
	    return null;
	    }
	
	public static User createUser (ResultSet resultSet) throws SQLException {
		
		String username = resultSet.getString(USERNAME_COLUMN_NAME);
    	String password = resultSet.getString(PASSWORD_COLUMN_NAME);
    	String email = resultSet.getString(EMAIL_COLUMN_NAME);
        User loadedUser = new User(username, email, password);
        
        loadedUser.id = resultSet.getLong(ID_COLUMN_NAME);
        return loadedUser;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", password=" + password + ", userGroupId="
				+ userGroupId + "]";
	}
//	*
//	 * do weryfikacji wczytywanie wszystkich uzytkowników
//	 * 
	static public User[] loadAllUsers(Connection conn) throws SQLException {
	    List<User> users = new ArrayList<User>();
	    String sql = "SELECT * FROM Warsztaty2.users"; 
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        User loadedUser = new User();
	        loadedUser.id = resultSet.getInt("id");
	        loadedUser.username = resultSet.getString("username");
	        loadedUser.password = resultSet.getString("password");
	        loadedUser.email = resultSet.getString("email");
	        users.add(loadedUser);}
	    User[] uArray = new User[users.size()]; uArray = users.toArray(uArray);
	    return uArray;}
//	*

	//usunięcie obiektu
	public void delete(Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM Warsztaty2.users WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setLong(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	public User []loadAllByGrupId (Connection conn) throws SQLException {
		   List<User> usersByGroup = new ArrayList<User>();
		String sql = "SELECT * FROM Warsztaty2.users where user_group_id=?";
		
		PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setInt(1, userGroupId);
	    ResultSet resultSet = preparedStatement.executeQuery();
		
	    while (resultSet.next()) {
	        User loadedUser = new User();
	        loadedUser.id = resultSet.getInt("id");
	        loadedUser.username = resultSet.getString("username");
	        loadedUser.password = resultSet.getString("password");
	        loadedUser.email = resultSet.getString("email");
	        loadedUser.userGroupId = resultSet.getInt("user_group_id");
	        usersByGroup.add(loadedUser);}
	        User[]uByGroupArray = new User [usersByGroup.size()]; uByGroupArray = usersByGroup.toArray(uByGroupArray);
	return uByGroupArray;
	    }
	
}

