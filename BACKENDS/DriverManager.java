import java.sql.*;

public class DriverManager {
    
    public static boolean updateDriverStatus(int driverId, String newStatus) {
        String sql = "UPDATE Drivers SET STATUS = ? WHERE DRIVER_ID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            
            pStatement.setString(1, newStatus);
            pStatement.setInt(2, driverId);
            
            int affectedRows = pStatement.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet getAvailableDrivers() {
        String sql = "SELECT DRIVER_ID, DRIVER_FIRSTNAME, DRIVER_LASTNAME, SHIFT FROM Drivers WHERE STATUS = 'Available'";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            return pStatement.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ResultSet getAvailableDriversByShift(String shiftTime) {
        String sql = "SELECT DRIVER_ID, DRIVER_FIRSTNAME, DRIVER_LASTNAME, SHIFT FROM Drivers WHERE STATUS = 'Available' AND SHIFT = ?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, shiftTime);
            return pStatement.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ResultSet generateDriverFrequencyReport(int year, int month) {
        String sql = "SELECT d.DRIVER_ID, d.DRIVER_FIRSTNAME, d.DRIVER_LASTNAME, d.SHIFT, " +
                    "COUNT(del.DELIVERY_ID) AS Total_Deliveries, " +
                    "RANK() OVER (PARTITION BY d.SHIFT ORDER BY COUNT(del.DELIVERY_ID) DESC) AS Driver_Rank " +
                    "FROM Drivers d " +
                    "LEFT JOIN Deliveries del ON d.DRIVER_ID = del.DRIVER_ID " +
                    "AND YEAR(del.DELIVERY_DATE) = ? " +
                    "AND MONTH(del.DELIVERY_DATE) = ? " +
                    "AND del.DELIVERY_STATUS = 'Delivered' " +
                    "GROUP BY d.DRIVER_ID, d.DRIVER_FIRSTNAME, d.DRIVER_LASTNAME, d.SHIFT " +
                    "ORDER BY d.SHIFT, Total_Deliveries DESC";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, year);
            pStatement.setInt(2, month);
            return pStatement.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ResultSet getDriverById(int driverId) {
        String sql = "SELECT * FROM Drivers WHERE DRIVER_ID = ?";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, driverId);
            return pStatement.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}