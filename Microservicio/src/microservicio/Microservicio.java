/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microservicio;

import java.util.LinkedList;




/**
 *
 * @author RICHARD
 */
public class Microservicio {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
        Conexion con=new Conexion();
        LinkedList<Noticia> lista=con.obtener();
        
        for(Noticia noticia: lista){
                System.out.println(noticia);
         }
    }
        
    
}
