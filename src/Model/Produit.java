package Model;
import java.util.*;

public class Produit {

    public Produit() {
    }

    public int idP;

    public String nom;

    public Float prix;

    public String description;

    public int stockQuantite;

    public boolean EstDispo = false;

    public Produit(int idP,String nom, Float prix, String description, int stockQuantite) {
        this.idP = idP;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.stockQuantite = stockQuantite;
        EstDispo = true;
    }

    public Vector<LigneCommande> listeLigneC = new Vector<LigneCommande>();

    public Catalogue catalogue;
}