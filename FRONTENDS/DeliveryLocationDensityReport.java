import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DeliveryLocationDensityReport extends JFrame {
    private JComboBox<Integer> monthCombo, yearCombo;
    private JTable reportTable;
    
    public DeliveryLocationDensityReport() {
        setTitle("Delivery Location Density Report");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.GRAY);
        main.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Delivery Location Density Report", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(Color.WHITE);
        header.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 2));
        main.add(header, BorderLayout.NORTH);
        
        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.LIGHT_GRAY);
        controls.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        controls.add(new JLabel("Month:"));
        monthCombo = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12});
        monthCombo.setBackground(Color.WHITE);
        controls.add(monthCombo);
        
        controls.add(new JLabel("Year:"));
        yearCombo = new JComboBox<>(new Integer[]{2023, 2024, 2025});
        yearCombo.setSelectedItem(2024);
        yearCombo.setBackground(Color.WHITE);
        controls.add(yearCombo);
        
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        generateBtn.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 2));
        generateBtn.addActionListener(e -> generateReport());
        controls.add(generateBtn);
        
        main.add(controls, BorderLayout.NORTH);
        
        String[] columns = {"Location", "Completed Deliveries", "Month", "Year", "Rank"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        reportTable = new JTable(model);
        reportTable.setBackground(Color.WHITE);
        reportTable.setGridColor(Color.BLACK);
        main.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        add(main);
        setVisible(true);
    }
    
    /**
     * Placeholder implementation.
     *
     * This method currently just informs the user that the backend logic
     * is not implemented yet so the project still compiles and the button
     * is clickable.
     *
     * TODO for backend team:
     *  1. Implement DeliveryManager.generateLocationDensityReport(int year, int month)
     *     using the SQL in sql/Delivery_Location_Density_Report.sql.
     *  2. Replace this placeholder body with code that:
     *       - calls that DeliveryManager method to get a ResultSet,
     *       - clears the table model,
     *       - iterates the ResultSet and adds rows matching the columns:
     *           "Delivery Location", "Total Completed Deliveries", "Month", "Year", "Location_Rank".
     */
    private void generateReport() {
        int month = (int) monthCombo.getSelectedItem();
        int year = (int) yearCombo.getSelectedItem();

        JOptionPane.showMessageDialog(
            this,
            "Delivery Location Density Report UI is wired, but the backend query\n" +
            "(DeliveryManager.generateLocationDensityReport(year, month)) is not yet implemented.\n\n" +
            "TODO for backend team:\n" +
            " 1) Implement the method in DeliveryManager using sql/Delivery_Location_Density_Report.sql.\n" +
            " 2) Update DeliveryLocationDensityReport.generateReport() to call that method\n" +
            "    and populate the JTable with the returned data.",
            "Not Implemented Yet",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

