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

public class CE implements Comparable {
    String cliente;
    int vlan;
    String ciudad;

    public CE() {
    }

    public CE(String cliente, int vlan, String ciudad) {
        this.cliente = cliente;
        this.vlan = vlan;
        this.ciudad = ciudad;
    }
    
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
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
        return "CE-"+city+"-" + cliente ;
    }

    @Override
    public int compareTo(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
