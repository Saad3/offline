package finalProject;

import java.sql.*;
import java.util.Scanner;

public class Cleaner {

	private Connection connection;
//	private Statement statement;
	private ResultSet result;
	private String[][] city;
	private String offline;
	private String optimized;

	public Cleaner(String url, String username, String password, String offline, String optimized) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			//statement = connection.createStatement();
			this.offline=offline;
			this.optimized=optimized;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void startClean() {
		
		try {
			System.out.println("Enter 1 to find the city form the user pio or user location\n"
					+ "Enter 2 to Start copying the Data to the optmized DB\n"
					+ "Or Enter 0 to exit the Cleaner\n");
			
			Scanner input = new Scanner(System.in);
			int key=0;
			key = input.nextInt();
			switch (key) {
			case 1:
				retrieveCityNames();
				if (city.length != 0) {
				cleanCityName();
				System.out.println("Done !!");
				}
				break;
			case 2:
				copier();
				break;
			case 0:
				break;
					
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void copier(){
		//this method res
		try {
			
			PreparedStatement query;
			query = connection.prepareStatement("INSERT INTO  "+optimized+".item "
					+ "SELECT offline.item.item_id , offline.item.user_id , offline.item.textual_content ,offline.item.tag_count ,offline.item.timestamp "
					+ "FROM offline.item "
					+ "WHERE ((offline.item.url_count < 2 ) "
					+ "and ("+offline+".item.tag_count < 4 )) "); //copy item
			
			query.executeUpdate();
			System.out.println("Copying the item is done!");
			
			query = connection.prepareStatement("INSERT INTO "+optimized+".user "
					+ "SELECT DISTINCT "+offline+".user.user_id ,"+offline+".user.city_id ,"+offline+".user.states_id "
					+ "FROM "+offline+".user"); //copy user
			query.executeUpdate();
			System.out.println("Copying the user is done!");
				
			query = connection.prepareStatement("INSERT INTO `"+optimized+"`.`contain` "
					+ "SELECT "+offline+".`contain`.`item_id`, "+offline+".`contain`.`tag_id` "
					+ "FROM "+offline+".`contain` , `"+optimized+"`.`item` "
					+ "WHERE   "+offline+".`contain`.`item_id` = "+optimized+".`item`.`item_id`"); //copy contain
			
			query.executeUpdate();
			System.out.println("Copying the contain is done! ");
			
			query = connection.prepareStatement("INSERT INTO "+optimized+".`tags` "
					+ "SELECT DISTINCT "+offline+".`tags`.`tag_id` , "+offline+".`tags`.`keyword`, "+offline+".`tags`.`add_time`  "
					+ "FROM "+offline+".`tags`, "+optimized+".`contain` "
					+ "WHERE "+offline+".`tags`.`tag_id` = "+optimized+".`contain`.`tag_id`"); //copy tag
			query.executeUpdate();
			System.out.println("Copying the tags is done!");
			
			query = connection.prepareStatement("INSERT INTO `optimized_offline_db`.`geotag` "
					+ "SELECT `offline`.`geotag`.`item_id` , `offline`.`geotag`.`latitude` , `offline`.`geotag`.`longitude` "
					+ "FROM `offline`.`geotag` , `optimized_offline_db`.`item` "
					+ "WHERE `offline`.`geotag`.`item_id`=`optimized_offline_db`.`item`.`item_id`"); //copy tag
			query.executeUpdate();
			
			System.out.println("Copying the geotag is done!");

			
		
		
		
		} catch (SQLException e) {
			System.out.println("copier method");
			e.printStackTrace();
		}

		
	}
	
	private void retrieveCityNames() {
		try {

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM `cities` WHERE 1");
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM `cities` ");
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

	private void cleanCityName(){
		try {
			
			
			PreparedStatement query;
			for (int i = 0; i < city.length; i++) {
				query = connection.prepareStatement("UPDATE "+offline+".user SET `city_id`=?,`states_id`=? WHERE "
						+ "(location LIKE ? OR location LIKE ? "
						+ "OR user_pio LIKE ? OR user_pio LIKE ?)");
				query.setString(1,city[i][0]);
				query.setString(2,city[i][1]);
				query.setString(3,"%"+city[i][2]+"%");//one for English
				query.setString(4,"%"+city[i][3]+"%");
				query.setString(5,"%"+city[i][2]+"%");//one for English
				query.setString(6,"%"+city[i][3]+"%");
				query.executeUpdate();

			}
			
			
			
			//update null value
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		;

	}
}
