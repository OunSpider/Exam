import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecureApp {
    private JFrame appFrame;
    private String currentPassword = null;
    private StringBuilder inputPassword = new StringBuilder();

    public void createFrame() {
        // Create the frame
        appFrame = new JFrame("Secure Application");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setSize(300, 200);
        appFrame.setLayout(new BorderLayout());

        // Create a panel for digit buttons
        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(3, 3));

        // Create digit buttons
        JButton[] digitButtons = new JButton[9];
        for (int i = 1; i <= 9; i++) {
            digitButtons[i - 1] = new JButton(String.valueOf(i));
            digitButtons[i - 1].addActionListener(new DigitButtonListener());
            digitPanel.add(digitButtons[i - 1]);
        }

        // Create panel for the text field and control buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JTextField passwordField = new JTextField(10);
        passwordField.setEditable(false);
        JButton enterButton = new JButton("Enter");
        JButton clearButton = new JButton("Clear");

        actionPanel.add(clearButton);
        actionPanel.add(passwordField);
        actionPanel.add(enterButton);

        // Create a label for status messages
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        // Add action listener to the enter button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPassword == null) {
                    // Setting the password for the first time
                    currentPassword = inputPassword.toString();
                    messageLabel.setText("Password Set");
                } else {
                    // Verifying the password
                    if (currentPassword.equals(inputPassword.toString())) {
                        messageLabel.setText("Correct Password");
                        JOptionPane.showMessageDialog(appFrame, "Correct Password", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        messageLabel.setText("Incorrect Password");
                        JOptionPane.showMessageDialog(appFrame, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                inputPassword.setLength(0); // Clear the entered password
                passwordField.setText(""); // Clear the password field
            }
        });

        // Add action listener to the clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputPassword.setLength(0); // Clear the entered password
                passwordField.setText(""); // Clear the password field
                messageLabel.setText(""); // Clear the status label
                currentPassword = null; // Reset stored password
            }
        });

        // Add panels to the frame
        appFrame.add(digitPanel, BorderLayout.CENTER);
        appFrame.add(actionPanel, BorderLayout.SOUTH);
        appFrame.add(messageLabel, BorderLayout.NORTH);

        // Set the frame visibility
        appFrame.setVisible(true);
    }

    private class DigitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            inputPassword.append(source.getText());
            JTextField passwordField = (JTextField) ((JPanel) source.getParent().getParent().getComponent(1)).getComponent(1);
            passwordField.setText(inputPassword.toString());
        }
    }
}