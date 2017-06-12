/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author miarvirt
 */
public class Keskustelu {

    private Integer id;
    private String otsikko;
    private String avaus;
    private Alue alue;

    public Keskustelu(Integer id, String otsikko, String avaus) {
        this.id = id;
        this.otsikko = otsikko;
        this.avaus = avaus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getAvaus() {
        return avaus;
    }

    public void setAvaus(String avaus) {
        this.avaus = avaus;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

}