/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

/**
 *
 * @author miarvirt
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;
    private Dao<Keskustelu, Integer> keskusteluDao;

    public ViestiDao(Database database, Dao<Keskustelu, Integer> keskusteluDao) {
        this.database = database;
        this.keskusteluDao = keskusteluDao;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String viestiteksti = rs.getString("viestiteksti");
        String aika = rs.getString("aika");
        
        Viesti viesti = new Viesti(id, viestiteksti, aika);

        Integer keskusteluId = rs.getInt("keskustelu");
        
        rs.close();
        stmt.close();
        connection.close();

        viesti.setKeskustelu(this.keskusteluDao.findOne(keskusteluId));
        
        return viesti;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            
            Integer id = rs.getInt("id");
            String viestiteksti = rs.getString("viestiteksti");
            String aika = rs.getString("aika");

            Viesti viesti = new Viesti(id, viestiteksti, aika);

            Integer keskusteluId = rs.getInt("keskustelu");

            viesti.setKeskustelu(this.keskusteluDao.findOne(keskusteluId));

            viestit.add(viesti);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public List<Viesti> findAllIn(Integer key) throws SQLException {
//        if (keys.isEmpty()) {
//            return new ArrayList<>();
//        }

        // Luodaan IN-kyselyä varten paikat, joihin arvot asetetaan --
        // toistaiseksi IN-parametrille ei voi antaa suoraan kokoelmaa
        StringBuilder muuttujat = new StringBuilder("?");
//        for (int i = 1; i < keys.size(); i++) {
//            muuttujat.append(", ?");
//        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.keskustelu IN (" + muuttujat + ")");
        int laskuri = 1;
//        for (Integer key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
//        }

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            
            Integer id = rs.getInt("id");
            String viestiteksti = rs.getString("viestiteksti");
            String aika = rs.getString("aika");

            Viesti viesti = new Viesti(id, viestiteksti, aika);

            Integer keskusteluId = rs.getInt("keskustelu");

            viesti.setKeskustelu(this.keskusteluDao.findOne(keskusteluId));

            viestit.add(viesti);
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public void add(String viestiteksti, String keskusteluId) throws SQLException {

        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (viestiteksti, keskustelu) VALUES (?, ?);");

        stmt.setObject(1, viestiteksti);
        stmt.setObject(2, keskusteluId);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
        
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
}