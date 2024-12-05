package controller;

import view.Vente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class VenteListener {

    private Vente vue;
    private Connection connection;

    public VenteListener(Vente vue) {
        this.vue = vue;
        this.connection = vue.connection;  // Récupère la connexion de la vue
    }

    // Méthode pour charger les produits disponibles
    public void chargerProduits() {
        try {

            String query = "SELECT nom, prix, quantite, photo FROM produit WHERE estDispo = true";

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            Vector<Vector<Object>> produits = new Vector<>();
            while (rs.next()) {
                Vector<Object> produit = new Vector<>();
                produit.add(rs.getString("nom"));              // Nom du produit
                produit.add(rs.getDouble("prix"));             // Prix du produit
                produit.add(rs.getInt("quantite"));  // Disponibilité
                produit.add(rs.getString("photo"));        // Image URL du produit
                produits.add(produit);
            }

            // Transfert des produits à la vue avec l'image URL
            vue.afficherProduits(produits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour rechercher des produits
    public void rechercherProduits(String recherche) {
        try {
            String query = "SELECT nom, prix, estDispo, photo FROM produit WHERE LOWER(nom) LIKE ? AND estDispo = true";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + recherche + "%");
            ResultSet rs = stmt.executeQuery();

            Vector<Vector<Object>> produits = new Vector<>();
            while (rs.next()) {
                Vector<Object> produit = new Vector<>();
                produit.add(rs.getString("nom"));              // Nom du produit
                produit.add(rs.getDouble("prix"));             // Prix du produit
                produit.add(rs.getInt("quantite"));// Disponibilité
                produit.add(rs.getString("photo"));        // Image URL du produit
                produits.add(produit);
            }

            // Transfert des produits à la vue
            vue.afficherProduits(produits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un produit au panier
    public void ajouterAuPanier() {
        int selectedRow = vue.tableProduits.getSelectedRow();
        if (selectedRow != -1) {
            String nom = (String) vue.tableModelProduits.getValueAt(selectedRow, 0);
            double prix = (double) vue.tableModelProduits.getValueAt(selectedRow, 1);
            int quantity = (int) vue.quantityComboBox.getSelectedItem();

            if (quantity > 0) {
                vue.ajouterAuPanier(nom, prix, quantity);

                // Mise à jour du stock dans la base de données
                mettreAJourStock(nom, quantity);
            }
        }
    }

    // Méthode pour mettre à jour le stock de produit dans la base de données
    private void mettreAJourStock(String nom, int quantity) {
        try {
            String query = "UPDATE produit SET Quantite = Quantite - ? WHERE nom = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setString(2, nom);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour valider le panier
    public void validerPanier() {
        try {
            // Effectuer une logique de commande ici
            System.out.println("Commande validée !");
            // Ici, vous pouvez ajouter du code pour enregistrer la commande dans la base de données
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
