import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ReportManager {

    public static List<CustomerPreferenceReport> getCustomerPreferences() {
        List<CustomerPreferenceReport> reportList = new ArrayList<>();

        // Join Deliveries through ORDER_ID to Pickups, then count by customer
        String sql = "SELECT c.customer_id, c.customer_firstname, c.customer_lastname, " +
                     "(SELECT COUNT(*) FROM deliveries d " +
                     " INNER JOIN pickups p ON d.order_id = p.order_id " +
                     " WHERE p.customer_id = c.customer_id) AS Delivery_Count, " +
                     "(SELECT COUNT(*) FROM pickups p WHERE p.customer_id = c.customer_id) AS Pickup_Count, " +
                     "CASE WHEN (SELECT COUNT(*) FROM deliveries d " +
                     "          INNER JOIN pickups p ON d.order_id = p.order_id " +
                     "          WHERE p.customer_id = c.customer_id) > " +
                     "     (SELECT COUNT(*) FROM pickups p WHERE p.customer_id = c.customer_id) " +
                     "THEN 'Delivery' ELSE 'Pickup' END AS Preferred_Method " +
                     "FROM customers c ORDER BY c.customer_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomerPreferenceReport report = new CustomerPreferenceReport(
                    rs.getInt("customer_id"),
                    rs.getString("customer_firstname"),
                    rs.getString("customer_lastname"),
                    rs.getInt("Delivery_Count"),
                    rs.getInt("Pickup_Count"),
                    rs.getString("Preferred_Method")
                );
                reportList.add(report);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportList;
    }

    // CustomerOrdersReport
    public static List<CustomerOrdersReport> getCustomerOrdersReport(int year, int month) {
        List<CustomerOrdersReport> reportList = new ArrayList<>();

        // Join Deliveries and Pickups independently with their own date filters
        String sql = "SELECT c.customer_id, " +
                     "CONCAT(c.customer_lastname, ', ', c.customer_firstname) AS CustomerName, " +
                     "COALESCE(delivery_counts.Deliveries, 0) AS Deliveries, " +
                     "COALESCE(pickup_counts.Pickups, 0) AS Pickups, " +
                     "COALESCE(delivery_amounts.TotalTransactionAmount, 0) AS TotalTransactionAmount " +
                     "FROM customers c " +
                     "LEFT JOIN (SELECT p.customer_id, COUNT(DISTINCT d.delivery_id) AS Deliveries " +
                     "          FROM deliveries d " +
                     "          INNER JOIN pickups p ON d.order_id = p.order_id " +
                     "          WHERE YEAR(d.delivery_date) = ? AND MONTH(d.delivery_date) = ? " +
                     "          GROUP BY p.customer_id) delivery_counts ON c.customer_id = delivery_counts.customer_id " +
                     "LEFT JOIN (SELECT customer_id, COUNT(DISTINCT order_id) AS Pickups " +
                     "          FROM pickups " +
                     "          WHERE YEAR(pickup_date) = ? AND MONTH(pickup_date) = ? " +
                     "          GROUP BY customer_id) pickup_counts ON c.customer_id = pickup_counts.customer_id " +
                     "LEFT JOIN (SELECT p.customer_id, " +
                     "          SUM(CASE WHEN d.payment IS NOT NULL AND d.payment != '' " +
                     "              THEN CAST(d.payment AS DECIMAL(10,2)) + d.delivery_fee ELSE 0 END) AS TotalTransactionAmount " +
                     "          FROM deliveries d " +
                     "          INNER JOIN pickups p ON d.order_id = p.order_id " +
                     "          WHERE YEAR(d.delivery_date) = ? AND MONTH(d.delivery_date) = ? " +
                     "          GROUP BY p.customer_id) delivery_amounts ON c.customer_id = delivery_amounts.customer_id " +
                     "GROUP BY c.customer_id, c.customer_lastname, c.customer_firstname, " +
                     "         delivery_counts.Deliveries, pickup_counts.Pickups, delivery_amounts.TotalTransactionAmount " +
                     "ORDER BY CustomerName ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for delivery_counts subquery (year, month)
            stmt.setInt(1, year);
            stmt.setInt(2, month);
            // Set parameters for pickup_counts subquery (year, month)
            stmt.setInt(3, year);
            stmt.setInt(4, month);
            // Set parameters for delivery_amounts subquery (year, month)
            stmt.setInt(5, year);
            stmt.setInt(6, month);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CustomerOrdersReport report = new CustomerOrdersReport(
                        rs.getInt("customer_id"),
                        rs.getString("CustomerName"),
                        rs.getInt("Deliveries"),
                        rs.getInt("Pickups"),
                        rs.getDouble("TotalTransactionAmount")
                    );
                    reportList.add(report);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportList;
    }

    }


