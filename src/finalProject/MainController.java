package finalProject;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class MainController {
	private static String originalDB = "jdbc:mysql://localhost:3306/offline?useUnicode=yes&characterEncoding=UTF-8";

	public static void main(String[] args){
		
		
		while(true){

		Scanner input = new Scanner(System.in);

		System.out.println(""
				+ "Welcome To Our Application!\n"
				+ "Please Enter Your Choice:\n"
				+ "1. Start Collecting Data Using Tags Generator\n"
				+ "2. Start Collecting Data Using Dynamic Tags\n"
				+ "3.Run The Cleaner\n"
				+ "99. Run The Algorithm\n"
				+ "OR 0. Exit The Application"
				+ "");
		int key=0;
			key = input.nextInt();
			switch (key) {
			
			case 1:
				collector();
				break;
				
			case 2:
				dynamicTags();
				break;
			
			case 3:
				cleaner();
				break;
				
			case 99:
				algorthm();
				break;

			default:
				input.close();
				System.exit(0);
				break;
			}

		}



	}

	private static void setTags(String tags[]) {

		//twitter Authentication
		String ConsumerKey = "v7cIWGoF4tnB8xCX2VQDvFfeg";
		String ConsumerSecret = "QluGAaYXULyo9GBJT7G5CFDOPqOhrMhKIUiPNWlBjTqf3sPv8Y";
		String AccessToken ="245278759-XnJon4fbGFNYdbW15nDZU4jt2syx0CFMTfbWpGRG";
		String AccessTokenSecret="nDo3Rl3IGTMtweBhOEoize2L8vDrfnnDRbQ6yU9FyvvoV"; 



		// DB connection
		DbConnection db = new DbConnection(originalDB,"root","","offline");
		
		Listener listener ;


		try {
		

				listener = new Listener(db,ConsumerKey,ConsumerSecret,AccessToken,AccessTokenSecret);
				System.out.println("The system will listen on:");
				listener.startListening(tags);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void collector(){


		//twitter Authentication
		String ConsumerKey = "v7cIWGoF4tnB8xCX2VQDvFfeg";
		String ConsumerSecret = "QluGAaYXULyo9GBJT7G5CFDOPqOhrMhKIUiPNWlBjTqf3sPv8Y";
		String AccessToken ="245278759-XnJon4fbGFNYdbW15nDZU4jt2syx0CFMTfbWpGRG";
		String AccessTokenSecret="nDo3Rl3IGTMtweBhOEoize2L8vDrfnnDRbQ6yU9FyvvoV"; 



		// DB connection
		DbConnection db = new DbConnection(originalDB,"root","","offline");

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
				listener.startListening(tagsGenerator.generatTags(10));
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

	private static void cleaner(){
		
		System.out.println("System will start clening now!");
		Cleaner cleaner = new Cleaner(originalDB,"root","","offline","optimized_offline_db");
		
		cleaner.startClean();
		
		System.out.println("Cleaning is Finished!");
		
	}

	private static void algorthm(){
		Algorithm algorithm = new Algorithm(originalDB,"root","","optimized_offline_db");
		
		algorithm.startAgorthm();
		
	}


	private static void dynamicTags(){
	
		
		try {
			
			
			
			while (true) {		
				System.out.println("listening list will be updated now !");
				setTags(TagList());
				TimeUnit.HOURS.sleep(setSleeptime()); // check listener.stop();

			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	

	private static long setSleeptime() {
		return 0;
	}

	private static String[] TagList(){
		
		int numberOfTags =0;
		
		String tags[] = new String[numberOfTags];
		
		//TODO sql
		
		
		
		
		return tags;
	}
	
//				String tags[] = {"مطر","امطار","أمطار","المطر","الامطار","الأمطار","السيل","السيول","غرق","الجو","فيضان","سيل","سيول","امطار","الامطار","الشتاء"};



}
