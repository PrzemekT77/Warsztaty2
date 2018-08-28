package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	private static long id;
	private Date created;
	private Date updated;
	private String description;
	private static int exercise;
	private static long users_id;
	
	private static final String INSERT_SOLUTION_STATEMENT = "INSERT INTO Warsztaty2.solutions(created, updated, description, exercise, users_id) "
			+ "VALUES (?, ?, ?, ?, ?)";
	
	public Solution (Date created, Date updated, String description, int exercise, long users_id) {
		//this.id = id;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.exercise = exercise;
		this.users_id = users_id;
	}
	
	public Solution () {
		
	}
	
	public Solution[] loadAllSolutions (Connection conn) throws SQLException {
		List<Solution> solutions = new ArrayList<Solution>();
	    String sql = "SELECT * FROM Warsztaty2.solutions"; 
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	    	Solution loadedSolution = new Solution();
	        loadedSolution.id = resultSet.getInt("id");
	        loadedSolution.created = resultSet.getDate("created");
	        loadedSolution.updated = resultSet.getDate("updated");
	        loadedSolution.description = resultSet.getString("description");
	        loadedSolution.exercise = resultSet.getInt("exercise");
	        loadedSolution.users_id = resultSet.getLong("users_id");
	        solutions.add(loadedSolution);}
	    Solution[] uArray = new Solution[solutions.size()]; 
	    uArray = solutions.toArray(uArray);
	    return uArray;
	}
	
	public Solution loadSolutionById (Connection conn) throws SQLException {
	    String sql = "SELECT * FROM Warsztaty2.solutions where id=?";
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setLong(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (resultSet.next()) {
	    	return createSolution (resultSet);
	        }
	    return null;
	    }
	
	public void deleteSolution (Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM Warsztaty2.solutions WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setLong(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	public void saveSolutionToDB (Connection conn) throws SQLException {
		String generatedColumns[] = {"id"};
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(INSERT_SOLUTION_STATEMENT, generatedColumns);
	    preparedStatement.setDate(1, this.created);
	    preparedStatement.setDate(2, this.updated);
	    preparedStatement.setString(3, this.description);
	    preparedStatement.setInt(4, this.exercise);
	    preparedStatement.setLong(4, this.users_id);
	    preparedStatement.executeUpdate();

	    ResultSet rs = preparedStatement.getGeneratedKeys();
	    if (rs.next()) {
	      this.id = rs.getInt(1);
	    }
	}
	
public static Solution createSolution (ResultSet resultSet) throws SQLException {
		
		Date created = resultSet.getDate ("created");
		Date updated = resultSet.getDate ("updated");		
    	String description = resultSet.getString("description");
    	int exercise = resultSet.getInt("exercise");
    	long users_id = resultSet.getLong("users_id");
    	
    	Solution loadedSolution = new Solution (created, updated, description, exercise, users_id);
        
    	loadedSolution.id = resultSet.getLong("id");
        return loadedSolution;
	}

public static Solution loadAllByUserId (Connection conn) throws SQLException {
	String sql = "SELECT * FROM Warsztaty2.solutions where users_id=?";
    PreparedStatement preparedStatement;
    preparedStatement = conn.prepareStatement(sql);
    preparedStatement.setLong(1, users_id);
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if (resultSet.next()) {
    	return createSolution (resultSet);
        }
    return null;
    }

public static Solution loadAllByExerciseId (Connection conn) throws SQLException {
	String sql = "SELECT * FROM Warsztaty2.solutions where exercise=?";
    PreparedStatement preparedStatement;
    preparedStatement = conn.prepareStatement(sql);
    preparedStatement.setInt(1, exercise);
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if (resultSet.next()) {
    	return createSolution (resultSet);
        }
    return null;
	
}

}
