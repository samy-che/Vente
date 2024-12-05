import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import Model.Catalogue;
import Model.Client;
import Model.Commande;
import Model.Facture;
import Model.Personne;
import Model.Produit;
import Model.Responsable;
import view.Menu;


public class Main {


    public static void main(String[] args) throws SQLException {
    	//Produit produit1 = new Produit("Apple iPhone 14 ", 500.0f, "Apple iPhone 14 32GB - Black", 10, "https://boulanger.scene7.com/is/image/Boulanger/0194253408253_h_f_l_0?wid=720&hei=720&resMode=sharp2&op_usm=1.75,0.3,2,0");
    	//Produit produit2 = new Produit("Galaxy s24", 250.0f, "AGM H5 Pro - Robust Smartphone 6.52\" Screen", 8, "https://boulanger.scene7.com/is/image/Boulanger/8806095308234_h_f_l_0?wid=720&hei=720&resMode=sharp2&op_usm=1.75,0.3,2,0");
    	
        Catalogue catalogue = new Catalogue();
        Menu menu = new Menu();

        //Client c1 = new Client("denis", "dupont", "champs élysé", "0123455678", "admin","admin");
        //Client c1 = new Client("toto", "a", "rue k", "09000000", "azer@gmail.com","admin");
//        Responsable r1 = new Responsable("nom3", "pre1", "rue k", "09000000", "jsdn@jdk.com","jjsjsik","jkndnlnln");
//        System.out.println(c1.idC);
//        System.out.println(r1.idR);
//        Produit p1 = new Produit("nom3", 19.9f, "rklk,zlk",7);
//        System.out.println(p1.idP);
//        Commande com1 = new Commande(LocalDate.now(),c1);
//        System.out.println(com1.idCommande);
//        Facture f1 = new Facture(com1);
//        System.out.println(f1.id);
    }
    
}