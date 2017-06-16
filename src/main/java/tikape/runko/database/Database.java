package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        
        /*

        */
        lista.add("PRAGMA foreign_keys = ON;");

        /*
            viimeisin_viesti_aika DATETIME DEFAULT CURRENT_TIMESTAMP tallennetaan
            universal time codena. Kyselyjä tehdessä ajan saa Suomen ajaksi
            muokkaamalla kyselyä muotoon: DATETIME(viimeisin_viesti_aika, 'localtime')
        
            viestien_maara integer, viimeisin_viesti_aika DATETIME DEFAULT CURRENT_TIMESTAMP
        */
        
        /*
            Alue
        */
        
        lista.add("CREATE TABLE Alue(id integer PRIMARY KEY, nimi varchar(255) NOT NULL UNIQUE);");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Martti Luther');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('John Wycliffe');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Ulrich Zwingli');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Katolinen kirkko uskonpuhdistuksessa');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Suuren Pingviinin kirkko');");
        
        /*
            Keskustelu
        */
        
        lista.add("CREATE TABLE Keskustelu(id integer PRIMARY KEY, otsikko varchar(255) NOT NULL, avaus varchar(6000) NOT NULL, alue integer, FOREIGN KEY(alue) REFERENCES Alue(id));");
        
        lista.add("INSERT INTO Keskustelu (otsikko, avaus, alue) VALUES ('Martti Lutherin henkilöhistoria', 'Kertokaa anekdootteja Martti Lutherin henkilöhistoriasta', 1)");
        lista.add("INSERT INTO Keskustelu (otsikko, avaus, alue) VALUES ('Martti Lutherin usko', 'Millainen oli Martti Lutherin käsitys uskosta?', 1)");
        
        lista.add("INSERT INTO Keskustelu (otsikko, avaus, alue) VALUES ('John Wycliffen henkilöhistoria', 'Kertokaa anekdootteja John Wycliffen henkilöhistoriasta', 2)");
        lista.add("INSERT INTO Keskustelu (otsikko, avaus, alue) VALUES ('John Wycliffen usko', 'Millainen oli John Wycliffen käsitys uskosta?', 2)");
        
        /*
            Viesti
        */
        
        lista.add("CREATE TABLE Viesti(id integer PRIMARY KEY, viestiteksti varchar(6000) NOT NULL, aika DATETIME DEFAULT CURRENT_TIMESTAMP, keskustelu integer, FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");
        lista.add("INSERT INTO Viesti (viestiteksti, keskustelu) VALUES ('Luther vaikutti myös teologian ulkopuolella. Hänen raamatunkäännöksensä ja muu kirjoitustyönsä kehittivät saksan kirjakieltä, ja hän oli tunnettu myös aktiivisena yhteiskunnallisena vaikuttajana.', 1);");
        lista.add("INSERT INTO Viesti (viestiteksti, keskustelu) VALUES ('Luther oli myös hyvin musikaalinen; hän sepitti itse useita sävelmiä, joista tunnetuin on protestanttisissa kirkoissa tunnettu koraali Jumala ompi linnamme (Ein feste Burg ist unser Gott).', 1);");
        
        lista.add("INSERT INTO Viesti (viestiteksti, keskustelu) VALUES ('Wycliffe kyseenalaisti paavin vallan kirkossa. Hän vastusti pyhimysten palvontaa ja pappien selibaattia. Hän korosti henkilökohtaista Raamatun lukemista ja Raamatun merkitystä kirkollisena auktoriteettina, mutta on epäselvää, kuinka paljon Raamatun käännöstyötä hän teki itse.', 3);");
        lista.add("INSERT INTO Viesti (viestiteksti, keskustelu) VALUES ('Katolinen kirkko julisti Wycliffen teesit harhaoppisiksi Oxfordissa 1381. Wycliffe ja Jan Hus julistettiin harhaoppisiksi myös Konstanzin kirkolliskokouksessa 1415, ja Wycliffen luut kaivettiin esiin ja poltettiin roviolla.', 3);");
        
        
        return lista;
    }
}
