package conmutacion;
 
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
 
/**
 * Clase encargada de establecer conexión y ejecutar comandos SSH.
 */
public class SSHConnector {
 
    /**
     * Constante que representa un enter.
     */
    private static final String ENTER_KEY = "n";
    /**
     * Sesión SSH establecida.
     */
    private Session session;
 
    /**
     * Establece una conexión SSH.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @param host     Host a conectar.
     * @param port     Puerto del Host.
     *
     * @throws JSchException          Cualquier error al establecer
     *                                conexión SSH.
     * @throws IllegalAccessException Indica que ya existe una conexión
     *                                SSH establecida.
     */
    public void connect(String username, String password, String host, int port)
        throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();
 
            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password);
 
            // Parametro para no validar key de conexion.
            this.session.setConfig("StrictHostKeyChecking", "no");
 
            this.session.connect();
        } else {
            throw new IllegalAccessException("Sesion SSH ya iniciada.");
        }
    }
 
    /**
     * Ejecuta un comando SSH.
     *
     * @param command Comando SSH a ejecutar.
     *
     * @return
     *
     * @throws IllegalAccessException Excepción lanzada cuando no hay
     *                                conexión establecida.
     * @throws JSchException          Excepción lanzada por algún
     *                                error en la ejecución del comando
     *                                SSH.
     * @throws IOException            Excepción al leer el texto arrojado
     *                                luego de la ejecución del comando
     *                                SSH.
     */
    public final String executeCommand(String ip,int actividad, CE vrf)
        throws IllegalAccessException, JSchException, IOException, InterruptedException {
        
        
        if (this.session != null && this.session.isConnected()) {
 
            // Abrimos un canal SSH. Es como abrir una consola.
            Channel channelExec = (Channel) this.session.openChannel("shell");
            
            OutputStream out= channelExec.getOutputStream();
            PrintStream commander = new PrintStream(out, true);

            channelExec .setOutputStream(System.out, true);

            channelExec.connect();
            //Crear Vrf
            if(actividad==1){
                commander.println("enable");    
                commander.println("cisco");
                commander.println("configure terminal");
                commander.println("ip vrf CE-"+vrf.cliente);
                commander.println("rd 1:"+vrf.vlan);
                commander.println("route-target export 1:"+vrf.vlan);
                commander.println("route-target import 1:"+vrf.vlan);
                commander.println("exit");
                commander.println("interface f0/0."+vrf.vlan);
                commander.println("encapsulation dot1Q "+vrf.vlan);
                commander.println("ip vrf forwarding CE-"+vrf.cliente);
                String ipvrf="10.";
                if(vrf.cliente.equals("NESTLE")){
                    if(vrf.ciudad.equals("GUAYAQUIL")){
                        ipvrf=ipvrf+"10.10.1";
                    }else{
                        ipvrf=ipvrf+"20.10.1";
                    }
                }else{
                    if(vrf.ciudad.equals("GUAYAQUIL")){
                        ipvrf=ipvrf+"10.10.5";
                    }else{
                        ipvrf=ipvrf+"20.10.5";
                    }
                }
                commander.println("ip address "+ipvrf+" 255.255.255.0");
                commander.println("end");
                commander.println("show ip vrf");
        


                commander.println("end");

                //Eliminar Vrf
            }else if (actividad==2){
                commander.println("enable");    
                commander.println("cisco");
                commander.println("configure terminal");
                commander.println("no ip vrf CE-"+vrf.cliente);
                commander.println("end");
                commander.println("show ip vrf");
                    
                //conectividad    
            }else {
             
                commander.println("enable");    
                commander.println("cisco");
                commander.println("ping " +ip);
                        
            }
            commander.close();
        
            
            
//            
//           InputStream in = channelExec.getInputStream();
           InputStream in = channelExec.getInputStream();
           
          
            // Obtenemos el texto impreso en la consola.
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            
            String linea;
            int acum=0;
            System.out.println("asddas");
            
            System.out.println("asdasd");
          
              
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
               acum++;
                if(linea.equals("!")){
                    
                    linea="!!!!!";
                    builder.append(linea);
                    builder.append("\n");
                    builder.append("Hubo Conectividad");
                    break;
                }else if(linea.equals(".")){
                    
                    linea=".....";
                    builder.append(linea);
                    builder.append("\n");
                    builder.append("No hubo conectividad");
                    break;
                }else{
                    builder.append(linea);
                    builder.append("\n");
//                    builder.append(ENTER_KEY);
                }
               if(linea==("CE1-GYE-NESTLE#ping 10.20.10.2")) {
                   this.session.disconnect();
               }
            }
            
            ;
            System.out.println("dasdasd"); 
            // Cerramos el canal SSH.
            channelExec.disconnect();
               
            // Retornamos el texto impreso en la consola.
            return builder.toString();
        } else {
            throw new IllegalAccessException("No existe sesion SSH iniciada.");
        }
    }
 
    /**
     * Cierra la sesión SSH.
     */
    public final void disconnect() {
        this.session.disconnect();
    }

}