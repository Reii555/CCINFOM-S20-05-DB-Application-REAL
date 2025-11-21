import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PickupPrepPanel extends JFrame {
    private JTable pickupTable;
    private DefaultTableModel tableModel;
    private JLabel customerInfoLabel;

    public PickupPrepPanel() {
        setTitle("Pickup Preparation & Customer Verification");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.lightGray);

        // Header
        JLabel header = new JLabel("Pickup Preparation & Customer Verification", SwingConstants.CENTER);
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

        String[] statuses = {"All", "Pending", "Ready", "Completed"};
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
        JLabel tableLabel = new JLabel("Pickup Orders", SwingConstants.CENTER);
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

        JLabel infoTitle = new JLabel("Customer Information", SwingConstants.CENTER);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoTitle.setOpaque(true);
        infoTitle.setBackground(Color.lightGray);
        infoTitle.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        infoPanel.add(infoTitle, BorderLayout.NORTH);

        customerInfoLabel = new JLabel("<html><br>Select a pickup order to view customer details<br><br></html>");
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

        JButton prepareButton = createButton("Prepare Order for Pickup", Color.white, buttonSize);
        prepareButton.addActionListener(e -> prepareOrder());
        gbc.gridy = 0;
        buttonPanel.add(prepareButton, gbc);

        JButton verifyButton = createButton("Verify Customer", Color.white, buttonSize);
        verifyButton.addActionListener(e -> verifyCustomer());
        gbc.gridy = 1;
        buttonPanel.add(verifyButton, gbc);

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

        // Sample data matching your database
        Object[][] data = {
                {1000000001, "Meal A", "Pending", "Sta. Ana branch", "2024-01-20", "Express", "Cash", 1000000001},
                {1000000002, "Combo meal C", "Ready", "Malate branch", "2024-01-21", "Standard", "Credit", 1000000002},
                {1000000003, "Combo meal D", "Completed", "Pasay branch", "2024-01-19", "Express", "Cash", 1000000003},
                {1000000004, "Meal B", "Pending", "Antipolo branch", "2024-01-22", "Standard", "Debit", 1000000004},
                {1000000005, "Meal A", "Ready", "Gen. Trias branch", "2024-01-21", "Express", "Credit", 1000000005}
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

            // TODO: Fetch customer details from database using customerId
            // For now, using sample data
            String customerName = getCustomerName(customerId);
            String customerAddress = getCustomerAddress(customerId);

            customerInfoLabel.setText(
                    "<html><b>Customer ID:</b> " + customerId + "<br>" +
                            "<b>Customer Name:</b> " + customerName + "<br>" +
                            "<b>Address:</b> " + customerAddress + "<br>" +
                            "<b>Order ID:</b> " + orderId + "<br>" +
                            "<b>Pickup Location:</b> " + location + "</html>"
            );
        }
    }

    private void prepareOrder() {
        int selectedRow = pickupTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a pickup order first", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = tableModel.getValueAt(selectedRow, 2).toString();
        if (!status.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "Only 'Pending' orders can be prepared", "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String orderId = tableModel.getValueAt(selectedRow, 0).toString();
        String location = tableModel.getValueAt(selectedRow, 3).toString();
        long customerId = Long.parseLong(tableModel.getValueAt(selectedRow, 7).toString());
        String customerName = getCustomerName(customerId);

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Prepare Order for Pickup</b><br><br>" +
                        "Order ID: " + orderId + "<br>" +
                        "Pickup Location: " + location + "<br>" +
                        "Customer: " + customerName + "<br><br>" +
                        "Update status to 'Ready'?</html>",
                "Confirm Preparation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Update database - UPDATE Pickups SET STATUS = 'Ready' WHERE ORDER_ID = orderId
            tableModel.setValueAt("Ready", selectedRow, 2);
            JOptionPane.showMessageDialog(this, "Order prepared successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateCustomerInfo();
        }
    }

    private void verifyCustomer() {
        int selectedRow = pickupTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a pickup order first", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = tableModel.getValueAt(selectedRow, 2).toString();
        if (!status.equals("Ready")) {
            JOptionPane.showMessageDialog(this, "Only 'Ready' orders can be verified", "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String orderId = tableModel.getValueAt(selectedRow, 0).toString();
        long customerId = Long.parseLong(tableModel.getValueAt(selectedRow, 7).toString());
        String customerName = getCustomerName(customerId);

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Verify Customer on Pickup</b><br><br>" +
                        "Order ID: " + orderId + "<br>" +
                        "Customer ID: " + customerId + "<br>" +
                        "Customer Name: " + customerName + "<br><br>" +
                        "Confirm pickup completion?</html>",
                "Verify Customer",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Update database - UPDATE Pickups SET STATUS = 'Completed' WHERE ORDER_ID = orderId
            tableModel.setValueAt("Completed", selectedRow, 2);
            JOptionPane.showMessageDialog(this, "Pickup verified and completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateCustomerInfo();
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