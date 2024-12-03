package Model;
import java.util.*;

public class Catalogue {

    public Vector<Produit> ListeProduit = new Vector<>();

    public Catalogue() {}

    public void DisplayCat() {
        for (Produit produit : ListeProduit) {
            System.out.println("ID: " + produit.idP);
            System.out.println("Nom: " + produit.nom);
            System.out.println("Prix: " + produit.prix + "€");
            System.out.println("Description: " + produit.description);
            System.out.println("Stock: " + produit.stockQuantite);
            System.out.println("Disponible: " + (produit.estDispo ? "Oui" : "Non"));
            System.out.println("------------------------");
        }
    }

    public void ajouterProduit(Produit produit) {
        ListeProduit.add(produit);
    }

    public void supprimerProduit(Produit produit) {
        ListeProduit.remove(produit);
    }

    public List<Produit> RechercheProd(String nom) {
        List<Produit> resultats = new ArrayList<>();
        for (Produit produit : ListeProduit) {
            if (produit.nom.toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(produit);
            }
        }
        return resultats;
    }

    public List<Produit> RechLogique(String req) {
        // Divise la requête en termes
        String[] terms = req.toLowerCase().split(" ");
        List<Produit> resultats = new ArrayList<>();

        for (Produit produit : ListeProduit) {
            boolean match = false;
            String produitInfo = (produit.nom + " " + produit.description).toLowerCase();

            if (req.contains(" et ")) {
                // Recherche avec ET logique - tous les termes doivent être présents
                match = true;
                for (String term : terms) {
                    if (!term.equals("et") && !produitInfo.contains(term)) {
                        match = false;
                        break;
                    }
                }
            } else if (req.contains(" ou ")) {
                // Recherche avec OU logique - au moins un terme doit être présent
                for (String term : terms) {
                    if (!term.equals("ou") && produitInfo.contains(term)) {
                        match = true;
                        break;
                    }
                }
            } else {
                // Recherche simple - recherche exacte
                match = produitInfo.contains(req.toLowerCase());
            }

            if (match) {
                resultats.add(produit);
            }
        }

        return resultats;
    }
 }