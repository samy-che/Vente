package Model;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Client extends Personne {
	
    
    
    public Vector<Commande> listeCmd = new Vector<Commande>();

    public static int nbClient = 0;

    public int idC;
    
    
    public Client(Personne p) throws SQLException {
        super(p.nom, p.prenom, p.adresse, p.tel, p.email);
        idC = p.idP;
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
            String sql = "INSERT INTO clients (Id_Client) VALUES (?)";

            // Create a PreparedStatement
            stmt = conn.prepareStatement(sql);
            
            // Set the values for the placeholders
            stmt.setInt(1, idC);
            

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
    
    public void ajouteCommande(Commande commande) {
    	listeCmd.add(commande);
    }

}