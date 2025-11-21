import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DriverGui{
    public static void main(String[] Args){
        JFrame frame = new JFrame("Delivery and Pickup Database - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        Dimension fixed = new Dimension(200, 30);
        Dimension buttonSize = new Dimension(120, 35);

        JPanel main = new JPanel();
        main.setBackground(Color.gray);
        main.setLayout(new GridBagLayout());
        GridBagConstraints mainGBC = new GridBagConstraints();
        mainGBC.insets = new Insets(10,10,10,10);

        JLabel header = new JLabel("Delivery and Pickup Database", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(Color.white);
        header.setBorder(BorderFactory.createLineBorder(Color.black, 4));

        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.gridwidth = 1;
        mainGBC.weightx = 1;
        mainGBC.fill = GridBagConstraints.HORIZONTAL;
        main.add(header, mainGBC);

        JPanel login = new JPanel();
        login.setOpaque(false);
        GridBagConstraints loginGBC = new GridBagConstraints();
        loginGBC.insets = new Insets(10,10,5,10);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginGBC.gridx = 0;
        loginGBC.gridy = 0;
        loginGBC.anchor = GridBagConstraints.LINE_END;
        loginGBC.insets = new Insets(10,10,10,5);
        login.add(userLabel, loginGBC);

        JTextField userField = new JTextField();
        userField.setBackground(Color.white);
        userField.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        userField.setPreferredSize(fixed);
        userField.setMinimumSize(fixed);
        userField.setMaximumSize(fixed);
        loginGBC.gridx = 1;
        loginGBC.gridy = 1;
        loginGBC.anchor = GridBagConstraints.LINE_START;
        loginGBC.insets = new Insets (10,0,10,10);
        login.add(userField, loginGBC);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginGBC.gridx = 0;
        loginGBC.gridy = 2;
        loginGBC.anchor = GridBagConstraints.LINE_END;
        loginGBC.insets = new Insets(10,10,10,5);
        login.add(passLabel, loginGBC);        

        JPasswordField passField = new JPasswordField();
        passField.setBackground(Color.white);
        passField.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        passField.setPreferredSize(fixed);
        passField.setMinimumSize(fixed);
        passField.setMaximumSize(fixed);
        loginGBC.gridx = 1;
        loginGBC.gridy = 3;
        loginGBC.anchor = GridBagConstraints.LINE_START;
        loginGBC.insets = new Insets (10,0,10,10);
        login.add(passField, loginGBC);        

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.white);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        loginButton.setPreferredSize(buttonSize);
        loginGBC.gridx = 0;
        loginGBC.gridy = 2;
        loginGBC.gridwidth = 2;
        loginGBC.anchor = GridBagConstraints.CENTER;
        loginGBC.insets = new Insets(10,10,10,10);
        login.add(loginButton, loginGBC);
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String user = userField.getText();
                String pass = new String(passField.getPassword());

                if (LoginHandler.checkCreds(user, pass)){
                    frame.dispose();
                    SwingUtilities.invokeLater(()->{
                        new MainMenu();
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainGBC.gridx = 0;
        mainGBC.gridy = 1;
        mainGBC.weightx = 1;
        mainGBC.weighty = 1;
        mainGBC.fill = GridBagConstraints.NONE;
        mainGBC.anchor = GridBagConstraints.CENTER;
        mainGBC.ipady = 0;
        mainGBC.insets = new Insets(10, 10, 10, 10);

        main.add(login, mainGBC);
        frame.add(main);
        frame.setVisible(true);


        

    }
}