package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne {

	public static int nbP = -1; // Initializing to an invalid value to ensure DB sync happens.
    public int idP;
    public String nom;
    public String prenom;
    public String adresse;
    public String tel;
    public String email;
    public String mdp;

    public Personne(String nom, String prenom, String adresse, String tel, String email,String mdp) throws SQLException {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.mdp = mdp;


        if (nbP == -1) {
            syncNbPWithDatabase();
        }
        idP = ++nbP;

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
            String sql = "INSERT INTO personne (id_Personne, Nom, Prenom, Adresse, Telephone, Email, mdp) VALUES (?, ?, ?, ?, ?, ?,?)";

            // Create a PreparedStatement
            stmt = conn.prepareStatement(sql);

            // Set the values for the placeholders
            stmt.setInt(1, idP);
            stmt.setString(2, this.nom);
            stmt.setString(3, this.prenom);
            stmt.setString(4, this.adresse);
            stmt.setString(5, this.tel);
            stmt.setString(6, this.email);
            stmt.setString(7, this.mdp);

            // Execute the SQL update
            stmt.executeUpdate();

            System.out.println("Données insérées avec succès dans la table Personne !");
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

    // Synchronize nbP with the database
    private void syncNbPWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nbP.");
                nbP = 0; // Default to 0 if the database is unavailable
                return;
            }

            String sql = "SELECT MAX(Id_Personne) AS maxId FROM personne";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nbP = rs.getInt("maxId");
            } else {
                nbP = 0; // Default to 0 if the table is empty
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nbP : " + e.getMessage());
            e.printStackTrace();
            nbP = 0; // Default to 0 in case of an error
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

}