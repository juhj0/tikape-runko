package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelu;

public class Main {

    public static void main(String[] args) throws Exception {
//        Database database = new Database("jdbc:sqlite:opiskelijat.db");
//        database.init();
//
//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
//
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());

        /*

        */
        
        Database database = new Database("jdbc:sqlite:wycliffe.db");
        database.init();

        AlueDao alueDao = new AlueDao(database);
        
        KeskusteluDao keskusteluDao = new KeskusteluDao(database, alueDao);
        
        ViestiDao viestiDao = new ViestiDao(database, keskusteluDao);

        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());
        
        get("/keskustelut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", keskusteluDao.findAll());

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());
        
        get("alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteluDao.findAllIn(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        get("keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            Keskustelu keskustelu = keskusteluDao.findOne(Integer.parseInt(req.params("id")));
            
            map.put("keskustelu", keskustelu);
            map.put("viestit", viestiDao.findAllIn(Integer.parseInt(req.params("id"))));
            map.put("alue", alueDao.findOne(keskustelu.getAlue().getId()));
            
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
    }
}
