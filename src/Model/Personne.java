package Model;

public class Personne {

    public Personne() {
    }

    private String nom;

    private String prenom;

    private String adresse;

    private String tel;

    private String email;

    public Personne(String nom, String prenom, String adresse, String tel, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
    }

}