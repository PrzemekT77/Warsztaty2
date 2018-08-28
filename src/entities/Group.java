package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Group {
	
	private static long id;
	private static String name;
	
	private static final String ID_COLUMN_NAME = "id";
	private static final String NAME_COLUMN_NAME = "name";

	private static final String INSERT_GROUP_STATEMENT = "INSERT INTO Warsztaty2.user_groups(name) "
			+ "VALUES (?)";
	
	public Group (String name) {

		this.name = name;
	}
	
	public Group () {
		
	}
	

	public void setName (String name){
		this.name = name;
	}
	public String getName (){
		return name;
	}
	
	public static void main(String[] args) {
		
	}
	
	public static Group createGroup (ResultSet resultSet) throws SQLException{
		
		String name = resultSet.getString(NAME_COLUMN_NAME);
		
		Group loadedGroup = new Group (name);
		
		loadedGroup.id = resultSet.getLong(ID_COLUMN_NAME);
		return loadedGroup;
		
		
	}
	
	static public Group loadGroupById (Connection conn, long id) throws SQLException {
		
		String sqlGroup = "SELECT * FROM Warsztaty2.user_groups where id=?";
		PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sqlGroup);
	    preparedStatement.setLong(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (resultSet.next()) {
	    	return createGroup (resultSet);
	        }
		
		return null;
		
	}
	
	public Group [] loadAllGroup (Connection conn) throws SQLException{
		
		ArrayList<Group>groups = new ArrayList<Group>();
		String sql = "SELECT * FROM Warsztaty2.user_groups"; 
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        Group loadedGroup = new Group();
	        loadedGroup.id = resultSet.getInt("id");
	        loadedGroup.name = resultSet.getString("name");
	        groups.add(loadedGroup);}
	    Group[] uArray = new Group[groups.size()]; uArray = groups.toArray(uArray);
	    return uArray;
		
	}
	
	public  void saveGroupToDB (Connection conn) throws SQLException {
		if (this.id == 0) {
		    String generatedColumns[] = { "ID_COLUMN_NAME"};
		    PreparedStatement preparedStatement;
		    preparedStatement = conn.prepareStatement(INSERT_GROUP_STATEMENT, generatedColumns);
		    preparedStatement.setString(1, this.name);    
		    preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    if (rs.next()) {
		      this.id = rs.getInt(1);
		    }
		  }
	}
	
	public void deleteGroup (Connection conn) throws SQLException {
		if (this.id != 0) {
	        String sql = "DELETE FROM Warsztaty2.user_groups WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = 	conn.prepareStatement(sql);
	        preparedStatement.setLong(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;	
	    }			
	}		
}
