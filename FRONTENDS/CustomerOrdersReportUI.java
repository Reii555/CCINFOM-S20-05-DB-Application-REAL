import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;

public class CustomerOrdersReportUI extends JFrame {
    private JComboBox<Integer> monthCombo, yearCombo;
    private JTable reportTable;
    
    public CustomerOrdersReportUI() {
        setTitle("Customer Orders Report");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.GRAY);
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Customer Orders Report", SwingConstants.CENTER);
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
        
        String[] columns = {"Customer ID", "Customer Name", "Deliveries", "Pickups", "Total Transaction Amount"};
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
        int month = (int) monthCombo.getSelectedItem();
        int year = (int) yearCombo.getSelectedItem();
        
        try {
            // Note: CustomerOrdersReport here refers to the backend model class
            List<CustomerOrdersReport> reports = ReportManager.getCustomerOrdersReport(year, month);
            
            if (reports == null || reports.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No data found for the selected month and year.", 
                    "No Data", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            // Iterate through backend model objects
            for (CustomerOrdersReport report : reports) {
                model.addRow(new Object[]{
                    report.getCustomerId(),
                    report.getCustomerName(),
                    report.getDeliveries(),
                    report.getPickups(),
                    "$" + df.format(report.getTotalTransactionAmount())
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

