package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

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

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
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
        
        /*
        
        */
        post("/alueet/:id", (req, res) -> {
            
            int alueId = Integer.parseInt(req.params("id"));
            
            keskusteluDao.add(req.queryParams("otsikko"), req.queryParams("avaus"), alueId);
            
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteluDao.findAllIn(alueId));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        /*
        
        */
        
        get("/alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteluDao.findAllIn(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        get("/keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            Keskustelu keskustelu = keskusteluDao.findOne(Integer.parseInt(req.params("id")));
            
            List<Viesti> viestit = viestiDao.findAllIn(Integer.parseInt(req.params("id")));
            
            map.put("keskustelu", keskustelu);
            map.put("viestit", viestit);
            map.put("alue", alueDao.findOne(keskustelu.getAlue().getId()));
            map.put("viestienMaara", viestit.size());
            
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        post("/keskustelut/:id", (req, res) -> {
            String viestiteksti = req.queryParams("viestiteksti");

            viestiDao.add(viestiteksti, req.params("id"));
            
            HashMap map = new HashMap<>();
            
            Keskustelu keskustelu = keskusteluDao.findOne(Integer.parseInt(req.params("id")));
            
            List<Viesti> viestit = viestiDao.findAllIn(Integer.parseInt(req.params("id")));
            
            map.put("keskustelu", keskustelu);
            map.put("viestit", viestit);
            map.put("alue", alueDao.findOne(keskustelu.getAlue().getId()));
            map.put("viestienMaara", viestit.size());
            
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        
        
//        post("/keskustelut/:id", (req, res) -> {
//            String viestiteksti = req.queryParams("viestiteksti");
////            nimet.add(nimi);
//            return "Kerrotaan siitä tiedon lähettäjälle: " + viestiteksti;
//        });
    }
}
