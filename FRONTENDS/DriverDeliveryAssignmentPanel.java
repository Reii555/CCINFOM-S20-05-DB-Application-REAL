import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.sql.Time;

public class DriverDeliveryAssignmentPanel extends JFrame {
    private JTable deliveryTable;
    private JTable driverTable;
    private DefaultTableModel deliveryTableModel;
    private DefaultTableModel driverTableModel;
    private JLabel deliveryInfoLabel;

    public DriverDeliveryAssignmentPanel() {
        setTitle("Driver and Delivery Assignment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.lightGray);

        // Header
        JLabel header = new JLabel("Driver and Delivery Assignment", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(Color.white);
        header.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        main.add(header, BorderLayout.NORTH);

        // Body Panel
        JPanel body = new JPanel(new BorderLayout(10, 10));
        body.setBackground(Color.gray);
        body.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Top Panel - Filters
        JPanel topPanel = createTopPanel();
        body.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Split into two tables
        JPanel centerPanel = createCenterPanel();
        body.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Delivery Info and Actions
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

        // Delivery Status Filter
        JLabel statusLabel = new JLabel("Delivery Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLabel);

        String[] statuses = {"All", "Pending", "Assigned", "In Transit", "Delivered"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        statusCombo.setPreferredSize(new Dimension(120, 30));
        statusCombo.setBackground(Color.white);
        statusCombo.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(statusCombo);

        // Delivery Type Filter
        JLabel typeLabel = new JLabel("Delivery Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(typeLabel);

        String[] types = {"All", "Standard", "Express"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        typeCombo.setPreferredSize(new Dimension(120, 30));
        typeCombo.setBackground(Color.white);
        typeCombo.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(typeCombo);

        // Driver Status Filter
        JLabel driverStatusLabel = new JLabel("Driver Status:");
        driverStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(driverStatusLabel);

        String[] driverStatuses = {"All", "Available", "On Delivery", "Off Duty"};
        JComboBox<String> driverStatusCombo = new JComboBox<>(driverStatuses);
        driverStatusCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        driverStatusCombo.setPreferredSize(new Dimension(120, 30));
        driverStatusCombo.setBackground(Color.white);
        driverStatusCombo.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(driverStatusCombo);

        // Refresh Button
        JButton refreshButton = createButton("Refresh", Color.white, new Dimension(100, 30));
        refreshButton.addActionListener(e -> loadSampleData());
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBackground(Color.gray);

        // Left Side - Delivery Orders Table
        JPanel deliveryPanel = createDeliveryTablePanel();
        panel.add(deliveryPanel);

        // Right Side - Available Drivers Table
        JPanel driverPanel = createDriverTablePanel();
        panel.add(driverPanel);

        return panel;
    }

    private JPanel createDeliveryTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Table Header Label
        JLabel tableLabel = new JLabel("Delivery Orders", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tableLabel.setOpaque(true);
        tableLabel.setBackground(Color.lightGray);
        tableLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(tableLabel, BorderLayout.NORTH);

        // Table - ACCESS: DRM
        String[] columns = {"Delivery ID", "Order ID", "Delivery Type", "Status", "Driver ID", "Est. Time"};
        deliveryTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        deliveryTable = new JTable(deliveryTableModel);
        deliveryTable.setFont(new Font("Arial", Font.PLAIN, 14));
        deliveryTable.setRowHeight(25);
        deliveryTable.setBackground(Color.white);
        deliveryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        deliveryTable.getTableHeader().setBackground(Color.lightGray);
        deliveryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deliveryTable.setGridColor(Color.black);

        // Add selection listener
        deliveryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDeliveryInfo();
            }
        });

        JScrollPane scrollPane = new JScrollPane(deliveryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        scrollPane.getViewport().setBackground(Color.white);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDriverTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Table Header Label
        JLabel tableLabel = new JLabel("Available Drivers", SwingConstants.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tableLabel.setOpaque(true);
        tableLabel.setBackground(Color.lightGray);
        tableLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        panel.add(tableLabel, BorderLayout.NORTH);

        // Table - ACCESS: RVM
        String[] columns = {"Driver ID", "Driver Name", "Licence", "Status", "Shift"};
        driverTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        driverTable = new JTable(driverTableModel);
        driverTable.setFont(new Font("Arial", Font.PLAIN, 14));
        driverTable.setRowHeight(25);
        driverTable.setBackground(Color.white);
        driverTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        driverTable.getTableHeader().setBackground(Color.lightGray);
        driverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        driverTable.setGridColor(Color.black);

        JScrollPane scrollPane = new JScrollPane(driverTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        scrollPane.getViewport().setBackground(Color.white);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.gray);

        // Delivery Info Panel (Left)
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.lightGray);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JLabel infoTitle = new JLabel("Delivery Information", SwingConstants.CENTER);
        infoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoTitle.setOpaque(true);
        infoTitle.setBackground(Color.lightGray);
        infoTitle.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        infoPanel.add(infoTitle, BorderLayout.NORTH);

        deliveryInfoLabel = new JLabel("<html><br>Select a delivery order to view details<br><br></html>");
        deliveryInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        deliveryInfoLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.add(deliveryInfoLabel, BorderLayout.CENTER);

        panel.add(infoPanel, BorderLayout.CENTER);

        // Action Buttons Panel (Right)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.gray);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Dimension buttonSize = new Dimension(220, 40);

        JButton assignButton = createButton("Assign Driver to Delivery", Color.white, buttonSize);
        assignButton.addActionListener(e -> assignDriverToDelivery());
        gbc.gridy = 0;
        buttonPanel.add(assignButton, gbc);

        JButton dispatchButton = createButton("Dispatch Delivery", Color.white, buttonSize);
        dispatchButton.addActionListener(e -> dispatchDeliveryToDriver());
        gbc.gridy = 1;
        buttonPanel.add(dispatchButton, gbc);

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
        // Load Delivery Orders
        deliveryTableModel.setRowCount(0);
        Object[][] deliveryData = {
                {2000000001L, 1000000001L, "Express", "Pending", null, "12:00 PM"},
                {2000000002L, 1000000002L, "Standard", "Assigned", 3000000001L, "1:30 PM"},
                {2000000003L, 1000000003L, "Express", "In Transit", 3000000002L, "11:45 AM"},
                {2000000004L, 1000000004L, "Standard", "Pending", null, "2:00 PM"},
                {2000000005L, 1000000005L, "Express", "Assigned", 3000000003L, "12:30 PM"}
        };

        for (Object[] row : deliveryData) {
            deliveryTableModel.addRow(row);
        }

        // Load Available Drivers - ACCESS: RVM
        driverTableModel.setRowCount(0);
        Object[][] driverData = {
                {3000000001L, "Ramon Cruz", "ABC-123-XYZ", "Available", "Morning"},
                {3000000002L, "Linda Santos", "DEF-456-UVW", "On Delivery", "Morning"},
                {3000000003L, "Carlos Reyes", "GHI-789-RST", "Available", "Afternoon"},
                {3000000004L, "Teresa Garcia", "JKL-012-PQR", "Available", "Afternoon"},
                {3000000005L, "Miguel Bautista", "MNO-345-LMN", "Off Duty", "Evening"}
        };

        for (Object[] row : driverData) {
            driverTableModel.addRow(row);
        }
    }

    private void updateDeliveryInfo() {
        int selectedRow = deliveryTable.getSelectedRow();
        if (selectedRow >= 0) {
            // ACCESS: DRM
            String deliveryId = deliveryTableModel.getValueAt(selectedRow, 0).toString();
            String orderId = deliveryTableModel.getValueAt(selectedRow, 1).toString();
            String deliveryType = deliveryTableModel.getValueAt(selectedRow, 2).toString();
            String status = deliveryTableModel.getValueAt(selectedRow, 3).toString();
            Object driverId = deliveryTableModel.getValueAt(selectedRow, 4);
            String estTime = deliveryTableModel.getValueAt(selectedRow, 5).toString();

            // Get customer address (simulated)
            String customerAddress = getCustomerAddress(orderId);

            deliveryInfoLabel.setText(
                    "<html><b>Delivery ID:</b> " + deliveryId + "<br>" +
                            "<b>Order ID:</b> " + orderId + "<br>" +
                            "<b>Delivery Type:</b> " + deliveryType + "<br>" +
                            "<b>Status:</b> " + status + "<br>" +
                            "<b>Assigned Driver ID:</b> " + (driverId != null ? driverId : "Not Assigned") + "<br>" +
                            "<b>Estimated Time:</b> " + estTime + "<br>" +
                            "<b>Customer Address:</b> " + customerAddress + "</html>"
            );
        }
    }

    private void assignDriverToDelivery() {
        int selectedDeliveryRow = deliveryTable.getSelectedRow();
        int selectedDriverRow = driverTable.getSelectedRow();

        if (selectedDeliveryRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a delivery order first",
                    "No Delivery Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedDriverRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a driver to assign",
                    "No Driver Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deliveryStatus = deliveryTableModel.getValueAt(selectedDeliveryRow, 3).toString();
        if (!deliveryStatus.equals("Pending")) {
            JOptionPane.showMessageDialog(this,
                    "Only 'Pending' deliveries can be assigned to drivers",
                    "Invalid Status",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String driverStatus = driverTableModel.getValueAt(selectedDriverRow, 3).toString();
        if (!driverStatus.equals("Available")) {
            JOptionPane.showMessageDialog(this,
                    "Only 'Available' drivers can be assigned",
                    "Driver Not Available",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ACCESS: DRM
        String deliveryId = deliveryTableModel.getValueAt(selectedDeliveryRow, 0).toString();
        String orderId = deliveryTableModel.getValueAt(selectedDeliveryRow, 1).toString();
        String deliveryType = deliveryTableModel.getValueAt(selectedDeliveryRow, 2).toString();

        // ACCESS: RVM
        String driverId = driverTableModel.getValueAt(selectedDriverRow, 0).toString();
        String driverName = driverTableModel.getValueAt(selectedDriverRow, 1).toString();
        String driverLicence = driverTableModel.getValueAt(selectedDriverRow, 2).toString();
        String driverShift = driverTableModel.getValueAt(selectedDriverRow, 4).toString();

        // Calculate estimated delivery time based on delivery type
        String estimatedTime = calculateEstimatedTime(deliveryType);

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Assign Driver to Delivery Order</b><br><br>" +
                        "<b>Delivery ID:</b> " + deliveryId + "<br>" +
                        "<b>Order ID:</b> " + orderId + "<br>" +
                        "<b>Delivery Type:</b> " + deliveryType + "<br><br>" +
                        "<b>Driver ID:</b> " + driverId + "<br>" +
                        "<b>Driver Name:</b> " + driverName + "<br>" +
                        "<b>Driver Licence:</b> " + driverLicence + "<br>" +
                        "<b>Shift:</b> " + driverShift + "<br><br>" +
                        "<b>New Estimated Delivery Time:</b> " + estimatedTime + "<br><br>" +
                        "Confirm driver assignment?</html>",
                "Confirm Assignment",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // UPDATE: DRM(Driver ID, Status, Estimated Delivery Time)
            // TODO: Execute SQL - UPDATE DRM SET Driver_ID = ?, Status = 'Assigned', Est_Delivery_Time = ? WHERE Delivery_ID = ?
            deliveryTableModel.setValueAt(driverId, selectedDeliveryRow, 4);
            deliveryTableModel.setValueAt("Assigned", selectedDeliveryRow, 3);
            deliveryTableModel.setValueAt(estimatedTime, selectedDeliveryRow, 5);

            // Update driver status
            driverTableModel.setValueAt("On Delivery", selectedDriverRow, 3);

            JOptionPane.showMessageDialog(this,
                    "Driver assigned successfully!\n" +
                            "Delivery Status: Assigned\n" +
                            "Estimated Delivery Time: " + estimatedTime,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            updateDeliveryInfo();
        }
    }

    private void dispatchDeliveryToDriver() {
        int selectedRow = deliveryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a delivery order first",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ACCESS: DRM(Delivery ID, Status)
        String deliveryId = deliveryTableModel.getValueAt(selectedRow, 0).toString();
        String orderId = deliveryTableModel.getValueAt(selectedRow, 1).toString();
        String deliveryStatus = deliveryTableModel.getValueAt(selectedRow, 3).toString();
        Object driverId = deliveryTableModel.getValueAt(selectedRow, 4);

        if (!deliveryStatus.equals("Assigned")) {
            JOptionPane.showMessageDialog(this,
                    "Only 'Assigned' deliveries can be dispatched",
                    "Invalid Status",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (driverId == null) {
            JOptionPane.showMessageDialog(this,
                    "No driver assigned to this delivery",
                    "No Driver",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ACCESS: CRM
        String customerAddress = getCustomerAddress(orderId);

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><b>Dispatch Delivery to Driver</b><br><br>" +
                        "<b>Delivery ID:</b> " + deliveryId + "<br>" +
                        "<b>Order ID:</b> " + orderId + "<br>" +
                        "<b>Driver ID:</b> " + driverId + "<br>" +
                        "<b>Current Status:</b> " + deliveryStatus + "<br>" +
                        "<b>Customer Address:</b> " + customerAddress + "<br><br>" +
                        "Dispatch delivery and update status to 'In Transit'?</html>",
                "Confirm Dispatch",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // UPDATE: DRM(Status) - Set to 'In Transit'
            // TODO: Execute SQL - UPDATE DRM SET Status = 'In Transit' WHERE Delivery_ID = ?
            deliveryTableModel.setValueAt("In Transit", selectedRow, 3);

            JOptionPane.showMessageDialog(this,
                    "Delivery dispatched successfully!\n" +
                            "Status updated to 'In Transit'\n" +
                            "Driver notified of customer address",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            updateDeliveryInfo();
        }
    }

    private String calculateEstimatedTime(String deliveryType) {
        // Simple calculation based on delivery type
        if (deliveryType.equals("Express")) {
            return "30 minutes";
        } else {
            return "60 minutes";
        }
    }

    // Sample customer address - ACCESS: CRM
    private String getCustomerAddress(String orderId) {
        switch (orderId) {
            case "1000000001": return "123 Rizal Street, Sta. Ana, Manila";
            case "1000000002": return "456 Mabini Avenue, Malate, Manila";
            case "1000000003": return "789 Roxas Boulevard, Pasay City";
            case "1000000004": return "321 Sumulong Highway, Antipolo, Rizal";
            case "1000000005": return "654 Governor's Drive, Gen. Trias, Cavite";
            default: return "Address not found";
        }
    }

}