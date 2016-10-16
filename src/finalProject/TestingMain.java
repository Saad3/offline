package finalProject;

import java.sql.*;

public class TestingMain {

	public static void main(String[] args) {
		Connection connection;
		Statement statement;
		ResultSet result;
		try {
		String password="";
		String username="root";
		String url="jdbc:mysql://localhost:3306/offline?useUnicode=yes&characterEncoding=UTF-8";
		connection = DriverManager.getConnection(url, username, password);
		
		statement = connection.createStatement();
		String x = "SELECT * FROM user WHERE location LIKE '%الرياض%' ";
		
			PreparedStatement statement1 = connection.prepareStatement(x);
		
			result = statement1.executeQuery();
		      while(result.next()){
			System.out.println(result.getString("location"));
		      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

}
