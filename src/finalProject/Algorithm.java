package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Algorithm {

	private Connection connection;
	private ResultSet result;
	private String offline;
	private String optimized;
	private String[][] emotin;
	private ArrayList<String> tweet;
	

	public Algorithm( String url, String username, String password, String offline, String optimized){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			this.offline=offline;
			this.optimized=optimized;
			tweet = new ArrayList<String>();
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void startAgorthm(){
		//emotionFinder();
		retrevtweets();
		test();
	}

	private void test() {

		System.out.println(tweet.size());
	}


	public void algorithm(){
		
		
		try {
			int maximumIteration=0;
			int t=0;
			while(t<= maximumIteration){
				for (int U = 0; U < emotin.length; U++) {//U Sets of users.
					for (int I = 0; I < emotin.length; I++) {//I Sets of items.
						for (int T = 0; T < emotin.length; T++) {//T Sets of tags.
							calculateEmotionalScore();
							calculateItemTagRelevance();
						}
					}
					
				}
				
				for (int U = 0; U < emotin.length; U++) {
					calculateUserUserSimilarity();
				}
				for (int I = 0; I < emotin.length; I++) {
					calculateItemItemSimilarity();
				}
				for (int T = 0; T < emotin.length; T++) {
					calculateTagTagSimilarity();
				}
				
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void calculateEmotionalScore() {
		// TODO Auto-generated method stub
		
	}
	
	private void retrevtweets(){
		
		try {
			
			PreparedStatement res;
			res = connection.prepareStatement("SELECT textual_content FROM "+optimized+".item WHERE 1");
			result = res.executeQuery();

			while(result.next()){

				tweet.add(result.getString("textual_content"));
				result.next();
				
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	
	
	
	
	
	
	private void calculateTagTagSimilarity() {
		// TODO Auto-generated method stub
		
	}

	private void calculateItemItemSimilarity() {
		// TODO Auto-generated method stub
		
	}

	private void calculateUserUserSimilarity() {
		// TODO Auto-generated method stub
		
	}

	private void calculateItemTagRelevance() {
		// TODO Auto-generated method stub
		
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
				emotin[i][0] = result.getString("keyword_id");//to insert it in the table "has emo"
				emotin[i][1] = result.getString("keyword");
			}
			
			return true;
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	
	
	
	
}
