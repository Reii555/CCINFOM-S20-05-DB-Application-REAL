import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.BorderFactory;
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
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(header, BorderLayout.NORTH);
        
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
        
        topPanel.add(controls, BorderLayout.CENTER);
        main.add(topPanel, BorderLayout.NORTH);
        
        String[] columns = {"Location", "Completed Deliveries", "Month", "Year", "Rank"};
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
        reportTable.setRowHeight(24);
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
            List<DeliveryLocationDensityReportModel> reports = DeliveryManager.getDeliveryLocationDensityReportModel(year, month);

            DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
            model.setRowCount(0);

            if (reports == null || reports.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
                    "No delivery location data found for the selected month and year.",
                    "No Data",
            JOptionPane.INFORMATION_MESSAGE
        );
                return;
            }

            for (DeliveryLocationDensityReportModel report : reports) {
                model.addRow(new Object[]{
                    report.getLocation(),
                    report.getTotalCompleteDeliveries(),
                    report.getMonth(),
                    report.getYear(),
                    report.getRank()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error generating report: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}

