package Model;

public class Personne {

    public Personne() {
    }

    private String Nom;

    private String Prenom;

    private String Adresse;

    private String tel;

    public Personne(String nom, String prenom, String adresse, String tel) {
        this.Nom = nom;
        this.Prenom = prenom;
        this.Adresse = adresse;
        this.tel = tel;
    }

}