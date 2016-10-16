package finalProject;


import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public final class TagsGenerator {

	private Twitter twitter;
	private Trends trends;
	private ConfigurationBuilder ob;
	public TagsGenerator(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {

		ob =new ConfigurationBuilder(); 
		ob.setDebugEnabled(true)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken)
		.setOAuthAccessTokenSecret(accessTokenSecret);
		twitter = new TwitterFactory(ob.build()).getInstance();
		try {
			trends = twitter.getPlaceTrends(23424938);	//23424938 SA
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public String[] generatTags(int numerOfTags){
		//check for the valid number

		String keywords[];

		if(!(numerOfTags<=trends.getTrends().length)){
			numerOfTags=trends.getTrends().length;

		}

		keywords = new String[numerOfTags];

		for (int i = 0; i < keywords.length; i++) {
			keywords[i]= trends.getTrends()[i].getName();
		} 

		return keywords;

	}
}