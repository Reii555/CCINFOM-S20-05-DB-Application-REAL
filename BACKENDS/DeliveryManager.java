import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryManager {
    public static Delivery getDeliveryById(int deliveryId){
        String sql = "SELECT * FROM Deliveries WHERE DELIVERY_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setInt(1, deliveryId);
            ResultSet resultSet = pStatement.executeQuery();
            if(resultSet.next()){
                return new Delivery(
                    resultSet.getInt("DELIVERY_ID"),
                    resultSet.getInt("ORDER_ID"),
                    resultSet.getString("DELIVERY_TYPE"),
                    resultSet.getString("DELIVERY_STATUS"),
                    resultSet.getInt("DRIVER_ID"),
                    resultSet.getString("PAYMENT"),
                    resultSet.getTime("EST_DELIVERY_TIME"),
                    resultSet.getTime("ACT_DELIVERY_TIME"),
                    resultSet.getDouble("DELIVERY_FEE"),
                    resultSet.getDate("DELIVERY_DATE")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();    
        }
            return null;
    }

public static List<Delivery> getAllDeliveriesByStatus(String status){
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM Deliveries WHERE DELIVERY STATUS = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setString(1, status);
            ResultSet resultSet = pStatement.executeQuery();

            while(resultSet.next()){
                deliveries.add(new Delivery(
                    resultSet.getInt("DELIVERY_ID"),
                    resultSet.getInt("ORDER_ID"),
                    resultSet.getString("DELIVERY_TYPE"),
                    resultSet.getString("DELIVERY_STATUS"),
                    resultSet.getInt("DRIVER_ID"),
                    resultSet.getString("PAYMENT"),
                    resultSet.getTime("EST_DELIVERY_TIME"),
                    resultSet.getTime("ACT_DELIVERY_TIME"),
                    resultSet.getDouble("DELIVERY_FEE"),
                    resultSet.getDate("DELIVERY_DATE")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }       return deliveries;

}

    public static boolean assignDriverToDelivery(int deliveryId, int driverId){
        String sql = "UPDATE Deliveries SET DRIVER_ID = ?, DELIVERY_STATUS = 'Assigned' WHERE DELIVERY_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setInt(1, driverId);
            pStatement.setInt(2, deliveryId);
            int rowsAffected = pStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateDeliveryStatus(int deliveryId, String newStatus){
        String sql = "UPDATE Deliveries SET DELIVERY_STATUS = ? WHERE DELIVERY_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setString(1, newStatus);
            pStatement.setInt(2, deliveryId);
            int rowsAffected = pStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean completeDelivery(int deliveryId, Time actualTime){
        String sql = "UPDATE Deliveries SET DELIVERY_STATUS = 'Delivered', ACT_DELIVERY_TIME = ? WHERE DELIVERY_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setTime(1, actualTime);
            pStatement.setInt(2, deliveryId);
            int rowsAffected = pStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Delivery> getDeliveriesByDriver(int driverId){
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM Deliveries WHERE DRIVER_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setInt(1, driverId);
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                deliveries.add(new Delivery(
                    resultSet.getInt("DELIVERY_ID"),
                    resultSet.getInt("ORDER_ID"),
                    resultSet.getString("DELIVERY_TYPE"),
                    resultSet.getString("DELIVERY_STATUS"),
                    resultSet.getInt("DRIVER_ID"),
                    resultSet.getString("PAYMENT"),
                    resultSet.getTime("EST_DELIVERY_TIME"),
                    resultSet.getTime("ACT_DELIVERY_TIME"),
                    resultSet.getDouble("DELIVERY_FEE"),
                    resultSet.getDate("DELIVERY_DATE")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }      return deliveries;
    }

    public static List<DeliveryLocationDensityReportModel> getDeliveryLocationDensityReportModel() {
    List<DeliveryLocationDensityReportModel> reportList = new ArrayList<>();

    String sql = "SELECT p.PICKUP_LOCATION AS Delivery_Location, " +
                 "COUNT(d.DELIVERY_ID) AS Total_Completed_Deliveries, " +
                 "MONTHNAME(d.DELIVERY_DATE) AS Month, " +
                 "YEAR(d.DELIVERY_DATE) AS Year, " +
                 "RANK() OVER (ORDER BY COUNT(d.DELIVERY_ID) DESC) AS Location_Rank " +
                 "FROM Deliveries d " +
                 "JOIN Pickups p ON d.ORDER_ID = p.ORDER_ID " +
                 "WHERE d.DELIVERY_STATUS = 'Delivered' " +
                 "AND YEAR(d.DELIVERY_DATE) = YEAR(CURDATE()) " +
                 "AND MONTH(d.DELIVERY_DATE) = MONTH(CURDATE()) " +
                 "GROUP BY p.PICKUP_LOCATION " +
                 "ORDER BY COUNT(d.DELIVERY_ID) DESC";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pStatement = conn.prepareStatement(sql);
         ResultSet resultSet = pStatement.executeQuery()) {

        while (resultSet.next()) {
            DeliveryLocationDensityReportModel report = new DeliveryLocationDensityReportModel(
                resultSet.getString("Delivery_Location"),
                resultSet.getInt("Total_Completed_Deliveries"),
                resultSet.getString("Month"),
                resultSet.getInt("Year"),
                resultSet.getInt("Location_Rank")
            );
            reportList.add(report);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reportList;
}


}
