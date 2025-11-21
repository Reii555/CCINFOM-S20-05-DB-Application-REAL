import java.sql.*;

public class TestSQL {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("=== CHECKING DATABASE DATA ===");
            
            // Check customers table
            checkTable(conn, "customers", "SELECT COUNT(*) FROM customers");
            
            // Check pickups table  
            checkTable(conn, "pickups", "SELECT COUNT(*) FROM pickups");
            
            // Check deliveries table
            checkTable(conn, "deliveries", "SELECT COUNT(*) FROM deliveries");
            
            // Check if we have any pickup-delivery relationships
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) FROM pickups p INNER JOIN deliveries d ON p.order_id = d.order_id"
            );
            if (rs.next()) {
                System.out.println("✅ Pickup-Delivery relationships: " + rs.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void checkTable(Connection conn, String tableName, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int count = rs.getInt(1);
            System.out.println("✅ " + tableName + " count: " + count);
        }
    }
}