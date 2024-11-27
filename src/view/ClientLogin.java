package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ClientLogin extends JFrame {

    private Menu menu;
    private JTextField emailField;
    private JTextField passwordField;

    public ClientLogin(Menu menu) {
        this.setTitle("Connexion Client");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));
        this.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Se connecter");
        JButton backButton = new JButton("Retour");
        JButton registerButton = new JButton("S'inscrire");

        loginButton.addActionListener(e -> authenticateUser());
        registerButton.addActionListener(e -> openRegistrationForm());

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);
        panel.add(registerButton);

        this.add(panel);

        // Action du bouton "Retour"
        backButton.addActionListener(e -> {
            dispose(); // Ferme la fenêtre actuelle
            menu.setVisible(true);// Retourne au menu principal
        });


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void authenticateUser() {
        String email = emailField.getText();;
        String password = new String(passwordField.getText());

//        try {
//            if (dbManager.authenticateClient(email, password)) {
//                JOptionPane.showMessageDialog(this, "Connexion réussie!");
//                // Ici, vous pouvez ouvrir l'écran principal du client
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect",
//                        "Erreur de connexion",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Erreur de connexion : " + e.getMessage(),
//                    "Erreur", JOptionPane.ERROR_MESSAGE);
//        }
    }

    private void openRegistrationForm() {
        new InscriptionClient(menu);
        dispose();
    }
}
