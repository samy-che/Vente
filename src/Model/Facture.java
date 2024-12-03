package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Facture {

    public static int cptFacture = -1; // Initialisé à -1 pour synchronisation avec la base.
    public int id;
    public float total;
    public Date date;
    public Commande commande;

    public Facture(Commande commande) throws SQLException {
        if (cptFacture == -1) {
            syncCptFactureWithDatabase();
        }
        this.id = ++cptFacture;
        this.commande = commande;
        this.date = new Date();
        this.total = commande.getPrice();

        // Insertion dans la base de données
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter la facture.");
                return;
            }

            // SQL pour insérer une facture
            String sql = "INSERT INTO facture (Id, Id_Commande, Date) VALUES (?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.id);
            stmt.setInt(2, this.commande.idCommande); // Associer à la commande
            stmt.setDate(3, new java.sql.Date(this.date.getTime())); // Conversion de Date en java.sql.Date

            stmt.executeUpdate();

            System.out.println("Facture insérée avec succès dans la table !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la facture : " + e.getMessage());
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
     * Synchronise `cptFacture` avec la base de données pour éviter les conflits de clés primaires.
     */
    private void syncCptFactureWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser cptFacture.");
                cptFacture = 0; // Défaut si la connexion échoue.
                return;
            }

            String sql = "SELECT MAX(Id) AS maxId FROM facture";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cptFacture = rs.getInt("maxId");
            } else {
                cptFacture = 0; // Défaut si la table est vide.
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de cptFacture : " + e.getMessage());
            e.printStackTrace();
            cptFacture = 0; // Défaut en cas d'erreur.
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

    public void AfficherFacture() {
        // TODO implement here
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

}