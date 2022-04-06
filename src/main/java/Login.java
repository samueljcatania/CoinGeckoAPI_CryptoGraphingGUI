import com.google.gson.*;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

/**
 * Login class shows a UI that takes user input for their username and password. If credentials are valid, then
 * MainUI is launched. Otherwise, the program exits.
 *
 * @author Samuel Catania
 */

public class Login {

    private final JTextField usernameField;
    private final JTextField passwordField;
    private final JFrame loginFrame;

    /**
     * Constructor for Login, which takes and checks user credentials.
     *
     */
    public Login() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(350, 125);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");

        JButton submit = new JButton("Submit");

        submit.addActionListener(l -> {
            checkPassword();
        });

        panel.add(username);
        username.setLabelFor(usernameField);
        panel.add(usernameField);

        panel.add(password);
        password.setLabelFor(passwordField);
        panel.add(passwordField);

        panel.add(submit);

        //Display the window
        loginFrame.setContentPane(panel);
        loginFrame.setVisible(true);
    }

    /**
     * Helper method to Login constructor to check validity of user password.
     *
     */
    private void checkPassword() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean validPassword = false;

        JsonParser jsonParser = new JsonParser();

        try (FileReader reader = new FileReader("src/main/text/credentials.json")) {

            Object object = jsonParser.parse(reader);

            JsonArray jsonArray = (JsonArray) object;

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = (JsonObject) jsonArray.get(i);

                if (jsonObject.get("username").getAsString().equals(username) && jsonObject.get("password").getAsString().equals(password)) {
                    validPassword = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (validPassword) {
            usernameField.setText("");
            passwordField.setText("");
            loginFrame.setVisible(false);

            JFrame mainUIFrame = MainUI.getInstance();
            mainUIFrame.setSize(900, 600);
            mainUIFrame.pack();
            mainUIFrame.setLocationRelativeTo(null);
            mainUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainUIFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid Username or Password. The application will now terminate.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Main is the entrypoint to the LemonMist cryptotrading program. It first asks for credentials,
     * and if they are valid, then it launches MainUI.
     *
     * @param args command line arguments (none needed to launch the program normally.)
     */
    public static void main(String[] args) {
        new Login();
    }
}
