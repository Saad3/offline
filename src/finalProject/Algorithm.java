package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Algorithm {

	private Connection connection;
	private ResultSet result;
	private String offline;
	private String optimized;
	private String[][] emotin;
	
	public Algorithm( String url, String username, String password, String offline, String optimized){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			this.offline=offline;
			this.optimized=optimized;
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 
	
	private void startAgorthm(){
		emotionFinder();
	}



	private void emotionFinder() {
		try {
			
			
			if(retrieveEmotion()){
				
				
				
				
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	private boolean retrieveEmotion() {
		try {
			
			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM "+optimized+".emotion"); // to retrieve the emotion
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM "+optimized+".emotion "); // to know how many emotion we have
			result = countQuery.executeQuery();
			result.next();
			emotin = new String[result.getInt("total")][2];

			result = retrieveQuery.executeQuery();
			
			for (int i = 0; i < emotin.length; i++) { // to store the emotion in array
				result.next();
				emotin[i][0] = result.getString("keyword_id");
				emotin[i][1] = result.getString("keyword");
			}
			
			return true;
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}
