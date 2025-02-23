import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar {
    private static final String url = "jdbc:mysql://localhost:3306/company"; //Base de datos
    private static final String user = "root"; //Usuario
    private static final String password = "12345678"; //Contrasenia

    //Metodo para conectarse a la base de datos
    public static Connection connect(){
        try {
            //Hacemos la conexion con el driver manager
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    } 
}