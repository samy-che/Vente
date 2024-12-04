package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import controller.VenteListener;

public class Vente extends JFrame {

    public JTable tableProduits;
    public DefaultTableModel tableModelProduits;
    private JTable tablePanier;
    private DefaultTableModel tableModelPanier;
    private JTextField rechercheField;
    public Connection connection;
    private VenteListener controller;
    public JComboBox<Integer> quantityComboBox;  // Menu déroulant pour sélectionner la quantité

    public Vente(Connection connection) {
        this.connection = connection;
        this.controller = new VenteListener(this);  // Création du contrôleur
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

        tableModelProduits = new DefaultTableModel(new String[]{"Nom", "Prix", "Disponible"}, 0);
        tableProduits = new JTable(tableModelProduits);
        JScrollPane produitsScrollPane = new JScrollPane(tableProduits);

        tableModelPanier = new DefaultTableModel(new String[]{"Nom", "Quantité", "Prix Unitaire", "Prix Total"}, 0);
        tablePanier = new JTable(tableModelPanier);
        JScrollPane panierScrollPane = new JScrollPane(tablePanier);

        quantityComboBox = new JComboBox<>();  // ComboBox pour la quantité
        for (int i = 1; i <= 10; i++) {
            quantityComboBox.addItem(i);
        }

        JButton ajouterPanierButton = new JButton("Ajouter au panier");
        ajouterPanierButton.addActionListener(e -> controller.ajouterAuPanier());

        JButton supprimerPanierButton = new JButton("Supprimer du panier");
        supprimerPanierButton.addActionListener(e -> supprimerDuPanier());

        JButton validerPanierButton = new JButton("Valider les achats");
        validerPanierButton.addActionListener(e -> validerPanier());

        JPanel boutonsPanel = new JPanel();
        boutonsPanel.add(new JLabel("Quantité :"));
        boutonsPanel.add(quantityComboBox);
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
        controller.chargerProduits();
    }

    public void afficherProduits(Vector<Vector<Object>> produits) {
        tableModelProduits.setRowCount(0);  // Réinitialise le tableau des produits
        for (Vector<Object> produit : produits) {
            tableModelProduits.addRow(produit);
        }
    }

    private void rechercherProduits() {
        String recherche = rechercheField.getText().toLowerCase();
        controller.rechercherProduits(recherche);
    }

    public void ajouterAuPanier(String nom, double prix, int quantity) {
        double total = prix * quantity;
        int rowIndex = getProduitInPanier(nom);

        if (rowIndex != -1) {
            // Si le produit existe déjà, on augmente la quantité
            int currentQuantity = (int) tableModelPanier.getValueAt(rowIndex, 1);
            tableModelPanier.setValueAt(currentQuantity + quantity, rowIndex, 1);
            tableModelPanier.setValueAt((currentQuantity + quantity) * prix, rowIndex, 3);
        } else {
            tableModelPanier.addRow(new Object[]{nom, quantity, prix, total});
        }
    }

    private int getProduitInPanier(String nom) {
        for (int i = 0; i < tableModelPanier.getRowCount(); i++) {
            if (tableModelPanier.getValueAt(i, 0).equals(nom)) {
                return i;  // Le produit est déjà dans le panier
            }
        }
        return -1;  // Le produit n'est pas dans le panier
    }

    private void supprimerDuPanier() {
        int selectedRow = tablePanier.getSelectedRow();
        if (selectedRow != -1) {
            tableModelPanier.removeRow(selectedRow);
        }
    }

    private void validerPanier() {
        controller.validerPanier();
        tableModelPanier.setRowCount(0);  // Réinitialise le panier après validation
    }
}
