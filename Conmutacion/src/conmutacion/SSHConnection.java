package conmutacion;
 
import com.jcraft.jsch.JSchException;
import java.io.IOException;

public class SSHConnection {
 
    private static final String USERNAME = "cisco";
    private static final String HOST = "192.168.1.10";
    private static final int PORT = 22;
    private static final String PASSWORD = "cisco";
 
    public static void main(String[] args) {
 
        try {
            SSHConnector sshConnector = new SSHConnector();
            sshConnector.connect(USERNAME, PASSWORD, HOST, PORT);
            String result = sshConnector.executeCommand("show clock");
 
            
            sshConnector.disconnect();
             
            System.out.println(result);
          
        } catch (JSchException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        }
    }
}
