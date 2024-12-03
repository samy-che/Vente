package Model;
import java.time.LocalDate;
import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

public class Commande {

    public static int nbCommande = -1; // Initialisé à -1 pour forcer la synchronisation avec la base.
    public LocalDate date;
    public Etat etat;
    public Client client;
    public int idCommande;
    public Vector<LigneCommande> listeLigneCmd = new Vector<>();
    public Facture facture;

    public Commande(LocalDate date, Client client) throws SQLException {
        this.date = date;
        this.etat = Etat.EN_COURS;
        this.client = client;

        if (nbCommande == -1) {
            syncNbCommandeWithDatabase();
        }
        this.idCommande = ++nbCommande;

        // Insertion dans la base de données
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible d'ajouter la commande.");
                return;
            }

            // SQL pour insérer une commande
            String sql = "INSERT INTO commande (Id, Date, Etat, id_Client) VALUES (?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.idCommande);
            stmt.setDate(2, java.sql.Date.valueOf(this.date)); // Conversion de LocalDate en java.sql.Date
            stmt.setString(3, this.etat.name()); // Sauvegarde de l'état en tant que chaîne
            stmt.setInt(4, this.client.idC); // Lier la commande au client

            stmt.executeUpdate();

            System.out.println("Commande insérée avec succès dans la table !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la commande : " + e.getMessage());
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
     * Synchronise `nbCommande` avec la base de données pour éviter les conflits de clés primaires.
     */
    private void syncNbCommandeWithDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Connexion échouée. Impossible de synchroniser nbCommande.");
                nbCommande = 0; // Défaut si la connexion échoue.
                return;
            }

            String sql = "SELECT MAX(Id) AS maxId FROM commande";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nbCommande = rs.getInt("maxId");
            } else {
                nbCommande = 0; // Défaut si la table est vide.
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la synchronisation de nbCommande : " + e.getMessage());
            e.printStackTrace();
            nbCommande = 0; // Défaut en cas d'erreur.
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

    public Commande() {
    }

    

    public void ajouteLigneCommande(LigneCommande ligneCommande) {
        listeLigneCmd.add(ligneCommande);
    }

    public LigneCommande rechercherLigneCommande(Produit art){
        // la fonction recherche une LigneCommande dans le tableau des LigneCommande par rapport à l'article passer en parametre
        for( int i=0; i<listeLigneCmd.size();i++){
            if( listeLigneCmd.get(i).produit == art ){
                return listeLigneCmd.get(i);
            }
        }
        return null;
    }

    public void modifierQuantiteLigneCommande(Produit art, int quantity ){
        // la fonction recherche la ListeCommande en fonction de l'article
        // puis elle modifie sa quantité
        rechercherLigneCommande(art).qte = quantity;
    }

    public void supprimerLigneCommande(Produit art){
        // La fonction parcours toute la listeLigneCmd pour supprimer
        // la LigneCommande correspondante a l'article passé en parametre
        Vector<LigneCommande> newVect = new Vector<LigneCommande>();
        for( int i=0; i<listeLigneCmd.size(); i++){
            if( listeLigneCmd.get(i).produit != art ){
                newVect.add( listeLigneCmd.get(i) );
            }
        }
        listeLigneCmd = newVect;
    }

    public float getPrice(){
        // fonction qui somme le prix de toutes les LigneCommande pour avoir le prix total
        float somme = 0;
        for (int i = 0; i<listeLigneCmd.size(); i++) {
            somme = somme+listeLigneCmd.get(i).getPrice();
        }
        return somme;
    }

    public void validerCommande() throws SQLException {
        if (listeLigneCmd.isEmpty()) {
            throw new IllegalStateException("Le panier est vide. Ajoutez des produits avant de valider.");
        }
        this.etat = Etat.VALIDEE;
        genererFacture();
    }

    private void genererFacture() throws SQLException {
        Facture facture = new Facture(this);
        // Enregistrez la facture en base ou affichez-la.
    }

}