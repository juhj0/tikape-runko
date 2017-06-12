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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Keskustelu;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;
    private Dao<Alue, Integer> alueDao;

    public KeskusteluDao(Database database, Dao<Alue, Integer> alueDao) {
        this.database = database;
        this.alueDao = alueDao;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String avaus = rs.getString("avaus");
        
        Keskustelu keskustelu = new Keskustelu(id, otsikko, avaus);

        Integer alueId = rs.getInt("alue");
        
        rs.close();
        stmt.close();
        connection.close();

        keskustelu.setAlue(this.alueDao.findOne(alueId));
        
        return keskustelu;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {

            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String avaus = rs.getString("avaus");

            Keskustelu keskustelu = new Keskustelu(id, otsikko, avaus);

            Integer alueId = rs.getInt("alue");
            keskustelu.setAlue(this.alueDao.findOne(alueId));
            
            keskustelut.add(keskustelu);
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    public List<Keskustelu> findAllIn(Integer key) throws SQLException {
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE Keskustelu.alue IN (" + muuttujat + ")");
        int laskuri = 1;
//        for (Integer key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
//        }

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String avaus = rs.getString("avaus");

            Keskustelu keskustelu = new Keskustelu(id, otsikko, avaus);

            Integer alueId = rs.getInt("alue");
            keskustelu.setAlue(this.alueDao.findOne(alueId));
            
            keskustelut.add(keskustelu);
        }
        
        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
}
