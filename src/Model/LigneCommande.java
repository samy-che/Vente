package Model;

public class LigneCommande {

    public LigneCommande() {
    }

    private int qte;

    public Commande commande;

    public Produit produit;

    public LigneCommande(int qte, Produit produit, Commande commande) {
        this.qte = qte;
        this.produit = produit;
        this.commande = commande;
    }

    public float getPrice() {
        return qte* produit.prix;
    }
}