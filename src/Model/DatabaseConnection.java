package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	

    public static Connection getConnection() {
    	String url = "jdbc:postgresql://localhost:5432/Projet_Java";
        String user = "postgres";
        String password = "mdpjava";

        Connection connection = null;

        try {
            // Établir la connexion
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connexion réussie à PostgreSQL !");
            } else {
                System.out.println("Échec de la connexion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
        
    }

    
}
