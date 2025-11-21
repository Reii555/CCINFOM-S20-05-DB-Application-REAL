import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DeliveryDriverFrequencyReport extends JFrame {
    private JComboBox<Integer> monthCombo, yearCombo;
    private JTable reportTable;
    
    public DeliveryDriverFrequencyReport() {
        setTitle("Delivery Driver Frequency Report");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.GRAY);
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Delivery Driver Frequency Report", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        main.add(header, BorderLayout.NORTH);
        
        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.LIGHT_GRAY);
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
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
        generateBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        generateBtn.addActionListener(e -> generateReport());
        controls.add(generateBtn);
        
        main.add(controls, BorderLayout.NORTH);
        
        String[] columns = {"Driver ID", "First Name", "Last Name", "Shift", "Total Deliveries", "Rank"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        reportTable = new JTable(model);
        reportTable.setBackground(Color.WHITE);
        reportTable.setGridColor(Color.BLACK);
        main.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        add(main);
        setVisible(true);
    }
    
    private void generateReport() {
        int month = (int) monthCombo.getSelectedItem();
        int year = (int) yearCombo.getSelectedItem();
        
        try {
            ResultSet rs = DriverManager.generateDriverFrequencyReport(year, month);
            if (rs == null) {
                JOptionPane.showMessageDialog(this, "Error: Could not generate report. Check database connection.");
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("DRIVER_ID"),
                    rs.getString("DRIVER_FIRSTNAME"),
                    rs.getString("DRIVER_LASTNAME"),
                    rs.getTime("SHIFT") != null ? rs.getTime("SHIFT").toString() : "",
                    rs.getInt("Total_Deliveries"),
                    rs.getInt("Driver_Rank")
                });
            }
            
            rs.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

