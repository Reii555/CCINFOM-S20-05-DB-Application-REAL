import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PickupManagementPanel extends JFrame {
    private JTable pickupTable;
    private DefaultTableModel tableModel;
    private JLabel customerInfoLabel;

    public PickupManagementPanel() {
        setTitle("Pickup Completion & Payment Processing");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.lightGray);

        // Header
        JLabel header = new JLabel("Pickup Completion & Payment Processing", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(Color.white);
        header.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        main.add(header, BorderLayout.NORTH);

        // Body Panel
        JPanel body = new JPanel(new BorderLayout(10, 10));
        body.setBackground(Color.gray);
        body.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Top Panel - Search and Filters
        JPanel topPanel = createTopPanel();
        body.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        JPanel centerPanel = createTablePanel();
        body.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Customer Info and Actions
        JPanel bottomPanel = createBottomPanel();
        body.add(bottomPanel, BorderLayout.SOUTH);

        main.add(body, BorderLayout.CENTER);
        add(main);
        setVisible(true);

        loadSampleData();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Search Label and Field
        JLabel searchLabel = new JLabel("Search Order ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 30));
        searchField.setBackground(Color.white);
        searchField.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(searchField);

        // Status Filter
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLabel);

        String[] statuses = {"All", "Completed", "Picked Up", "Failed"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        statusCombo.setPreferredSize(new Dimension(120, 30));
        statusCombo.setBackground(Color.white);
        statusCombo.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(statusCombo);

        // Location Filter
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(locationLabel);

        String[] locations = {"All", "Sta. Ana branch", "Malate branch", "Pasay branch", "Antipolo branch", "Gen. Trias branch"};
        JComboBox<String> locationCombo = new JComboBox<>(locations);
        locationCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        locationCombo.setPreferredSize(new Dimension(150, 30));
        locationCombo.setBackground(Color.white);
        locationCombo.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(locationCombo);

        // Search Button
        JButton searchButton = createButton("Search", Color.white, new Dimension(100, 30));
        panel.add(searchButton);

        // Refresh Button
        JButton refreshButton = createButton("Refresh", Color.white, new Dimension(100, 30));
        refreshButton.addActionListener(e -> loadSampleData());
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Table Header Label
        JLabel tableLabel = new JLabel("Completed Pickup Orders", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tableLabel.setOpaque(true);
        tableLabel.setBackground(Color.lightGray);
        tableLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(tableLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Order ID", "Order Type", "Status", "Location", "Date", "Service", "Payment", "Customer ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        pickupTable = new JTable(tableModel);
        pickupTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pickupTable.setRowHeight(25);
        pickupTable.setBackground(Color.white);
        pickupTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        pickupTable.getTableHeader().setBackground(Color.lightGray);
        pickupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pickupTable.setGridColor(Color.black);

        // Add selection listener
        pickupTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCustomerInfo();
            }
        });

        JScrollPane scrollPane = new JScrollPane(pickupTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        scrollPane.getViewport().setBackground(Color.white);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.gray);

        // Customer Info Panel (Left)
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JLabel infoTitle = new JLabel("Order & Payment Information", SwingConstants.CENTER);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoTitle.setOpaque(true);
        infoTitle.setBackground(Color.lightGray);
        infoTitle.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        infoPanel.add(infoTitle, BorderLayout.NORTH);

        customerInfoLabel = new JLabel("<html><br>Select a completed order to process pickup<br><br></html>");
        customerInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        customerInfoLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.add(customerInfoLabel, BorderLayout.CENTER);

        panel.add(infoPanel, BorderLayout.CENTER);

        // Action Buttons Panel (Right)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.gray);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Dimension buttonSize = new Dimension(220, 40);

        JButton successButton = createButton("Process Successful Pickup", new Color(144, 238, 144), buttonSize);
        successButton.addActionListener(e -> processSuccessfulPickup());
        gbc.gridy = 0;
        buttonPanel.add(successButton, gbc);

        JButton failedButton = createButton("Process Failed Pickup", new Color(255, 182, 193), buttonSize);
        failedButton.addActionListener(e -> processFailedPickup());
        gbc.gridy = 1;
        buttonPanel.add(failedButton, gbc);

        JButton backButton = createButton("Back to Main Menu", Color.white, buttonSize);
        backButton.addActionListener(e -> dispose());
        gbc.gridy = 2;
        buttonPanel.add(backButton, gbc);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JButton createButton(String text, Color color, Dimension size) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        return button;
    }

    private void loadSampleData() {
        tableModel.setRowCount(0);

        // Sample data - Only Completed, Picked Up, and Failed orders
        Object[][] data = {
                {1000000003, "Combo meal D", "Completed", "Pasay branch", "2024-01-19", "Express", "Cash", 1000000003},
                {1000000006, "Meal C", "Completed", "Malate branch", "2024-01-22", "Standard", "Credit", 1000000002},
                {1000000007, "Combo meal A", "Picked Up", "Sta. Ana branch", "2024-01-21", "Express", "Debit", 1000000001},
                {1000000008, "Meal B", "Failed", "Antipolo branch", "2024-01-20", "Standard", "Cash", 1000000004}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void updateCustomerInfo() {
        int selectedRow = pickupTable.getSelectedRow();
        if (selectedRow >= 0) {
            long customerId = Long.parseLong(tableModel.getValueAt(selectedRow, 7).toString());
            String orderId = tableModel.getValueAt(selectedRow, 0).toString();
            String location = tableModel.getValueAt(selectedRow, 3).toString();
            String status = tableModel.getValueAt(selectedRow, 2).toString();
            String paymentMethod = tableModel.getValueAt(selectedRow, 6).toString();

            String customerName = getCustomerName(customerId);
            String customerAddress = getCustomerAddress(customerId);

            customerInfoLabel.setText(
                    "<html><b>Customer ID:</b> " + customerId + "<br>" +
                            "<b>Customer Name:</b> " + customerName + "<br>" +
                            "<b>Address:</b> " + customerAddress + "<br>" +
                            "<b>Order ID:</b> " + orderId + "<br>" +
                            "<b>Pickup Location:</b> " + location + "<br>" +
                            "<b>Current Status:</b> " + status + "<br>" +
                            "<b>Payment Method:</b> " + paymentMethod + "</html>"
            );
        }
    }

    private void processSuccessfulPickup() {
        int selectedRow = pickupTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order first", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = tableModel.getValueAt(selectedRow, 2).toString();
        if (!status.equals("Completed")) {
            JOptionPane.showMessageDialog(this,
                    "Only 'Completed' orders can be processed for pickup\n" +
                            "Current status: " + status,
                    "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ACCESS: PRM(Order ID, Pickup Status, Payment Method)
        String orderId = tableModel.getValueAt(selectedRow, 0).toString();
        String paymentMethod = tableModel.getValueAt(selectedRow, 6).toString();
        long customerId = Long.parseLong(tableModel.getValueAt(selectedRow, 7).toString());
        String customerName = getCustomerName(customerId);

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Process Successful Pickup</b><br><br>" +
                        "Order ID: " + orderId + "<br>" +
                        "Customer: " + customerName + " (ID: " + customerId + ")<br>" +
                        "Payment Method: " + paymentMethod + "<br><br>" +
                        "Confirm that the customer has successfully picked up their order?<br>" +
                        "Payment will be processed via " + paymentMethod + ".</html>",
                "Confirm Successful Pickup",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: UPDATE: PRM(Pickup Status, Payment Method)
            // UPDATE Pickups SET STATUS = 'Picked Up', PAYMENT_METHOD = ? WHERE ORDER_ID = ?
            tableModel.setValueAt("Picked Up", selectedRow, 2);
            JOptionPane.showMessageDialog(this,
                    "Pickup processed successfully!\n" +
                            "Status updated to 'Picked Up'\n" +
                            "Payment processed via " + paymentMethod,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            updateCustomerInfo();
        }
    }

    private void processFailedPickup() {
        int selectedRow = pickupTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order first", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = tableModel.getValueAt(selectedRow, 2).toString();
        if (!status.equals("Completed")) {
            JOptionPane.showMessageDialog(this,
                    "Only 'Completed' orders can be marked as failed\n" +
                            "Current status: " + status,
                    "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ACCESS: PRM(Order ID, Pickup Status, Payment Method)
        String orderId = tableModel.getValueAt(selectedRow, 0).toString();
        long customerId = Long.parseLong(tableModel.getValueAt(selectedRow, 7).toString());
        String customerName = getCustomerName(customerId);

        // Ask for reason
        String[] reasons = {
                "Customer did not show up",
                "Customer could not be verified",
                "Order was incorrect",
                "Payment issue",
                "Other"
        };

        String reason = (String) JOptionPane.showInputDialog(this,
                "Select reason for failed pickup:",
                "Failed Pickup Reason",
                JOptionPane.QUESTION_MESSAGE,
                null,
                reasons,
                reasons[0]);

        if (reason != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html><b>Process Failed Pickup</b><br><br>" +
                            "Order ID: " + orderId + "<br>" +
                            "Customer: " + customerName + " (ID: " + customerId + ")<br>" +
                            "Reason: " + reason + "<br><br>" +
                            "Mark this pickup as 'Failed'?<br>" +
                            "This will require follow-up action.</html>",
                    "Confirm Failed Pickup",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // TODO: UPDATE: PRM(Pickup Status, Payment Method)
                // UPDATE Pickups SET STATUS = 'Failed', NOTES = ? WHERE ORDER_ID = ?
                tableModel.setValueAt("Failed", selectedRow, 2);
                JOptionPane.showMessageDialog(this,
                        "Pickup marked as 'Failed'\n" +
                                "Reason: " + reason + "\n" +
                                "Follow-up action required",
                        "Failed Pickup Processed", JOptionPane.WARNING_MESSAGE);
                updateCustomerInfo();
            }
        }
    }

    // Sample customer data - TODO: Replace with database queries
    private String getCustomerName(long customerId) {
        switch ((int)customerId) {
            case 1000000001: return "Juan Dela Cruz";
            case 1000000002: return "Maria Santos";
            case 1000000003: return "Pedro Reyes";
            case 1000000004: return "Ana Garcia";
            case 1000000005: return "Jose Bautista";
            default: return "Unknown Customer";
        }
    }

    private String getCustomerAddress(long customerId) {
        switch ((int)customerId) {
            case 1000000001: return "123 Rizal Street, Sta. Ana, Manila";
            case 1000000002: return "456 Mabini Avenue, Malate, Manila";
            case 1000000003: return "789 Roxas Boulevard, Pasay City";
            case 1000000004: return "321 Sumulong Highway, Antipolo, Rizal";
            case 1000000005: return "654 Governor's Drive, Gen. Trias, Cavite";
            default: return "Address not found";
        }
    }
}