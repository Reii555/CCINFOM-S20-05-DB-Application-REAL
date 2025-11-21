import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerPreferencesReportUI extends JFrame {
    private JTable reportTable;
    
    public CustomerPreferencesReportUI() {
        setTitle("Customer Preferences Report");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.GRAY);
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Customer Preferences Report", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        main.add(header, BorderLayout.NORTH);
        
        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.LIGHT_GRAY);
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        generateBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        generateBtn.addActionListener(e -> generateReport());
        controls.add(generateBtn);
        
        main.add(controls, BorderLayout.NORTH);
        
        String[] columns = {"Customer ID", "First Name", "Last Name", "Delivery Count", "Pickup Count", "Preferred Method"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(model);
        reportTable.setBackground(Color.WHITE);
        reportTable.setGridColor(Color.BLACK);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 13));
        reportTable.setRowHeight(25);
        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        reportTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        
        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        main.add(scrollPane, BorderLayout.CENTER);
        
        add(main);
        setVisible(true);
    }
    
    private void generateReport() {
        try {
            List<CustomerPreferenceReport> reports = ReportManager.getCustomerPreferences();
            
            if (reports == null || reports.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No data found.", 
                    "No Data", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);
            
            for (CustomerPreferenceReport report : reports) {
                model.addRow(new Object[]{
                    report.getCustomerId(),
                    report.getFirstName(),
                    report.getLastName(),
                    report.getDeliveryCount(),
                    report.getPickupCount(),
                    report.getPreferredMethod()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error generating report: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

