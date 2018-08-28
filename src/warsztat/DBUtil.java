package warsztat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	
	public final static Connection createConnection () throws SQLException {
		
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root",
				"coderslab");
	}
}
