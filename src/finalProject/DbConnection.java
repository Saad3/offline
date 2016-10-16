package finalProject;


import java.sql.*;

import twitter4j.*;

public class DbConnection {

	private Connection connection;
	private Statement statement;
	private ResultSet result;
	private Twitter twitter;

	public DbConnection(String url,String username, String password ) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}	

	}

	public DbConnection(String url,String username, String password, Twitter twitter) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}		}

	public void storStatus(Status item){


		try{	


			itemQuery(item);
			geotagQuery(item);
			visualQuery(item);
			if(item.getRetweetCount()!=0)
			System.out.println(item.getRetweetCount()+"");

			if (item.isRetweeted()){
				System.out.println("item.isRetweeted()");
				republishQuery(item);

			}
	

			tagsQuery(item);
			userQuery(item);

		} catch (Exception ex) {
			System.out.println(ex+"  Erorr in storStatus method");
		}
	}

	private void itemQuery(Status item){
		try{

			PreparedStatement itemQuery;
			//the query to insert in the DB
			itemQuery = connection.prepareStatement("INSERT INTO `item`(`item_id`, `user_id`, `textual_content`,`url_count`, `tag_count`, `timestamp`) VALUES (?,?,?,?,?,?)");
			itemQuery.setString(1, item.getId()+""); //insert the item id
			itemQuery.setString(2, item.getUser().getId()+"");//insert user id
			itemQuery.setString(3, item.getText());//insert text
			if (item.getURLEntities().length!=0){
				itemQuery.setInt(4, item.getURLEntities().length);
			}else{
				itemQuery.setInt(4,0);
			}
			if (item.getHashtagEntities().length!=0){
				itemQuery.setInt(5, item.getHashtagEntities().length);
			}else{
				itemQuery.setInt(5, 0);
			}
			
			itemQuery.setString(6, item.getCreatedAt()+"");//insert timestamp 
			itemQuery.executeUpdate();

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			System.out.println("itemQuery");

		}	

	}

	private void userQuery(Status item){

		try{
			result = statement.executeQuery("SELECT * FROM user WHERE user_id ="+item.getUser().getId());
			if(!result.next()){

				String location=null;
				if (item.getUser().getLocation()!=null) {
					location = item.getUser().getLocation(); //location of the user
				}

				PreparedStatement userQuery;
				userQuery = connection.prepareStatement("INSERT INTO `user`(`user_id`, `user_name`, `location`)VALUES (?,?,?)");
				userQuery.setString(1, item.getUser().getId()+"");//insert user id
				userQuery.setString(2, item.getUser().getScreenName());//insert username
				userQuery.setString(3, location);//insert location
				userQuery.executeUpdate();

			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			System.out.println("userQuery");
		}	

	}

	private void geotagQuery(Status item){
		try{
			if (item.getGeoLocation()!=null){
				PreparedStatement geotagQuery;
				geotagQuery = connection.prepareStatement("INSERT INTO `geotag`(`item_id`, `latitude`, `longitude`) VALUES (?,?,?)");
				geotagQuery.setString(1, item.getId()+"");
				geotagQuery.setDouble(2, item.getGeoLocation().getLatitude());
				geotagQuery.setDouble(3, item.getGeoLocation().getLongitude());
				geotagQuery.executeUpdate();

			}			
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			System.out.println("geotagQuery");

		}	

	}

	private void visualQuery(Status item){
		try{
			if (item.getMediaEntities().length!=0){// check if there is visual url

				PreparedStatement visualQuery;
				visualQuery = connection.prepareStatement("INSERT INTO `visual`(`item_id`, `visual_URL`) VALUES (?,?)");

				for (int i = 0; i < item.getMediaEntities().length; i++) {
					visualQuery.setString(1, item.getId()+"");
					visualQuery.setString(2, item.getMediaEntities()[i].getMediaURL());
					visualQuery.executeUpdate();
				}
			}			
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			System.out.println("visualQuery");
		}	

	}


	private void republishQuery(Status item){
		try{
			IDs id ;
			id = twitter.getRetweeterIds(item.getId(),-1);
			long[] ids = id.getIDs();
			PreparedStatement republishQuery;
			republishQuery = connection.prepareStatement("INSERT INTO `republish`(`item_id`,`user_id`) VALUES (?,?)");
			for (int i = 0; i < ids.length; i++) {

				republishQuery.setString(1, item.getId()+"");
				republishQuery.setString(2, ids[i]+"");
				republishQuery.executeUpdate();
				System.out.println("republishQuery");
			}

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}	

	}

	/*

	private void likeQuery(String item_id, String user_id){
		try{

			PreparedStatement likeQuery;
			likeQuery = connection.prepareStatement("INSERT INTO `like`(`item_id`,`user_id`) VALUES (?,?)");
			likeQuery.setString(1, item_id);
			likeQuery.setString(2, user_id);
			likeQuery.executeUpdate();
			System.out.println("likeQuery");

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}	

	}
*/
	
	private void tagsQuery(Status item){
		try{

			if (item.getHashtagEntities().length!=0){//check if there is hashtags
				int oldTagID[]=new int[item.getHashtagEntities().length];
				for (int i = 0; i < item.getHashtagEntities().length; i++) {

					//Search for tag id if it there
					PreparedStatement res;							
					res = connection.prepareStatement("SELECT * FROM tags WHERE keyword =?");
					res.setString(1, "#"+item.getHashtagEntities()[i].getText());
					result=res.executeQuery();

					if(!result.next()){// no tag like it

						//insert new tag
						PreparedStatement tagsQuery;
						tagsQuery = connection.prepareStatement("INSERT INTO `tags`(`keyword`) VALUES (?)");
						tagsQuery.setString(1, "#"+item.getHashtagEntities()[i].getText());
						tagsQuery.executeUpdate();

						if(i==0){//to repeat the loop to get the tag id 
							i=0;
						}
						else{
							i--;
						}

					}
					else{
						//
						boolean oldTag=true;
						for (int j = 0; j < oldTagID.length; j++) { //check for stupid people who use the same hashtag more than ones
							if(oldTagID[j]==result.getInt("tag_id"))
								oldTag=false;
						}
						oldTagID[i]=result.getInt("tag_id");

						//fixed repeated tags in same tweet

						if(oldTag){	
							PreparedStatement containQuery;
							containQuery = connection.prepareStatement("INSERT INTO `contain`(`item_id`,`tag_id`) VALUES (?,?)");
							containQuery.setString(1, item.getId()+"");
							containQuery.setInt(2,result.getInt("tag_id"));
							containQuery.executeUpdate();
						}
					}

				}
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			System.out.println("tagsQuery");

		}	

	}

	public void storStatus1(Status status){
		//incomingTweet2 item = new incomingTweet2(status);

		//String tweetQuery ="INSERT INTO `item`(`item_id`, `textual_content`, `visual_content`, `geotag`, `timestamp`) "
		//		+ "VALUES ("+item.getItemId()+",'"+item.getTextualContent()+"','"+item.getVisualContent()+"','"+item.getGeoTags()+"','"+item.getTimestamp()+"')";



	}


	public void storStatus(Status item, Twitter twitter) {
		// TODO Auto-generated method stub

	}














}


