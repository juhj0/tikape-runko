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
public class Viesti {
    
    private Integer id;
    private String viestiteksti;
    private String aika;
    private Keskustelu keskustelu;

    public Viesti(Integer id, String viestiteksti, String aika) {
        this.id = id;
        this.viestiteksti = viestiteksti;
        this.aika = aika;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getViestiteksti() {
        return viestiteksti;
    }

    public void setViestiteksti(String viestiteksti) {
        this.viestiteksti = viestiteksti;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }
    
}
