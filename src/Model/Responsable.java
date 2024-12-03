package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Responsable extends Personne {

    public static int nbResponsable = -1; // Initialisé à -1 pour forcer la synchronisation avec la base.
    public int idR;

    private String login;
    private String mdp;

    private Vector<Client> listClient = new Vector<>();
    public Vector<Commande> listeCommande = new Vector<>();

    public Responsable(String nom, String prenom, String adresse, String tel, String email, String login, String mdp) throws SQLException {
        super(nom, prenom, adresse, tel, email,mdp);

        this.login = login;
        this.mdp = mdp;

        if (nbResponsable == -1) {
            syncNbResponsableWithDatabase();
        }
        idR = ++nbResponsable;

        // Insérer dans la base de données directement ici
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter les données.");
                return;
            }

            // SQL pour insérer un responsable
            String sql = "INSERT INTO responsable (id_Responsable, Login, Mdp) VALUES (?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idR);
            stmt.setString(2, this.login);
            stmt.setString(3, this.mdp);
            

            stmt.executeUpdate();

            System.out.println("Données insérées avec succès dans la table Responsable !");
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
     * Synchronise `nbResponsable` avec la base de données pour éviter les conflits de clés primaires.
     */
    private void syncNbResponsableWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nbResponsable.");
                nbResponsable = 0; // Défaut si la connexion échoue.
                return;
            }

            String sql = "SELECT MAX(id_Responsable) AS maxId FROM responsable";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nbResponsable = rs.getInt("maxId");
            } else {
                nbResponsable = 0; // Défaut si la table est vide.
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nbResponsable : " + e.getMessage());
            e.printStackTrace();
            nbResponsable = 0; // Défaut en cas d'erreur.
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


    /*public void ChangerEtatCommande(void Etat nvEtat) {
        // TODO implement here
    }*/

// a verifier si a ajouter ou pas
//    public void ajouteCommande(Commande commande) {
//
//        listeCommande.add(commande);
//    }

}