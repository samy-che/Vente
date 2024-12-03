package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Produit {

    public static int nbProduit = -1; // Initialisé à -1 pour forcer la synchronisation avec la base.
    public int idP;
    public String nom;
    public Float prix;
    public String description;
    public int stockQuantite;
    public boolean estDispo = false;

    public Produit(String nom, Float prix, String description, int stockQuantite) throws SQLException {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.stockQuantite = stockQuantite;
        this.estDispo = stockQuantite > 0; // Disponible si la quantité en stock est > 0.

        if (nbProduit == -1) {
            syncNbProduitWithDatabase();
        }
        this.idP = ++nbProduit;

        // Insérer dans la base de données directement ici
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter les données.");
                return;
            }

            // SQL pour insérer un produit
            String sql = "INSERT INTO produit (Id, Nom, Prix, Description, Quantite, EstDispo) VALUES (?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.idP);
            stmt.setString(2, this.nom);
            stmt.setFloat(3, this.prix);
            stmt.setString(4, this.description);
            stmt.setInt(5, this.stockQuantite);
            stmt.setBoolean(6, this.estDispo);

            stmt.executeUpdate();

            System.out.println("Données insérées avec succès dans la table Produit !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion des données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Synchronise `nbProduit` avec la base de données pour éviter les conflits de clés primaires.
     */
    private void syncNbProduitWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nbProduit.");
                nbProduit = 0; // Défaut si la connexion échoue.
                return;
            }

            String sql = "SELECT MAX(Id) AS maxId FROM produit";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nbProduit = rs.getInt("maxId");
            } else {
                nbProduit = 0; // Défaut si la table est vide.
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nbProduit : " + e.getMessage());
            e.printStackTrace();
            nbProduit = 0; // Défaut en cas d'erreur.
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    public Vector<LigneCommande> listeLigneC = new Vector<LigneCommande>();

    // à checker
    public void ajouteLigneCommande(LigneCommande ligneCommande){
        listeLigneC.add(ligneCommande);
    }

}