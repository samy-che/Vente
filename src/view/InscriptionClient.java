package view;

import javax.swing.*;
import java.awt.*;


public class InscriptionClient extends JFrame {
    private Menu menu;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    public InscriptionClient(Menu menu) {
        this.menu = menu;

        this.setTitle("Inscription Client");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 450));
        this.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passwordField);
        panel.add(new JLabel("Confirmer mot de passe:"));
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Confirmer l'inscription");
        JButton backButton = new JButton("Retour");

        registerButton.addActionListener(e -> registerClient());
        backButton.addActionListener(e -> {
            new ClientLogin(menu);
            dispose();
        });

        panel.add(backButton);
        panel.add(registerButton);

        this.add(panel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void registerClient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation des champs
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

//        try {
//            if (dbManager.registerClient(nom, prenom, email, password)) {
//                JOptionPane.showMessageDialog(this, "Inscription réussie!");
//                new ClientLogin(menu);
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription",
//                        "Erreur", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage(),
//                    "Erreur", JOptionPane.ERROR_MESSAGE);
//        }
    }
}
