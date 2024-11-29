
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.Catalogue;
import Model.Client;
import Model.Personne;
import view.Menu;


public class Main {


    public static void main(String[] args) throws SQLException {
        Catalogue catalogue = new Catalogue();
        Menu menu = new Menu();
        
        Personne p3 = new Personne("nom3", "pre1", "rue k", "09000000", "jsdn@jdk.com");
        System.out.println(p3.idP);
        Client c3 = new Client(p3);
        System.out.println(c3.idC);
        
    }
    
}