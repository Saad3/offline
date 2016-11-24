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
	private String[][] emotin;
	private Stack<String> tweet;
	private int resultCounter;

	private int positiveEmotin, negativeEmotin, positiveNum, negativeNum;

	public Algorithm(String url, String username, String password, String offline, String optimized) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			this.offline = offline;
			this.optimized = optimized;
			tweet = new Stack<String>();

			resultCounter = 0;

			positiveEmotin = negativeEmotin = positiveNum = negativeNum = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startAgorthm() {

		retrieveEmotion();
		retrevtweets();

		calculateEmotionalScore();

		System.out.println(positiveEmotin + "\n" + positiveNum);
		System.out.println(negativeEmotin + "\n" + negativeNum);
	}

	public void algorithm() {

		try {
			int maximumIteration = 0;
			int t = 0;
			while (t <= maximumIteration) {
				for (int U = 0; U < emotin.length; U++) {// U Sets of users.
					for (int I = 0; I < emotin.length; I++) {// I Sets of items.
						for (int T = 0; T < emotin.length; T++) {// T Sets of
																	// tags.
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
		String testedTweet = null;
		int temp = 0;
		while (!tweet.isEmpty()) {
			testedTweet = tweet.pop();
			for (int i = 0; i < emotin.length; i++) {
				if (testedTweet.contains(emotin[i][0])) {
					temp = Integer.parseInt(emotin[i][1]);// casting String t
															// int
					if (temp < 0) {
						negativeNum++;
						negativeEmotin = negativeEmotin + temp;
					} else {
						positiveNum++;
						positiveEmotin = positiveEmotin + temp;
					}

				}
			}
		}
	}

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
						.prepareStatement("SELECT textual_content FROM " + optimized + ".item WHERE 1 limit ?, ?");
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

			if (retrieveEmotion()) {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean retrieveEmotion() {
		try {

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM " + optimized + ".emotion"); // to
																									// retrieve
																									// the
																									// emotion
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM " + optimized + ".emotion "); // to
																													// know
																													// how
																													// many
																													// emotion
																													// we
																													// have
			result = countQuery.executeQuery();
			result.next();
			emotin = new String[result.getInt("total")][2];

			result = retrieveQuery.executeQuery();

			for (int i = 0; i < emotin.length; i++) { // to store the emotion in
														// array
				result.next();
				emotin[i][0] = result.getString("keyword");// to insert it in
															// the table "has
															// emo"
				emotin[i][1] = result.getString("weight");
			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
