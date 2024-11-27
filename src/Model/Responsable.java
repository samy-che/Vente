package Model;
import java.sql.SQLException;
import java.util.*;

public class Responsable extends Personne {

    

    public static int nbResponsable = 0;
    public int idP;

    private String Login;
    private String mdp;

    private Vector<Client> ListClient = new Vector<Client>();
    public Vector<Commande> listeCommande = new Vector<Commande>();

    public Responsable(String nom, String prenom, String adresse, String tel,String email) throws SQLException{
        super(nom, prenom, adresse, tel, email);
        idP = ++nbResponsable;
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