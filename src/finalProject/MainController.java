package finalProject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class MainController {

	public static void main(String[] args){



		//twitter Authentication
		String ConsumerKey = "v7cIWGoF4tnB8xCX2VQDvFfeg";
		String ConsumerSecret = "QluGAaYXULyo9GBJT7G5CFDOPqOhrMhKIUiPNWlBjTqf3sPv8Y";
		String AccessToken ="245278759-XnJon4fbGFNYdbW15nDZU4jt2syx0CFMTfbWpGRG";
		String AccessTokenSecret="nDo3Rl3IGTMtweBhOEoize2L8vDrfnnDRbQ6yU9FyvvoV"; 

		ConfigurationBuilder ob =new ConfigurationBuilder(); 
		ob.setDebugEnabled(true)
		.setOAuthConsumerKey(ConsumerKey)
		.setOAuthConsumerSecret(ConsumerSecret)
		.setOAuthAccessToken(AccessToken)
		.setOAuthAccessTokenSecret(AccessTokenSecret);
		Twitter twitter = new TwitterFactory(ob.build()).getInstance();




		// DB connection
		DbConnection db = new DbConnection("jdbc:mysql://localhost:3306/test?useUnicode=yes&characterEncoding=UTF-8","username","",twitter);

		TagsGenerator tagsGenerator ;
		Listener listener ;


		try {
			while(true){
				
				tagsGenerator = new TagsGenerator(ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret);
				listener = new Listener(db,ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret);
				
				System.out.println("The system will listen on:");
				String key[] = tagsGenerator.generatTags(10);
				for (String string : key) {
					System.out.println(string);
				}
				listener.startListening(tagsGenerator.generatTags(3));
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
