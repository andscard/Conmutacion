/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conmutacion;

/**
 *
 * @author RICHARD
 */
public class PE implements Comparable {
    String ciudad;

    public PE() {
    }

    public PE(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
         String city="";
        if(ciudad.equals("QUITO")){
            city="UIO";
        }else{
            city="GYE";
        }
        return "PE-"+city;
    }

    @Override
    public int compareTo(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
