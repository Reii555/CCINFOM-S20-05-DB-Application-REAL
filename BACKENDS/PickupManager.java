import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PickupManager {

    public static Pickup getPickupById(int orderId){
        String sql = "SELECT * FROM Pickups WHERE ORDER_ID = ?";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pStatement = conn.prepareStatement(sql)){
            pStatement.setInt(1, orderId);
            ResultSet resultSet = pStatement.executeQuery();

            if(resultSet.next()){
                Pickup pickup = new Pickup(
                resultSet.getInt("ORDER_ID"),
                resultSet.getString("ORDER_TYPE"),
                resultSet.getString("STATUS"),
                resultSet.getString("PICKUP_LOCATION"),
                resultSet.getDate("PICKUP_DATE"),
                resultSet.getString("PICKUP_SERVICE"),
                resultSet.getString("PAYMENT_METHOD"),
                resultSet.getInt("CUSTOMER_ID")
                );
                return pickup;
            } else {
                return null; // No pickup found with the given orderId
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return null;
        }    
    }

    public static boolean updatePickupStatus(int orderId, String newStatus){
        
        /*// test print for noooww...
        System.out.println("Updating order " + orderId + " to status: " + newStatus);
        return true;*/

        String sql = "UPDATE Pickups SET STATUS = ? WHERE ORDER_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setString(1, newStatus);  //puts "status" into the first "?" in the sql string
            pStatement.setInt(2, orderId);       //puts orderId into the second "?" in the sql string

            int affectedRows = pStatement.executeUpdate();

            return affectedRows > 0;        //if one or more rows were affected, return true

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean completePickupWPayment(int orderId, String paymentMethod){
        String sql = "UPDATE Pickups SET STATUS = 'Completed', PAYMENT_METHOD = ? WHERE ORDER_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setString(1, paymentMethod);
            pStatement.setInt(2, orderId);

            int affectedRows = pStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return false;
        }
    }    

    // Get available drivers for pickup assignment
    public static List<Driver> getAvailableDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT DRIVER_ID, DRIVER_LASTNAME, DRIVER_FIRSTNAME, DRIVER_CONTACT, DRIVER_LICENCE, STATUS, SHIFT FROM Drivers WHERE STATUS = 'Available'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                drivers.add(new Driver(
                    rs.getInt("DRIVER_ID"),
                    rs.getString("DRIVER_LASTNAME"),
                    rs.getString("DRIVER_FIRSTNAME"),
                    rs.getLong("DRIVER_CONTACT"),
                    rs.getString("DRIVER_LICENCE"),
                    rs.getString("STATUS"),
                    rs.getTime("SHIFT") != null ? rs.getTime("SHIFT").toString() : ""
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

}
