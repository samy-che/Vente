package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class Vente extends JFrame {

    private JTable tableProduits;
    private DefaultTableModel tableModelProduits;
    private JTable tablePanier;
    private DefaultTableModel tableModelPanier;
    private JTextField rechercheField;
    private Connection connection;

    public Vente(Connection connection) {
        this.connection = connection;
        this.setTitle("Catalogue des Produits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));

        JPanel nordPanel = new JPanel();
        JLabel rechercheLabel = new JLabel("Recherche :");
        rechercheField = new JTextField(20);
        JButton rechercheButton = new JButton("Rechercher");
        rechercheButton.addActionListener(e -> rechercherProduits());

        nordPanel.add(rechercheLabel);
        nordPanel.add(rechercheField);
        nordPanel.add(rechercheButton);

        tableModelProduits = new DefaultTableModel(new String[]{"Nom", "Prix"}, 0);
        tableProduits = new JTable(tableModelProduits);
        JScrollPane produitsScrollPane = new JScrollPane(tableProduits);

        tableModelPanier = new DefaultTableModel(new String[]{"Nom", "Quantité", "Prix Unitaire", "Prix Total"}, 0);
        tablePanier = new JTable(tableModelPanier);
        JScrollPane panierScrollPane = new JScrollPane(tablePanier);

        JButton ajouterPanierButton = new JButton("Ajouter au panier");
        ajouterPanierButton.addActionListener(e -> ajouterAuPanier());

        JButton supprimerPanierButton = new JButton("Supprimer du panier");
        supprimerPanierButton.addActionListener(e -> supprimerDuPanier());

        JButton validerPanierButton = new JButton("Valider les achats");
        validerPanierButton.addActionListener(e -> validerPanier());

        JPanel boutonsPanel = new JPanel();
        boutonsPanel.add(ajouterPanierButton);
        boutonsPanel.add(supprimerPanierButton);
        boutonsPanel.add(validerPanierButton);

        this.setLayout(new BorderLayout());
        this.add(nordPanel, BorderLayout.NORTH);
        this.add(produitsScrollPane, BorderLayout.WEST);
        this.add(panierScrollPane, BorderLayout.CENTER);
        this.add(boutonsPanel, BorderLayout.SOUTH);

        chargerProduits();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void chargerProduits() {
        try {
            String query = "SELECT nom, prix FROM produit";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModelProduits.addRow(new Object[]{rs.getString("nom"), rs.getDouble("prix")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des produits : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherProduits() {
        String recherche = rechercheField.getText().toLowerCase();
        tableModelProduits.setRowCount(0);

        try {
            String query = "SELECT nom, prix FROM produit WHERE LOWER(nom) LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + recherche + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModelProduits.addRow(new Object[]{rs.getString("nom"), rs.getDouble("prix")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterAuPanier() {
        int selectedRow = tableProduits.getSelectedRow();
        if (selectedRow != -1) {
            String nom = (String) tableModelProduits.getValueAt(selectedRow, 0);
            double prix = (double) tableModelProduits.getValueAt(selectedRow, 1);
            tableModelPanier.addRow(new Object[]{nom, 1, prix, prix});
        }
    }

    private void supprimerDuPanier() {
        int selectedRow = tablePanier.getSelectedRow();
        if (selectedRow != -1) {
            tableModelPanier.removeRow(selectedRow);
        }
    }

    private void validerPanier() {
        JOptionPane.showMessageDialog(this, "Achat validé !");
        tableModelPanier.setRowCount(0);
    }
}
