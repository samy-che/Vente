package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Client extends Personne {

    public Vector<Commande> listeCmd = new Vector<>();
    public static int nbClient = -1; // Initialisé à -1 pour synchronisation avec la base
    public int idC;

    public Client(String nom, String prenom, String adress, String tel, String email,String mdp) throws SQLException {
        super(nom, prenom, adress, tel, email,mdp);
        if (nbClient == -1) {
            syncNbClientWithDatabase();
        }
        idC = ++nbClient;


        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter les données.");
                return;
            }

            // Insertion dans la table `clients`
            String sql = "INSERT INTO clients (Id_Client) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idC);
            stmt.executeUpdate();

            System.out.println("Données insérées avec succès dans la table clients !");
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

    public void ajouteCommande(Commande commande) {
        listeCmd.add(commande);
    }

    // Synchronisation de nbClient avec la base de données
    private void syncNbClientWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nbClient.");
                nbClient = 0; // Valeur par défaut en cas d'échec de connexion
                return;
            }

            String sql = "SELECT MAX(Id_Client) AS maxId FROM clients";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nbClient = rs.getInt("maxId");
            } else {
                nbClient = 0; // Valeur par défaut si la table est vide
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nbClient : " + e.getMessage());
            e.printStackTrace();
            nbClient = 0; // Valeur par défaut en cas d'erreur
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
}