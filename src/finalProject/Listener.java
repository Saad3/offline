package finalProject;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class Listener {
	

	private DbConnection db;
	private TwitterStream twitterStream;	

	public Listener(DbConnection db, String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
	
		this.db=db;
		
		ConfigurationBuilder ob =new ConfigurationBuilder(); 
		
		ob.setDebugEnabled(true)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken)
		.setOAuthAccessTokenSecret(accessTokenSecret);
		
		twitterStream = new TwitterStreamFactory(ob.build()).getInstance();	
	
	}
	
	public void startListening(String keywords[]){

		FilterQuery fq=new FilterQuery();
		twitterStream.addListener(statusListener(db));
		fq.track(keywords);
		twitterStream.filter(fq);
	}
	
	public void stop(){
		twitterStream.shutdown();
	}
	
	private StatusListener statusListener(DbConnection db){
		
		StatusListener listener = new StatusListener() {

			@Override
			public void onStatus(Status status) {
				db.storStatus(status);

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
			}

			@Override
			public void onStallWarning(StallWarning warning) {
			}

			@Override
			public void onException(Exception ex) {
			}
		};
		return listener;
		
	}
}

//http://stackoverflow.com/questions/35727753/fetching-all-tweets-using-twitter4j-around-a-given-location