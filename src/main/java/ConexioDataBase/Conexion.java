package ConexioDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSI
 */
public class Conexion {

    Statement mistatement;
    Connection con;

    public void conectar() {
        try {
            if (con != null && con.isValid(0)) {
                System.out.println("Ya existe una conexion");
            } else {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lectorTemperatura", "root", "bamm2212");
                mistatement = con.createStatement();
                System.out.println("Conexion Exitosa!!\n");
            }
        } catch (SQLException ex) {

        }
    }

    public ResultSet listar(String temperatura) {
        conectar();
        try {
            ResultSet listado = mistatement.executeQuery("INSERT TO temperatura ()");
            return listado;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
