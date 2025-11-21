import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DeliveryCompletionPanel extends JFrame {
    private JTable deliveryTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField deliveryIDField;
    private JTextField customerNameField;
    private JTextField deliveryAddressField;
    private JTextField deliveryFeeField;
    private JComboBox<String> paymentMethodCombo;
    private JComboBox<String> statusCombo;
    private JTextArea notesArea;
    private JLabel selectedDeliveryLabel;
    private String selectedDeliveryID = null;

    public DeliveryCompletionPanel() {
        setTitle("Delivery Completion & Payment Processing");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.lightGray);

        // Header - matching MainMenu style
        JLabel header = new JLabel("Delivery Completion & Payment Processing", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(Color.white);
        header.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        header.setPreferredSize(new Dimension(0, 70));
        main.add(header, BorderLayout.NORTH);

        // Main content panel - matching MainMenu gray background
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.gray);
        content.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Left panel - Pending Deliveries Table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        JPanel tablePanel = createTablePanel();
        content.add(tablePanel, gbc);

        // Right panel - Completion Form
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        JPanel formPanel = createFormPanel();
        content.add(formPanel, gbc);

        main.add(content, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(Color.gray);

        JButton closeButton = createStyledButton("Close", Color.red, new Dimension(150, 40));
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);

        main.add(bottomPanel, BorderLayout.SOUTH);

        add(main);
        setVisible(true);

        // Load sample data
        loadSampleDeliveries();
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Title and search
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.lightGray);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Pending Deliveries");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton searchButton = createStyledButton("Search", Color.white, new Dimension(80, 25));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Delivery ID", "Customer", "Address", "Driver", "Status", "Fee"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        deliveryTable = new JTable(tableModel);
        deliveryTable.setFont(new Font("Arial", Font.PLAIN, 13));
        deliveryTable.setRowHeight(25);
        deliveryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        deliveryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        deliveryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && deliveryTable.getSelectedRow() != -1) {
                loadDeliveryDetails();
            }
        });

        JScrollPane scrollPane = new JScrollPane(deliveryTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // Title
        JLabel title = new JLabel("Complete Delivery", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setOpaque(true);
        title.setBackground(Color.lightGray);
        title.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.add(title, BorderLayout.NORTH);

        // Form content
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setBackground(Color.lightGray);
        formContent.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Selected Delivery ID
        selectedDeliveryLabel = new JLabel("No delivery selected");
        selectedDeliveryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectedDeliveryLabel.setForeground(Color.blue);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        formContent.add(selectedDeliveryLabel, gbc);
        gbc.gridwidth = 1;

        // Delivery ID (Display)
        addFormLabel(formContent, "Delivery ID:", gbc, row);
        deliveryIDField = addFormField(formContent, gbc, row++, false);

        // Customer Name
        addFormLabel(formContent, "Customer Name:", gbc, row);
        customerNameField = addFormField(formContent, gbc, row++, false);

        // Delivery Address
        addFormLabel(formContent, "Delivery Address:", gbc, row);
        deliveryAddressField = addFormField(formContent, gbc, row++, false);

        // Delivery Fee
        addFormLabel(formContent, "Delivery Fee:", gbc, row);
        deliveryFeeField = addFormField(formContent, gbc, row++, false);

        // Delivery Status
        addFormLabel(formContent, "Delivery Status:", gbc, row);
        String[] statuses = {"Successful", "Failed - Convert to Pickup", "Failed - Customer Unavailable", "Failed - Address Issue"};
        statusCombo = new JComboBox<>(statuses);
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        statusCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        formContent.add(statusCombo, gbc);

        // Payment Method (only for successful)
        addFormLabel(formContent, "Payment Method:", gbc, row);
        String[] paymentMethods = {"Cash", "Credit Card", "Debit Card", "Digital Wallet", "Online Transfer"};
        paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        paymentMethodCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = row++;
        formContent.add(paymentMethodCombo, gbc);

        // Notes
        addFormLabel(formContent, "Notes:", gbc, row);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        notesArea = new JTextArea(5, 20);
        notesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JScrollPane notesScroll = new JScrollPane(notesArea);
        formContent.add(notesScroll, gbc);

        // Buttons
        gbc.gridy = row++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton processButton = createStyledButton("Process Delivery", Color.white, new Dimension(180, 35));
        processButton.addActionListener(e -> processDelivery());

        JButton clearButton = createStyledButton("Clear", Color.white, new Dimension(120, 35));
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(processButton);
        buttonPanel.add(clearButton);
        formContent.add(buttonPanel, gbc);

        panel.add(formContent, BorderLayout.CENTER);

        return panel;
    }

    private void addFormLabel(JPanel panel, String text, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);
    }

    private JTextField addFormField(JPanel panel, GridBagConstraints gbc, int row, boolean editable) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));
        field.setEditable(editable);
        field.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
        return field;
    }

    private JButton createStyledButton(String text, Color color, Dimension dim) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button.setPreferredSize(dim);
        button.setFocusPainted(false);
        return button;
    }

    private void loadSampleDeliveries() {
        tableModel.addRow(new Object[]{"DEL-001", "John Smith", "123 Main St", "Driver A", "In Transit", "$45.50"});
        tableModel.addRow(new Object[]{"DEL-002", "Mary Johnson", "456 Oak Ave", "Driver B", "In Transit", "$32.75"});
        tableModel.addRow(new Object[]{"DEL-003", "Robert Brown", "789 Pine Rd", "Driver A", "In Transit", "$58.20"});
        tableModel.addRow(new Object[]{"DEL-004", "Lisa Davis", "321 Elm St", "Driver C", "In Transit", "$41.00"});
        tableModel.addRow(new Object[]{"DEL-005", "James Wilson", "654 Maple Dr", "Driver B", "In Transit", "$67.90"});
    }

    private void loadDeliveryDetails() {
        int selectedRow = deliveryTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedDeliveryID = (String) tableModel.getValueAt(selectedRow, 0);
            String customer = (String) tableModel.getValueAt(selectedRow, 1);
            String address = (String) tableModel.getValueAt(selectedRow, 2);
            String fee = (String) tableModel.getValueAt(selectedRow, 5);

            selectedDeliveryLabel.setText("Selected: " + selectedDeliveryID);
            deliveryIDField.setText(selectedDeliveryID);
            customerNameField.setText(customer);
            deliveryAddressField.setText(address);
            deliveryFeeField.setText(fee);
            statusCombo.setSelectedIndex(0);
            paymentMethodCombo.setSelectedIndex(0);
            notesArea.setText("");
        }
    }

    private void processDelivery() {
        if (selectedDeliveryID == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a delivery from the table first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = (String) statusCombo.getSelectedItem();
        String paymentMethod = (String) paymentMethodCombo.getSelectedItem();
        String notes = notesArea.getText().trim();
        String deliveryFee = deliveryFeeField.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String actualDeliveryTime = sdf.format(new Date());

        if (status.equals("Successful")) {
            // Successful delivery
            // ACCESS: DRM(Delivery ID, Status, Payment Method, Delivery Fee)
            // UPDATE: DRM(Status, Actual Delivery Time, Payment Method)

            String message = String.format(
                    "Delivery Completed Successfully!\n\n" +
                            "=== DATABASE UPDATE ===\n" +
                            "Delivery ID: %s\n" +
                            "Status: %s → Completed\n" +
                            "Actual Delivery Time: %s\n" +
                            "Payment Method: %s\n" +
                            "Delivery Fee: %s\n" +
                            "Notes: %s",
                    selectedDeliveryID,
                    "In Transit",
                    actualDeliveryTime,
                    paymentMethod,
                    deliveryFee,
                    notes.isEmpty() ? "None" : notes
            );

            JOptionPane.showMessageDialog(this,
                    message,
                    "Successful Delivery",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            // Failed delivery
            // ACCESS: DRM(Delivery ID, Status)
            // UPDATE: DRM(Delivery ID, Status)

            String newStatus = status.contains("Convert to Pickup") ? "Converted to Pickup" : "Failed";

            String message = String.format(
                    "Delivery Failed - Status Updated\n\n" +
                            "=== DATABASE UPDATE ===\n" +
                            "Delivery ID: %s\n" +
                            "Old Status: In Transit\n" +
                            "New Status: %s\n" +
                            "Reason: %s\n" +
                            "Notes: %s",
                    selectedDeliveryID,
                    newStatus,
                    status,
                    notes.isEmpty() ? "None" : notes
            );

            JOptionPane.showMessageDialog(this,
                    message,
                    "Delivery Failed",
                    JOptionPane.WARNING_MESSAGE);
        }

        // Remove from pending deliveries table
        int selectedRow = deliveryTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }

        clearForm();
    }

    private void clearForm() {
        selectedDeliveryID = null;
        selectedDeliveryLabel.setText("No delivery selected");
        deliveryIDField.setText("");
        customerNameField.setText("");
        deliveryAddressField.setText("");
        deliveryFeeField.setText("");
        statusCombo.setSelectedIndex(0);
        paymentMethodCombo.setSelectedIndex(0);
        notesArea.setText("");
        deliveryTable.clearSelection();
    }

}