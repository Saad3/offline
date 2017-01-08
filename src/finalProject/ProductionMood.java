package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;



public class ProductionMood {

	private Connection connection;
	private ResultSet result;
	private String[][] city;
	private String offline;
	private String online;
	private String optimized;
	private static String originalDB = "jdbc:mysql://localhost:3306/offline?useUnicode=yes&characterEncoding=UTF-8";

	private Listener listener ;
	
	
	
	/*
	 * String ConsumerKey = "v7cIWGoF4tnB8xCX2VQDvFfeg";
		String ConsumerSecret = "QluGAaYXULyo9GBJT7G5CFDOPqOhrMhKIUiPNWlBjTqf3sPv8Y";
		String AccessToken ="245278759-XnJon4fbGFNYdbW15nDZU4jt2syx0CFMTfbWpGRG";
		String AccessTokenSecret="nDo3Rl3IGTMtweBhOEoize2L8vDrfnnDRbQ6yU9FyvvoV"; 
	 * 
	 * 
	 * 
	 */
	
	public ProductionMood(String url, String username, String password, String offline, String online, String optimized){
		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			//statement = connection.createStatement();
			this.offline=offline;
			this.optimized=optimized;
			this.online=online;
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void startProductionMood(){
		controller();
	}
	private void controller(){
		
		try {
			int numberOfQuery;
			int oldnumberOfQuery =0;
			
			PreparedStatement query;
			
			while(true){	
				query = connection.prepareStatement("SELECT COUNT(*) AS total "
						+ "FROM "+online+".query , "+online+".duration "
						+ "WHERE "+online+".query.status = 0 "
						+ "AND "+online+".duration.query_id = "+online+".query.query_id "
						+ "AND "+online+".duration.start_time <= NOW() "
						+ "AND "+online+".duration.end_time >= NOW()");
				result = query.executeQuery();
				result.next();
				numberOfQuery = result.getInt("total");
				
				

				if(numberOfQuery !=oldnumberOfQuery)
				System.out.println(numberOfQuery);

				
				
				if ((oldnumberOfQuery != numberOfQuery) && numberOfQuery!=0 ){
					//listener.stop(); //stop the listener
					setTags(retrieveCurrentTags(numberOfQuery)); //set new tags array
					//TimeUnit.MINUTES.sleep(10);

				}
					if (oldnumberOfQuery > numberOfQuery){ //then we have one query finished !!
						
						//we may need new threads here !!
						//cleaner();
						runAlgorithmForQuery((oldnumberOfQuery - numberOfQuery)); // how many rags finished
					}
					
					oldnumberOfQuery = numberOfQuery;
				
			//	TimeUnit.SECONDS.sleep(10);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	


	private void runAlgorithmForQuery(int numberOfTags) {
		System.out.println("runAlgorithmForQuery! start work ");

			try {
				String[] tags = new String[numberOfTags]; 
				int[] ids= new int[numberOfTags]; 
				PreparedStatement query, updateQuery;
				query = connection.prepareStatement("SELECT "+online+".query.content , "+online+".query.query_id "
						+ "FROM "+online+".query, "+online+".duration "
						+ "WHERE "+online+".query.status = 0 "
						+ "AND "+online+".duration.query_id = "+online+".query.query_id "
						+ "AND "+online+".duration.end_time < NOW()");

				updateQuery = connection.prepareStatement("UPDATE "+online+".query "
						+ "SET "+online+".query.status=1 "
						+ "WHERE "+online+".query.query_id=?");

				result = query.executeQuery();
				
				for (int i = 0; i < tags.length; i++){
					
					result.next();
					tags[i]=result.getString("content");
					ids[i]=result.getInt("query_id");
					updateQuery.setInt(1,ids[i]);
					updateQuery.executeUpdate();
				}
				
				for (int i = 0; i < tags.length; i++) {
					algorthim(ids[i],findTagID(tags[i]));
				}
			
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}


	private void algorthim(int queryId, int tagID) {
		try {
			
			AlgorithmForProduction algorithm = new AlgorithmForProduction(originalDB,"root","","optimized_offline_db");
			algorithm.startAgorthm(queryId, tagID);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private int findTagID(String tagName) {
		// to find the tag id and use it in algorithm
		try {
			PreparedStatement query;
			query = connection.prepareStatement("SELECT tag_id  FROM "+optimized+".tags WHERE keyword =?");
			query.setString(1,tagName);
			
			result = query.executeQuery();
			result.next();
			
			return result.getInt("tag_id");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
		}
	}


	private String[] retrieveCurrentTags(int numberOfQuery) {
		
		
		System.out.println("retrieveCurrentTags! start work ");
		
		
		try {
			String[] tags = new String[numberOfQuery]; 
			
			PreparedStatement query;
			query = connection.prepareStatement("SELECT "+online+".query.content "
					+ "FROM "+online+".query, "+online+".duration "
					+ "WHERE "+online+".query.status = 0 "
					+ "AND "+online+".duration.query_id = "+online+".query.query_id "
					+ "AND "+online+".duration.start_time <= NOW() "
					+ "AND "+online+".duration.end_time >= NOW()");
		
			result = query.executeQuery();
			
			for (int i = 0; i < tags.length; i++){
				result.next();
				tags[i]=result.getString("content");
			}
			
			return tags;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}



	
	
	private void setTags(String tags[]) {
		
		System.out.println("setTags! start work ");


		try {
			String ConsumerKey = "v7cIWGoF4tnB8xCX2VQDvFfeg";
			String ConsumerSecret = "QluGAaYXULyo9GBJT7G5CFDOPqOhrMhKIUiPNWlBjTqf3sPv8Y";
			String AccessToken ="245278759-XnJon4fbGFNYdbW15nDZU4jt2syx0CFMTfbWpGRG";
			String AccessTokenSecret="nDo3Rl3IGTMtweBhOEoize2L8vDrfnnDRbQ6yU9FyvvoV"; 

			
			
			DbConnection db = new DbConnection(originalDB,"root","","offline");

			
			listener = new Listener(db,ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret);

				System.out.println("The system will listen on:");
			
				for (String string : tags) {
					System.out.println(string);
				}
				listener.startListening(tags);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	//2
	
	private void cleaner(){
		
		System.out.println("System will start clening now!");
		Cleaner cleaner = new Cleaner(originalDB,"root","","offline","optimized_offline_db");
		cleaner.startClean();
		
		//to fix 
		
		System.out.println("Cleaning is Finished!");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void controller0(){




		try {
			while(true){

		//		listener = new Listener(db,ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret);

				System.out.println("The system will listen on:");
				//String key[] = tagsGenerator.generatTags(10);
			//	for (String string : key) {
			//		System.out.println(string);
			//	}
			//	listener.startListening(tagsGenerator.generatTags(10));
				System.out.println("The System is running now");
				TimeUnit.MINUTES.sleep(10);
				listener.stop();
				System.out.println("System will be restarted now");


			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
