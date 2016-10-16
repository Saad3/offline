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

/*





		TwitterStream twitterStream = new TwitterStreamFactory(ob.build()).getInstance();

		DbConnection db = new DbConnection("jdbc:mysql://localhost:3306/test?useUnicode=yes&characterEncoding=UTF-8","username","");


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

		FilterQuery fq=new FilterQuery();
		twitterStream.addListener(listener);
		String keywords[] = {"#Ø§Ù„Ø§Ù�Ø¶Ù„_Ø§Ù„Ø´ØªØ§Ø¡_Ø§Ù…_Ø§Ù„ØµÙŠÙ�","#Ø±Ø§Ø­_Ù†Ù�Ù„Ø³ÙƒÙ…_7"};
		fq.track(keywords);
		twitterStream.filter(fq);
		
		
		
		
		
/*
		try {


			while(true){
				Twitter twitter  = new TwitterFactory(cb.build()).getInstance();
				Trends trends = twitter.getPlaceTrends(23424938);//saudi tag
				String keywords[] = {trends.getTrends()[0].getName(),trends.getTrends()[1].getName(),trends.getTrends()[2].getName()};
				fq.track(keywords);
				twitterStream.filter(fq);
				System.out.println("we will lesten on");
				System.out.println(trends.getTrends()[0].getName()+"::"+trends.getTrends()[1].getName()+"::"+trends.getTrends()[2].getName());
				TimeUnit.MINUTES.sleep(5);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/



	//end of main

