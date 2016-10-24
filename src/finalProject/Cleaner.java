package finalProject;

import java.sql.*;

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
		retrieveCityNames();
		if (city.length != 0) {
		cleanCityName();
		}
		copier();
	}
	
	private void copier(){
		//this method res
		try {
			
			PreparedStatement query;
			query = connection.prepareStatement("INSERT INTO "+optimized+".user SELECT * FROM "+offline+".user_opt "); //copy user
			query.executeUpdate();
			query = connection.prepareStatement("INSERT INTO  "+optimized+".item SELECT * FROM "+offline+".item_opt"); //copy item
			query.executeUpdate();
			query = connection.prepareStatement("INSERT INTO  "+optimized+".contain SELECT * FROM "+offline+".contain_opt"); //copy contain
			query.executeUpdate();
			query = connection.prepareStatement("INSERT INTO  "+optimized+".tags SELECT * FROM "+offline+".tags"); //copy tag
			query.executeUpdate();
			query = connection.prepareStatement("INSERT INTO  "+optimized+".geotag SELECT * FROM "+offline+".geotag"); //copy tag
			query.executeUpdate();
		
		
		
		} catch (SQLException e) {
			System.out.println("copier method");
			e.printStackTrace();
		}

		
	}
	
	// item cleaner

	// user cleaner
	private void retrieveCityNames() {
		try {

			PreparedStatement countQuery, retrieveQuery;
			retrieveQuery = connection.prepareStatement("SELECT * FROM `cities` WHERE 1");
			countQuery = connection.prepareStatement("SELECT COUNT(*)AS total FROM `cities` ");
			result = countQuery.executeQuery();
			result.next();
			city = new String[result.getInt("total")][3];

			result = retrieveQuery.executeQuery();

			// 0 will hold states number 1 will hold city name in arabic 2 in english
			//while (result.next()) {
				
				for (int i = 0; i < city.length; i++) {
					result.next();
					city[i][0] = result.getString("parent_id");
					city[i][1] = result.getString("arabic");
					city[i][2] = result.getString("english");

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
				query = connection.prepareStatement("UPDATE "+offline+".user SET `city`=?,`states`=? WHERE (location LIKE ? OR location LIKE ?)");
				query.setString(1,city[i][1]);
				query.setString(2,city[i][0]);
				query.setString(3,"%"+city[i][1]+"%");//one for English
				query.setString(4,"%"+city[i][2]+"%");
				query.executeUpdate();

			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		;

	}
}
