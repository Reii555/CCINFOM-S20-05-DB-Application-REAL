import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JFrame{
    public MainMenu(){
        setTitle("Delivery and Pickup Database - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.lightGray);

        JLabel header = new JLabel("Delivery and Pickup Database", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 32));
        header.setOpaque(true);
        header.setBackground(Color.white);
        header.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        main.add(header, BorderLayout.NORTH);
        
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(Color.gray);
        body.setBorder(new EmptyBorder(15,15,15,15));

        GridBagConstraints GBC = new GridBagConstraints();
        GBC.insets = new Insets (10, 10, 10, 10);
        GBC.fill = GridBagConstraints.BOTH;
        GBC.weighty = 1;

        Dimension buttonD = new Dimension(250,40);

        GBC.gridx = 0;
        GBC.weightx = 0.4;
        JPanel records = createRecords(Color.lightGray);
        body.add(records, GBC);

        GBC.gridx = 1;
        GBC.weightx = 0.3;
        JPanel transactions = createTransactions(Color.lightGray, Color.darkGray, buttonD);
        body.add(transactions, GBC);

        GBC.gridx = 2;
        GBC.weightx = 0.3;
        JPanel reports = createReports(Color.lightGray, Color.darkGray, buttonD);
        body.add(reports, GBC);

        main.add(body,BorderLayout.CENTER);
        add(main);
        setVisible(true);

    }

    private JButton createMenuB(String text, Color color, Dimension dim){
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button.setPreferredSize(dim);
        button.setMinimumSize(dim);
        button.setMaximumSize(dim);
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createRecords(Color color){
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel title = new JLabel("Records");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        top.add(title);

        String[] records = {"Delivery Record", "Pickup Record", "Driver Record", "Customer Record"};
        JComboBox<String> recordDropdown = new JComboBox<>(records);
        recordDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        recordDropdown.setSelectedIndex(0);
        recordDropdown.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        recordDropdown.setPreferredSize(new Dimension(200, 30));
        top.add(recordDropdown);
        
        panel.add(top, BorderLayout.NORTH);

        JLabel content = new JLabel("View records here", SwingConstants.CENTER);
        content.setFont(new Font("Arial", Font.ITALIC, 16));
        content.setForeground(Color.darkGray);
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

private JPanel createTransactions(Color color, Color button, Dimension dim){
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(color);
    panel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel title = new JLabel("Transactions", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setBorder(BorderFactory.createLineBorder(Color.black));
    panel.add(title, BorderLayout.NORTH);

    JPanel buttonHolder = new JPanel (new GridBagLayout());
    buttonHolder.setOpaque(false);
    GridBagConstraints GBC = new GridBagConstraints();
    GBC.insets = new Insets (10,10,10,10);
    GBC.gridx = 0;
    GBC.fill = GridBagConstraints.HORIZONTAL;

    JButton ddaButton = createMenuB("Driver and Delivery Assignment", Color.white, dim);
    ddaButton.addActionListener(e -> new DriverDeliveryAssignmentPanel());
    GBC.gridy = 0;
    buttonHolder.add(ddaButton, GBC);

    JButton dcppButton = createMenuB("Delivery Completion & Payment Processing", Color.white, dim);
    dcppButton.addActionListener(e -> new DeliveryCompletionPanel());
    GBC.gridy = 1;
    buttonHolder.add(dcppButton, GBC);

    JButton ppcvButton = createMenuB("Pickup Preparation & Customer Verification", Color.white, dim);
    ppcvButton.addActionListener(e -> new PickupPrepPanel());
    GBC.gridy = 2;
    buttonHolder.add(ppcvButton, GBC);

    JButton pcppButton = createMenuB("Pickup Completion & Payment Processing", Color.white, dim);
    pcppButton.addActionListener(e -> new PickupManagementPanel());
    GBC.gridy = 3;
    buttonHolder.add(pcppButton, GBC);

    panel.add(buttonHolder, BorderLayout.CENTER);

    JButton exit = createMenuB("EXIT", Color.red, new Dimension(150, 40));
    exit.addActionListener(e->dispose());

    JPanel exitWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 ,10));
    exitWrapper.setOpaque(false);
    exitWrapper.add(exit);
    panel.add(exitWrapper, BorderLayout.SOUTH);

    return panel;
}

private JPanel createReports(Color color, Color button, Dimension dim){
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(color);
    panel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel title = new JLabel("Reports", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setBorder(BorderFactory.createLineBorder(Color.black));
    panel.add(title, BorderLayout.NORTH);

    JPanel buttonHolder = new JPanel(new GridBagLayout());
    buttonHolder.setOpaque(false);
    GridBagConstraints GBC = new GridBagConstraints();
    GBC.insets = new Insets (10,10,10,10);
    GBC.gridx = 0;
    GBC.fill = GridBagConstraints.HORIZONTAL;

    JButton coButton = createMenuB("Customer Orders Report", Color.white, dim);
    coButton.addActionListener(e -> new CustomerOrdersReportUI());
    GBC.gridy = 0;
    buttonHolder.add(coButton, GBC);

    JButton dldButton = createMenuB("Delivery Location Density Report", Color.white, dim);
    dldButton.addActionListener(e -> new DeliveryLocationDensityReport());
    GBC.gridy = 1;
    buttonHolder.add(dldButton, GBC);

    JButton ddfButton = createMenuB("Delivery Driver Frequency Report", Color.white, dim);
    ddfButton.addActionListener(e -> new DeliveryDriverFrequencyReport());
    GBC.gridy = 2;
    buttonHolder.add(ddfButton, GBC);

    JButton cpButton = createMenuB("Customer Preferences Report", Color.white, dim);
    cpButton.addActionListener(e -> new CustomerPreferencesReportUI());
    GBC.gridy = 3;
    buttonHolder.add(cpButton, GBC);

    panel.add(buttonHolder, BorderLayout.CENTER);
    return panel;
}

}
