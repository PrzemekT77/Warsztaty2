package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercise {

	private static long id;
	private String title;
	private String description;
	
	private static final String INSERT_EXERCISE_STATEMENT = "INSERT INTO Warsztaty2.exercise(title, description) "
			+ "VALUES (?, ?)";
	
	public Exercise ( String title, String description) {
	
		this.title = title;
		this.description = description;
	}
	public Exercise (){
		
	}
	
	public Exercise[] loadAllExercises (Connection conn) throws SQLException {
		List<Exercise> exercises = new ArrayList<Exercise>();
	    String sql = "SELECT * FROM Warsztaty2.exercise"; 
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        Exercise loadedExercise = new Exercise();
	        loadedExercise.id = resultSet.getInt("id");
	        loadedExercise.title = resultSet.getString("username");
	        loadedExercise.description = resultSet.getString("description");
	        exercises.add(loadedExercise);}
	    Exercise[] uArray = new Exercise[exercises.size()]; 
	    uArray = exercises.toArray(uArray);
	    return uArray;
	}
	
	public Exercise loadExerciseById (Connection conn) throws SQLException {
	    String sql = "SELECT * FROM Warsztaty2.exercise where id=?";
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setLong(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if (resultSet.next()) {
	    	return createExercise (resultSet);
	        }
	    return null;
	    }
	
	public void deleteExercise (Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM Warsztaty2.exercise WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setLong(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	public void saveExerciseToDB (Connection conn) throws SQLException {
		String generatedColumns[] = {"id"};
	    PreparedStatement preparedStatement;
	    preparedStatement = conn.prepareStatement(INSERT_EXERCISE_STATEMENT, generatedColumns);
	    preparedStatement.setString(1, this.title);
	    preparedStatement.setString(2, this.description);
	    preparedStatement.executeUpdate();

	    ResultSet rs = preparedStatement.getGeneratedKeys();
	    if (rs.next()) {
	      this.id = rs.getInt(1);
	    }
	}
	
public static Exercise createExercise (ResultSet resultSet) throws SQLException {
		
		String title = resultSet.getString("title");
    	String description = resultSet.getString("description");
        Exercise loadedExercise = new Exercise(title, description);
        
        loadedExercise.id = resultSet.getLong("id");
        return loadedExercise;
	}
	}
