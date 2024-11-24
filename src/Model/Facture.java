package Model;
import java.util.*;

public class Facture {

    public Facture() {
    }

    public static int cptFacture = 0;

    private int id;

    private float total;

    private Date date;

    public Commande commande;

    public Facture(Commande commande) {
        this.id = ++cptFacture;
        this.commande = commande;
        this.date = new Date();
        this.total = commande.getPrice();
    }

    public void AfficherFacture() {
        // TODO implement here
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

}