package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LigneCommande {
	public int nblc = -1;
	public int idLc;
    public LigneCommande() {
    }

    public int qte;

    public Commande commande;

    public Produit produit;

    public LigneCommande(int qte, Produit produit, Commande commande) {
        this.qte = qte;
        this.produit = produit;
        this.commande = commande;
        if (nblc == -1) {
            syncNbPWithDatabase();
        }
        this.idLc = ++nblc;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Get the database connection
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter les données.");
                return;
            }

            // SQL query to insert into the Personne table
            String sql = "INSERT INTO LigneCommande (Id, Id_Commande, Id_Produit, Quantite) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement
            stmt = conn.prepareStatement(sql);

            // Set the values for the placeholders
            stmt.setInt(1, idLc);
            stmt.setInt(2, commande.idCommande);
            stmt.setInt(3, produit.idP);
            stmt.setInt(4, qte);
           

            // Execute the SQL update
            stmt.executeUpdate();

            System.out.println("Données insérées avec succès dans la table LigneCommande !");
        } catch (SQLException e) {
            // Handle SQL errors
            System.err.println("Erreur lors de l'insertion des données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }
    private void syncNbPWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nblc.");
                nblc = 0; // Default to 0 if the database is unavailable
                return;
            }

            String sql = "SELECT MAX(Id) AS maxId FROM LigneCommande";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nblc = rs.getInt("maxId");
            } else {
                nblc= 0; // Default to 0 if the table is empty
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nblc : " + e.getMessage());
            e.printStackTrace();
            nblc = 0; // Default to 0 in case of an error
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    public float getPrice() {
        return qte* produit.prix;
    }
}