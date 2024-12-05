package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

        tableModelProduits = new DefaultTableModel(new String[]{"Nom", "Prix", "Quantité","image"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non modifiables
            }
        };
        tableProduits = new JTable(tableModelProduits);
        tableProduits.setRowHeight(100);
        tableProduits.getColumnModel().getColumn(3).setPreferredWidth(100);
        JScrollPane produitsScrollPane = new JScrollPane(tableProduits);

        tableModelPanier = new DefaultTableModel(new String[]{"Nom", "Quantité", "Prix Unitaire", "Prix Total"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non modifiables
            }
        };

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
        for (int rowIndex = 0; rowIndex < produits.size(); rowIndex++) {
            Vector<Object> produit = produits.get(rowIndex);
            if (produit.size() >= 4) {  // Ensure there are at least 4 columns (Name, Price, Availability, Image URL)
                String imageUrl = (String) produit.get(3);  // Get the image URL from the 4th column
                // Set a placeholder first
                produit.set(3, new ImageIcon("path/to/placeholder.png"));
                tableModelProduits.addRow(produit);  // Add the row with placeholder
                // Use SwingWorker to load the image asynchronously
                int finalRowIndex = rowIndex;
                SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        return getImageFromUrl(imageUrl); // Fetch and scale the image
                    }
                    private ImageIcon getImageFromUrl(String imageUrl) {
                        try {
                            URL url = new URL(imageUrl);
                            BufferedImage img = ImageIO.read(url);
                            // Scale image to fit the cell size
                            return new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        } catch (Exception e) {
                            System.err.println("Error loading image from: " + imageUrl);
                            e.printStackTrace();
                            return new ImageIcon("path/to/placeholder.png"); // Fallback placeholder
                        }
                    }
                    @Override
                    protected void done() {
                        try {
                            ImageIcon productImage = get(); // Get the loaded image
                            tableModelProduits.setValueAt(productImage, finalRowIndex, 3); // Update the cell
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                worker.execute(); // Start the image loading task
            } else {
                System.err.println("Invalid product data: " + produit);
            }
        }
        // Set the custom renderer for the image column (index 3)
        tableProduits.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
        tableProduits.getColumnModel().getColumn(3).setPreferredWidth(100); // Adjust column width
        tableProduits.setRowHeight(100); // Adjust row height to fit images
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
class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            JLabel label = new JLabel((ImageIcon) value);
            label.setHorizontalAlignment(JLabel.CENTER); // Center the image
            return label;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }}
