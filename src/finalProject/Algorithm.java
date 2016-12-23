package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class Algorithm {

	private Connection connection;
	private ResultSet result;
	private String optimized;
	private String[] emotionWords;
	private int[][] emotionIdAndWeight;
	private int[][] stateEmotionMatrix;
	private int[][] resultSetForStates;
	private Stack<String> tweet;
	private int resultCounter;
	private int numberOfStates;
	private String[][] statesName;
	private int[] tweetsCounter;
	
	
	public Algorithm(String url, String username, String password,  String optimized) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			this.optimized = optimized;
			tweet = new Stack<String>();

			resultCounter = 0;
			numberOfStates=0;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startAgorthm() {
		setResultCounter();
		calculateEmotionalScoreForState();
	//	printTheResultForAllStates();
		System.out.println("Done !!!!!!!");
	
	}

	private void calculateEmotionalScoreForState() { 
		
		// you can't call this method before calling retrieveCityNames()

		try {
			setNumberOfStates();

			setResultArray(numberOfStates);

			setTweetsCounter(numberOfStates);

			retrieveStatesNames();

			retrieveEmotion();
			
			setStateEmotionArray(emotionIdAndWeight, numberOfStates);
			
			System.out.println("Foundational phase is Done!");

			for (int i = 0; i < statesName.length; i++) {

				testRetrieveTweetsForOneStates(statesName[i][0]);
				emotionFinderForStates(i);
				calculateEmotionalScoreForOneState(i);
				printTheResultForOneState(i);
				
			}
			printAll();

		} catch (Exception e) {
			System.out.println("error in calculateEmotionalScoreForState()");
			e.printStackTrace();
		}
	}
	

	private void setNumberOfStates(){
		
		try {
			
			PreparedStatement countQuery;
			countQuery = connection.prepareStatement("SELECT  COUNT(DISTINCT `" + optimized + "`.`cities`.`parent_id`) AS total "
					+ "FROM `" + optimized + "`.`cities`"); // retrieve the number of states

			result = countQuery.executeQuery();
			result.next();
			numberOfStates=result.getInt("total");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setResultArray(int numberOfStates) {
		resultSetForStates = new int[numberOfStates][5];//0 for id  1positiveNum for , 2 for positiveEmotion 3 for negativeNum , , 4 for negativeEmotion
		
	}

	private void setTweetsCounter(int numberOfStates) {
		tweetsCounter = new int[numberOfStates];
	}

	private void retrieveStatesNames() {
		try {
			
			PreparedStatement  retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT  `" + optimized + "`.`cities`.`parent_id`, `" + optimized + "`.`cities`.`id`, `" + optimized + "`.`cities`.`arabic`, `" + optimized + "`.`cities`.`english` "
					+ "FROM `" + optimized + "`.`cities` "
					+ "GROUP BY `" + optimized + "`.`cities`.`parent_id`");
			
			statesName = new String[numberOfStates][3];

			result = retrieveQuery.executeQuery();
			
			// 0 will hold states id  1 will hold state name in Arabic 2 in English
			for (int i = 0; i < statesName.length; i++) {
				result.next();
				statesName[i][0] = result.getString("id");
				statesName[i][1] = result.getString("arabic");
				statesName[i][2] = result.getString("english");
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void retrieveEmotion() {
		try {
			//TODO you can write method add new emotion

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM " + optimized + ".emotion"); // to retrieve the emotion
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM " + optimized + ".emotion "); // to know how many emotion we have
			result = countQuery.executeQuery();
			result.next();
			emotionWords = new String[result.getInt("total")];
			emotionIdAndWeight = new int[result.getInt("total")][3];
			result = retrieveQuery.executeQuery();

			for (int i = 0; i < emotionWords.length; i++) { // to store the emotion in array
				result.next();
				emotionWords[i] = result.getString("keyword");// to insert it in the table "has emo"
				emotionIdAndWeight[i][0] = result.getInt("keyword_id");
				emotionIdAndWeight[i][1] = result.getInt("weight");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	private void setStateEmotionArray(int[][] originalMatrix ,int widthOfNewMatrix){
		stateEmotionMatrix = new int [originalMatrix.length][widthOfNewMatrix];
			
	}
	
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
	
 	private void testRetrieveTweetsForOneStates(String states_id){
 		
 		try {
 			int id = Integer.parseInt(states_id);
			PreparedStatement res;
			res = connection.prepareStatement(
					"SELECT `" + optimized + "`.`item`.`textual_content`" + "FROM `" + optimized + "`.`item` , `"
							+ optimized + "`.`user`" + "WHERE `" + optimized + "`.`user`.`states_id` = ?" + "AND `"
							+ optimized + "`.`user`.`user_id`=`" + optimized + "`.`item`.`user_id` ");
			
			res.setString(1, states_id);
			result = res.executeQuery();

			while (result.next()) {
				tweet.push(result.getString("textual_content"));
				tweetsCounter[id]++;
				result.next();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 	}
 	
 	private void test1RetrieveTweetsForOneStates(String states_id){
 		
 		try {
 			int id = Integer.parseInt(states_id);
			PreparedStatement res;
			res = connection.prepareStatement(
					"SELECT `" + optimized + "`.`item`.`textual_content`" 
							+ "FROM `" + optimized + "`.`item` "
							+ "WHERE `" + optimized + "`.`item`.`states_id` = ?" );
			
			res.setString(1, states_id);
			result = res.executeQuery();

			while (result.next()) {
				tweet.push(result.getString("textual_content"));
				tweetsCounter[id]++;
				result.next();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 	}
	
	private void emotionFinderForStates(int stateId) {
		
		try {
			
			String testedTweet = null;
			while (!tweet.isEmpty()) {
				testedTweet = tweet.pop();
				for (int i = 0; i < emotionWords.length; i++) {
					if (testedTweet.contains(emotionWords[i])) {
						emotionIdAndWeight[i][2]++;
						stateEmotionMatrix[i][stateId]++;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void calculateEmotionalScoreForOneState(int stateId) {

		try {

			for (int i = 0; i < stateEmotionMatrix.length; i++) {
				calculateEmotion(stateId, emotionIdAndWeight[i][1], stateEmotionMatrix[i][stateId]);
			} // end of for

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	private void calculateEmotion (int stateId, int weight,int value){
		
		if (weight > 0) {
			resultSetForStates[stateId][1] =resultSetForStates[stateId][1]+ value;
			resultSetForStates[stateId][2]=resultSetForStates[stateId][2]+(weight*value);
		} else {
			resultSetForStates[stateId][3] =resultSetForStates[stateId][3]+value;
			resultSetForStates[stateId][4]=resultSetForStates[stateId][4]+(weight*value);
		}
	}


	private void printTheResultForOneState(int stateId) {
		try {
			
			System.out.println("\nThe Result For "+ statesName[stateId][2]+" is:\n");
			printEmotionResult(stateId);
			printOverallEmotion(stateId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	private void printEmotionResult(int stateId) {

		System.out.println("The Total Number Of tweets is :"+tweetsCounter[stateId]);
		System.out.println("The Number Emotions is: " + (resultSetForStates[stateId][1] + resultSetForStates[stateId][3]));
		System.out.println("The Number Positive Emotions is: " + resultSetForStates[stateId][1]);
		System.out.println("The Number Negative Emotions is: "+  resultSetForStates[stateId][3]);

		System.out.println("The Total Positive Emotion Wight is: " + resultSetForStates[stateId][2]);
		System.out.println("The Total Negative Emotion Wight is: " + resultSetForStates[stateId][4]);

	}
	
	
	private void printOverallEmotion(int stateId) {

		double positive = (double) ((resultSetForStates[stateId][2] * 100)
				/ (double) (resultSetForStates[stateId][2] + Math.abs(resultSetForStates[stateId][4])));
		double negative = (double) (Math.abs(resultSetForStates[stateId][4]) * 100)
				/ (double) (resultSetForStates[stateId][2] + Math.abs(resultSetForStates[stateId][4]));
	
		System.out.print("Overall Result For This state is : ");
		System.out.print(Math.round(positive) + "% Positive And ");
		System.out.print(Math.round(negative) + "% Negative \n\n");
		
	}
	
	private void printAll(){
		
		
	}
	
	
	
	
	
	
	

	private int setResultCounterForOneState(String states_id ) {// TODO fix

		try {

			PreparedStatement res;
			res = connection.prepareStatement("SELECT  COUNT(*) AS total "
					+ "FROM `" + optimized + "`.`item`,`" + optimized + "`.`user` "
					+ "WHERE `" + optimized + "`.`user`.`states_id`=?"
					+ "AND `" + optimized + "`.`user`.`user_id`=`" + optimized + "`.`item`.`user_id`");
			
			res.setString(1, states_id);
			result = res.executeQuery();
			result.next();
			return result.getInt("total");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



	/*private void retrevtweets() {
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
				for (int i = 0; i < emotionWords.length; i++) {
					if (testedTweet.contains(emotionWords[i])) {
						emotionIdAndWeight[i][2]++;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private void calculateEmotionalScoreForOneCity() {

		int id=0;
		try {
	
			for (int i = 0; i < emotionIdAndWeight.length; i++) {
				if (emotionIdAndWeight[i][2]==0){
					continue;
				}
				
				System.out.println("This Emotion: "+emotionWords[i]+" "
						+ "ID is: "+emotionIdAndWeight[i][0]+" "
						+ "Weight is: "+emotionIdAndWeight[i][1]+" "
						+ "Used: "+emotionIdAndWeight[i][2]+" Time "
						+ "The Total Weight is: "+(calculateTotalWeight(emotionIdAndWeight[i][1],emotionIdAndWeight[i][2])));
			
				calculateEmotion(id,emotionIdAndWeight[i][1],emotionIdAndWeight[i][2]);
				
			}//end of for
			
			calculateOverallEmotion(id);

			
			printEmotionResult(id);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	*/

 	private int calculateTotalWeight(int weight,int value) {
		return  weight*value;
	}

	private void printResultWithKeywordDetail(int keyWordId, int stateId){
		
		
		System.out.println("This Emotion: "+emotionWords[keyWordId]+" "
				+ "ID is: "+emotionIdAndWeight[keyWordId][0]+" "
				+ "Weight is: "+emotionIdAndWeight[keyWordId][1]+" "
				+ "Used: "+stateEmotionMatrix[keyWordId][stateId]+" Time "
				+ "The Total Weight is: "+
				(calculateTotalWeight(emotionIdAndWeight[keyWordId][1], stateEmotionMatrix[keyWordId][stateId])));
		
	}

	public void setResultCounter() {// TODO fix

		try {

			PreparedStatement res;
			res = connection.prepareStatement("SELECT COUNT(*) AS total FROM " + optimized + ".item WHERE 1");
			result = res.executeQuery();
			result.next();
			resultCounter = result.getInt("total");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

 	private void retrieveTweetsForOneStates(String states_id) {
		
		try {
			
			
			int startNum, endNum, i;
			i = 0;
			startNum = 0;
			endNum = 500;

			while (i < resultCounter) {
				
				PreparedStatement res;
				res = connection.prepareStatement("SELECT `" + optimized + "`.`item`.`textual_content`"
						+ "FROM `" + optimized + "`.`item` , `" + optimized + "`.`user`"
						+ "WHERE `" + optimized + "`.`user`.`states_id` = ?"
						+ "AND `" + optimized + "`.`user`.`user_id`=`" + optimized + "`.`item`.`user_id` "
						+ "limit ?, ?"); 
				
				res.setString(1, states_id);
				res.setInt(2, startNum);
				res.setInt(3, endNum);
				
				result = res.executeQuery();
				
				while (result.next()) {
					tweet.push(result.getString("textual_content"));
					//i++;
					result.next();
				}
			
				startNum = endNum + 1;
				
				if (endNum + 500 > resultCounter)
					endNum = resultCounter;
				else
					endNum = +500;

			}
			
			
			System.out.println("Finish retrieve the tweets");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	

	
}
