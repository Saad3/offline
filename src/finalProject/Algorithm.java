package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class Algorithm {

	private Connection connection;
	private ResultSet result;
	private String offline;
	private String optimized;
	private String[] emotionWord;
	private int[][] emotionWeight;
	private int[][] originalEmotionWeight;
	private Stack<String> tweet;
	private int resultCounter;
	private int  positiveEmotion, negativeEmotion, positiveNum, negativeNum;
	private String[][] city;


	public Algorithm(String url, String username, String password, String offline, String optimized) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			this.offline = offline;
			this.optimized = optimized;
			tweet = new Stack<String>();

			resultCounter = 0;

			positiveEmotion = negativeEmotion = positiveNum = negativeNum = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startAgorthm() {

		retrieveEmotion();
		System.out.println("retrieveEmotion is Done!");
		retrieveCityNames();
		System.out.println("retrieveCityNames is Done!");
		//calculateEmotionalScoreForCities();
		//calculateEmotionalScore();
		retrevtweets();
		emotionFinder() ;
		calculateEmotionalScoreForOneCity();
		System.out.println("Done !!!!!!!");
	
	}

	private void calculateEmotionalScoreForCities() { // you can't call this method before calling retrieveCityNames()
		
		try {
			
			for (int i = 0; i < city.length; i++) {
				retrevtweets(city[i][0]);
				calculateEmotionalScoreForOneCity(city[i][3]);
			}
			retrevtweets("NULL");
			calculateEmotionalScoreForOneCity("unlocated");
			
		} catch (Exception e) {
			System.out.println("error in calculateEmotionalScoreForCity()");
			e.printStackTrace();
		}
		
		
		
	}

	private void calculateEmotionalScoreForOneCity(String States) {
		System.out.println("The Result for "+States);

		
		try {
			int positiveEmotion, negativeEmotion, positiveNum, negativeNum;
			positiveEmotion = negativeEmotion = positiveNum = negativeNum = 0;
			int emotionWeight[][]= emotionFinder(originalEmotionWeight);
			
			for (int i = 0; i < emotionWeight.length; i++) {
				
				System.out.println("This Emotion: "+emotionWord[i]+" "
						+ "ID is: "+emotionWeight[i][0]+" "
						+ "Weight is: "+emotionWeight[i][1]+" "
						+ "Used: "+emotionWeight[i][2]+" Time "
						+ "The Total Weight is: "+(emotionWeight[i][1]*emotionWeight[i][2]));
				
				if (emotionWeight[i][1] < 0) {
					negativeNum=negativeNum+emotionWeight[i][2];
					negativeEmotion =negativeEmotion+ emotionWeight[i][1];
				} else {
					positiveNum=positiveNum+emotionWeight[i][2];
					positiveEmotion =positiveEmotion+ emotionWeight[i][1];
				}
			}//end of for
			//TODO fix the problem
			System.out.println("The Result for "+States+"is:");
			
			System.out.println("The Number of negative Emotions is: "+negativeNum+" "
					+ "The Sum weight of negativity is: "+negativeEmotion);
			
			System.out.println("The Number of positive Emotions is: "+positiveNum+" "
					+ "The Sum weight of positivity is: "+positiveEmotion);
			
			System.out.println("The Total Number Emotions is: "+(positiveEmotion+negativeEmotion)+" | "
					+ ((positiveEmotion*100)/(positiveEmotion+negativeEmotion))+"% Positive "
							+ ((negativeEmotion*100)/(positiveEmotion+negativeEmotion))+"% Negative");
			
			this.positiveEmotion = this.positiveEmotion + positiveEmotion;
			this.negativeEmotion = this.negativeEmotion + negativeEmotion;
			this.positiveNum = this.positiveNum + positiveNum;
			this.negativeNum = this.negativeNum + negativeNum;

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void retrevtweets(String states_id) {
		try {
			setResultCounter();

			int startNum, endNum, i;
			i = 0;
			startNum = 0;
			endNum = 500;

			while (i < resultCounter) {
				PreparedStatement res;
				res = connection
						.prepareStatement("SELECT textual_content FROM " + optimized + ".item "
										+ "INNER JOIN " + optimized + ".user "
										+ "WHERE " + optimized + ".user.states_id =?"
										+ "limit ?, ?");
				res.setString(1, states_id);
				res.setInt(2, startNum);
				res.setInt(3, endNum);
				result = res.executeQuery();
				while (result.next()) {
					tweet.push(result.getString("textual_content"));
					i++;
					result.next();

				}
				startNum = endNum + 1;
				if (endNum + 500 > resultCounter)
					endNum = resultCounter;
				else
					endNum = +500;

			}
			System.out.println(i + "\t" + resultCounter);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void setResultCounter() {// TODO fix

		try {

			PreparedStatement res;
			res = connection.prepareStatement("SELECT COUNT(*) AS total FROM optimized_offline_db.item WHERE 1");
			result = res.executeQuery();
			result.next();
			resultCounter = result.getInt("total");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int[][] emotionFinder(int cityEmotionWeight[][]) {
		
		try {
			
			String testedTweet = null;
			while (!tweet.isEmpty()) {
				testedTweet = tweet.pop();
				for (int i = 0; i < emotionWord.length; i++) {
					if (testedTweet.contains(emotionWord[i])) {
						emotionWeight[i][2]++;
						cityEmotionWeight[i][2]++;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityEmotionWeight;
	}

	private boolean retrieveEmotion() {
		try {

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM " + optimized + ".emotion"); // to retrieve the emotion
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM " + optimized + ".emotion "); // to know how many emotion we have
			result = countQuery.executeQuery();
			result.next();
			emotionWord = new String[result.getInt("total")];
			emotionWeight = new int[result.getInt("total")][3];
			result = retrieveQuery.executeQuery();

			for (int i = 0; i < emotionWord.length; i++) { // to store the emotion in
														// array
				result.next();
				emotionWord[i] = result.getString("keyword");// to insert it in the table "has emo"
				emotionWeight[i][0] = result.getInt("keyword_id");
				emotionWeight[i][1] = result.getInt("weight");
			}
			originalEmotionWeight=emotionWeight;

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void retrieveCityNames() {
		try {

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM " + optimized + ".cities WHERE " +optimized+ ".cities.parent_id < 14");//TODO Fix
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM " + optimized + ".cities ");
			result = countQuery.executeQuery();
			result.next();
			city = new String[result.getInt("total")][4];

			result = retrieveQuery.executeQuery();

			// 0 will hold states number 1 will hold city name in arabic 2 in english
			//while (result.next()) {
				
				for (int i = 0; i < city.length; i++) {
					result.next();
					city[i][0] = result.getString("id");
					city[i][1] = result.getString("parent_id");
					city[i][2] = result.getString("arabic");
					city[i][3] = result.getString("english");
					

				}
			//}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public void algorithm() {

		try {
			int maximumIteration = 0;
			int t = 0;
			while (t <= maximumIteration) {
				for (int U = 0; U < emotionWord.length; U++) {// U Sets of users.
					for (int I = 0; I < emotionWord.length; I++) {// I Sets of items.
						for (int T = 0; T < emotionWord.length; T++) {// T Sets of
																	// tags.
						//	calculateEmotionalScore();
							calculateItemTagRelevance();
						}
					}

				}

				for (int U = 0; U < emotionWord.length; U++) {
					calculateUserUserSimilarity();
				}
				for (int I = 0; I < emotionWord.length; I++) {
					calculateItemItemSimilarity();
				}
				for (int T = 0; T < emotionWord.length; T++) {
					calculateTagTagSimilarity();
				}

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

	
	
	
	
	
	
	//////////////////////////////////////////////
	
	private void retrevtweets() {
		try {
			setResultCounter();

			int startNum, endNum, i;
			i = 0;
			startNum = 0;
			endNum = 500;

			while (i < resultCounter) {
				PreparedStatement res;
				res = connection
						.prepareStatement("SELECT textual_content FROM " + optimized + ".item "
										+ "limit ?, ?");
				res.setInt(1, startNum);
				res.setInt(2, endNum);
				result = res.executeQuery();
				while (result.next()) {
					tweet.push(result.getString("textual_content"));
					i++;
					result.next();

				}
				startNum = endNum + 1;
				if (endNum + 500 > resultCounter)
					endNum = resultCounter;
				else
					endNum = +500;

			}
			System.out.println("the number of tweet is: " + resultCounter);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	private void emotionFinder() {
		
		try {
			
			String testedTweet = null;
			while (!tweet.isEmpty()) {
				testedTweet = tweet.pop();
				for (int i = 0; i < emotionWord.length; i++) {
					if (testedTweet.contains(emotionWord[i])) {
						emotionWeight[i][2]++;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void calculateEmotionalScoreForOneCity( ) {

		
		try {
	
			for (int i = 0; i < emotionWeight.length; i++) {
				if (emotionWeight[i][2]==0){
					continue;
				}
				
				System.out.println("This Emotion: "+emotionWord[i]+" "
						+ "ID is: "+emotionWeight[i][0]+" "
						+ "Weight is: "+emotionWeight[i][1]+" "
						+ "Used: "+emotionWeight[i][2]+" Time "
						+ "The Total Weight is: "+(emotionWeight[i][1]*emotionWeight[i][2]));
				
				if (emotionWeight[i][1] < 0) {
					negativeNum=negativeNum+emotionWeight[i][2];
					negativeEmotion =negativeEmotion+ (emotionWeight[i][1]*emotionWeight[i][2]);
				} else {
					positiveNum=positiveNum+emotionWeight[i][2];
					positiveEmotion =positiveEmotion+ (emotionWeight[i][1]*emotionWeight[i][2]);
				}
			}//end of for
			//TODO fix the problem
		

			System.out.println("\nThe Total Number Emotions is: " + (positiveNum + negativeNum) + " | " 
			+ "positive= "
			+ positiveNum + " & Negative= " + negativeNum);
			
			System.out.println("\nthe total positive Emotion is: "+positiveEmotion+" "
					+ "& the total negativeEmotion is: "+negativeEmotion);
			
			
			double positive = (double)((positiveEmotion * 100) / ((double)(Math.abs(positiveEmotion) + Math.abs(negativeEmotion))));
			double negative = ((double)(Math.abs(negativeEmotion) * 100) / (double)((Math.abs(positiveEmotion) +Math.abs(negativeEmotion))));

			System.out.println("\n"+ Math.round(positive)+ "% Positive "
					+ Math.rint(negative) + "% Negative");

			positiveEmotion = positiveEmotion + positiveEmotion;
			negativeEmotion = negativeEmotion + negativeEmotion;
			positiveNum = positiveNum + positiveNum;
			negativeNum = negativeNum + negativeNum;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
