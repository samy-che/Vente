package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Personne {

	public static int nbP = 4;

    public int idP;

    public String nom;

    public String prenom;

    public String adresse;

    public String tel;

    public String email;

    public Personne(String nom, String prenom, String adresse, String tel, String email)throws SQLException {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        idP = ++nbP;
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Get the database connection
            conn = DatabaseConnection.getConnection();
            
            // Check if the connection is valid
            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter les données.");
                return;
            }

            // Add the commande to the list
            

            // SQL query to insert into the Personne table
            String sql = "INSERT INTO personne (Nom, Prenom, Adresse, Telephone) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement
            stmt = conn.prepareStatement(sql);
            
            // Set the values for the placeholders
            stmt.setString(1, this.nom);
            stmt.setString(2, this.prenom);
            stmt.setString(3, this.adresse);
            stmt.setString(4, this.tel);

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
            }}
        
    }

}