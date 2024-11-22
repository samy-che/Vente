package Model;
import java.util.*;

public class Commande {

    public Commande() {
    }

    private int Id;

    public Enum Etat;

    private Date Date;

    public Client client;

    public Vector<LigneCommande> listeLigneCmd= new Vector<>() ;

    public Responsable vendeur;

    public Facture Facture;

    public void Total() {
        // TODO implement here
    }

}