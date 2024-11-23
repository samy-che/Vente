package Model;
import java.util.*;

public class Client extends Personne {

    public Client() {
    }

    public Vector<Commande> listeCmd = new Vector<Commande>();

    public static int nbClient = 0;

    public int idC;

    public Client(String nom, String prenom, String adresse, String tel, String email) {
        super(nom, prenom, adresse, tel, email);
        idC = ++nbClient;
    }

    public void ajouteCommande(Commande commande){
        listeCmd.add(commande);
    }




}