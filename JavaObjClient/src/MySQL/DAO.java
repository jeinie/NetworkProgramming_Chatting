package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

    static final String DB_URL = "jdbc:mysql://localhost:3306/network_db";
    static final String USER = "root";
    static final String PASSWORD = "1234";
    static final String QUERY = "SELECT * FROM users"; // 실행할 쿼리  

    public static void createRoomTable() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = con
					.prepareStatement("CREATE TABLE IF NOT EXISTS "
							+ "Room("
							+ "Room VARCHAR(255),"
							+ "PRIMARY KEY(Room))");
			statement.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Table successfully created");
		}
	}
    
    public static void addRoom(String Room) {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement insert = con.prepareStatement(""
					+ "INSERT INTO Room"
					+ "(Room) "
					+ "VALUES "
					+ "('" + Room + "')");
			insert.executeUpdate();
            System.out.println("Database 에 잘 들어감");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

    public static String[][] getRooms() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = con.prepareStatement(
					"SELECT * FROM Room");
			ResultSet results = statement.executeQuery();
			ArrayList<String[]> list = new ArrayList<String[]>();
			while(results.next()) {
				list.add(new String[] {
						results.getString("Room")
						});
			}
			System.out.println("The data has been fetched");
			String[][] arr = new String[list.size()][1];
			return list.toArray(arr);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
    
    public static String[][] getFriends(String Name) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			PreparedStatement statement = con.prepareStatement(
					"SELECT * FROM users WHERE Name NOT IN " + "('" + Name + "')");
			ResultSet results = statement.executeQuery();
			ArrayList<String[]> list = new ArrayList<String[]>();
			while(results.next()) {
				list.add(new String[] {
						//results.getString("image"),
						results.getString("Name")
						});
			}
			System.out.println("친구 목록 출력");
			String[][] arr = new String[list.size()][1];
			return list.toArray(arr);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
    }
	
}