package utility;

import java.sql.*;

public class DataBaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/daoTest";
    private static String user = "root";
    private static String password = "admim";

    private static Connection connection;

    public static Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeResultSet(ResultSet result) {
        if(result != null) {
            try {
                result.close();
            }
            catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void closeStatement(PreparedStatement pstmt) {
        if(pstmt != null) {
            try {
                pstmt.close();
            }
            catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    public static void disconnectDataBase() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }
}
