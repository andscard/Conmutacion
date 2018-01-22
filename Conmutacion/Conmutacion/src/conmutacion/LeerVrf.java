/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conmutacion;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;



public class LeerVrf {

    private boolean band=true;
    /**
     * @return  LinkedList<Vrf> retornamos una lista de Vrf creadaso
     */
    public LinkedList<CE> cargarVrf() {
        FileReader fr;
        BufferedReader br = null;
        String linea;
        String valores[];
        CE c1;
        LinkedList<CE> vrf=new LinkedList<>();
        
        try {
            fr = new FileReader("vrf.txt");
            br = new BufferedReader(fr);
            //abrimos el archivo y recorremos linea por linea
            while ((linea = br.readLine()) != null) {
            
                   valores = linea.split(",", 5);
                    c1 = new CE();
                    //ingresamos todos los valores a un objeto Cuenta c1
                    c1.ciudad=valores[0];
                    c1.cliente=valores[2];
                    c1.vlan=Integer.parseInt(valores[1]);
                    vrf.add(c1);
            }
         //manegamos excepciones 
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no existe");
        } catch (IOException ex) {
            System.out.println("Error de Lectura");
        }
        return vrf;
    }
    public void guardarVrf(LinkedList<CE> lista,CE vrf,String tipo) {
        FileWriter fw;
        BufferedWriter bw;
        PrintWriter pw;
        try {
            fw = new FileWriter("vrf.txt");
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            int acum=0;
            for (CE obj : lista) {
                if(tipo=="crear"){
                    if(acum==0){
                       pw.write(obj.ciudad+",");
                       pw.write(obj.vlan+",");
                       pw.write(obj.cliente);
                       pw.write("\n");
                        pw.write(vrf.ciudad+",");
                        pw.write(vrf.vlan+",");
                        pw.write(vrf.cliente);
                        pw.write("\n");
                        acum=acum+1;
                    }else{
                       pw.write(obj.ciudad+",");
                       pw.write(obj.vlan+",");
                       pw.write(obj.cliente);
                       pw.write("\n");
                    }
                }else{
                    if(obj!=vrf){
                       pw.write(obj.ciudad+",");
                       pw.write(obj.vlan+",");
                       pw.write(obj.cliente);
                       pw.write("\n");
                 }
                }
                
            }
            pw.close();
            bw.close();
            System.out.println(vrf);
            
        } catch (IOException ex) {
            System.out.println("ERROR: No se puedo acceder al archivo");
        }
    }
}
