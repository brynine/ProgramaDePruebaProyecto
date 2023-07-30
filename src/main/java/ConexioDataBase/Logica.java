package ConexioDataBase;

import LectorTemperatura.VentanaPrincipal;
import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MSI
 */
public class Logica {
    
    VentanaPrincipal ventana;
    
    Statement mystatement;
    java.sql.Connection conn;
    
    private final String HOST="localhost";
    private final String PORT="3306";
    private final String DBNAME="lectorTemperatura";
    private final String USER="root";
    private final String PASSWORD="bamm2212";
    
    public void connect(){
        try {
            if (conn!=null && conn.isValid(0)) {
                System.out.println("Conexion ya establecida!");
             
            }else{
               conn = DriverManager.getConnection(
                "jdbc:mysql://"+HOST+":"+PORT+"/"+DBNAME,
                USER,
                PASSWORD
                );
                
                mystatement = conn.createStatement();
                
                System.out.println("Conexion Exitosa!"); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void listarDatosDesdeBaseDeDatosOrdenadosPorTemperatura(boolean ordenAscendente) {
        String query = "SELECT id, temperatura, fecha_hora, tipo_temperatura " +
                       "FROM TablaTemperaturas " +
                       "ORDER BY temperatura " + (ordenAscendente ? "ASC" : "DESC");

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Limpiar los datos actuales de la tabla
            DefaultTableModel modelo = (DefaultTableModel) ventana.getTablaResultados().getModel();
            modelo.setRowCount(0);

            // Recorrer los resultados de la consulta y agregarlos a la tabla
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double temperatura = resultSet.getDouble("temperatura");
                String fechaHora = resultSet.getString("fecha_hora");
                String tipoTemp = resultSet.getString("tipo_temperatura");

                modelo.addRow(new Object[]{id, temperatura, fechaHora, tipoTemp});
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los datos ordenados por temperatura: " + e.getMessage());
        }
    }
    
}
