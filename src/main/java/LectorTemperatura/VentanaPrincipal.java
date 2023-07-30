package LectorTemperatura;

import ConexioDataBase.Logica;
import com.fazecast.jSerialComm.SerialPort;
import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MSI
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private SerialPort serialPort;
    private DefaultTableModel tablaModelo;
    private DefaultTableModel tablaModeloResultados;
    private int idContador = 1;
    Connection conn;
    Statement mystatement;

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DBNAME = "lectorTemperatura";
    private final String USER = "root";
    private final String PASSWORD = "bamm2212";

    public VentanaPrincipal() {

        initComponents();
        connect();
        modificarTipoColumnaTemperatura();
        inicializarTabla();
        inicializarTablaResultados();
        conexionArduino();

        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);

                // Dentro del método initComponents():
                cmbTipoTemperatura = new javax.swing.JComboBox<>();
                cmbTipoTemperatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Todos", "Caliente", "Frío", "Templado"}));
                cmbTipoTemperatura.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        cmbTipoTemperaturaActionPerformed(evt);
                    }
                });

            }
        });

        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

    }

    public JTable getTablaResultados() {
        return TablaResultados;
    }

    public void connect() {
        try {
            if (conn != null && conn.isValid(0)) {
                System.out.println("Conexion ya establecida!");

            } else {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME,
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

    //Conexion del Arduino con java
    public void conexionArduino() {

        serialPort = SerialPort.getCommPort("COM6");

        if (serialPort.openPort()) {
            System.out.println("Puerto serial conectado correctamente.");

            StringBuilder datosrecibidos = new StringBuilder();

            //Muestra el dato de temperatura en el txt
            Timer timer = new Timer(1000, e -> {
                byte[] newData = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(newData, newData.length);
                String data = new String(newData);

                if (data.contains("\n")) {
                    // Eliminar el caracter de nueva línea ("\n") del final de los datos recibidos
                    data = data.trim();

                    try {
                        double temperatura = Double.parseDouble(datosrecibidos.toString() + data);
                        String mensaje = String.format("%.2f", temperatura);

                        // Mostrar los datos en el TextArea
                        SwingUtilities.invokeLater(() -> txtTemperatura.setText(mensaje));
                    } catch (NumberFormatException ex) {
                        // Manejar el caso en que no se pueda convertir a double
                        System.out.println("Error al convertir a double: " + ex.getMessage());
                    }

                    // Limpiar el StringBuilder para los siguientes datos
                    datosrecibidos.setLength(0);
                } else {
                    // Acumular los datos recibidos hasta recibir el valor completo
                    datosrecibidos.append(data);
                    System.out.println("dato acumulado " + datosrecibidos);
                }
            });
            // Iniciar el temporizador
            timer.start();
        } else {
            System.out.println("Error al conectar al puerto serial.");
            JOptionPane.showMessageDialog(null, "Error al conectar con Arduino.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        txtTemperatura = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaResultados = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        btnLimpiarTabla = new javax.swing.JButton();
        cmbTipoTemperatura = new javax.swing.JComboBox<>();
        btnOrdenarTemperatura = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnListar.setText("LISTAR");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "DATO TEMPERATURA", "FECHA Y HORA", "TIPO DE TEMPERATURA"
            }
        ));
        jScrollPane2.setViewportView(TablaDatos);

        txtTemperatura.setFont(new java.awt.Font("Old English Text MT", 3, 24)); // NOI18N
        txtTemperatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTemperaturaActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel1.setText("BUSCAR DATO DE TEMPERATURA");

        jLabel2.setText("INGRESE ID:");

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        TablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "DATO TEMPERATURA", "FECHA Y HORA", "TIPO DE TEMPERATURA"
            }
        ));
        jScrollPane4.setViewportView(TablaResultados);

        btnModificar.setText("MODIFICAR POR FECHA Y HORA");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnOrdenar.setText("ORDENAR POR FECHA Y HORA");
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        btnLimpiarTabla.setText("LIMPIAR TABLA");
        btnLimpiarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTablaActionPerformed(evt);
            }
        });

        cmbTipoTemperatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Caliente", "Frio", "Templado" }));
        cmbTipoTemperatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoTemperaturaActionPerformed(evt);
            }
        });

        btnOrdenarTemperatura.setText("ORDENAR POR TEMPERATURA");
        btnOrdenarTemperatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarTemperaturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTemperatura))
                .addGap(82, 82, 82)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOrdenar)
                            .addComponent(cmbTipoTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(btnOrdenarTemperatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cmbTipoTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(88, 88, 88)
                                        .addComponent(btnOrdenarTemperatura)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar)
                            .addComponent(btnModificar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLimpiarTabla)
                        .addGap(59, 59, 59)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTemperaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTemperaturaActionPerformed

    }//GEN-LAST:event_txtTemperaturaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        String temperaturaTexto = txtTemperatura.getText();

        // Verificar si hay una temperatura válida en el TextArea
        if (!temperaturaTexto.isEmpty()) {
            try {
                // Reemplazar la coma por punto en la temperatura
                temperaturaTexto = temperaturaTexto.replace(",", ".");

                // Obtener la fecha y hora actual
                String fechaHoraActual = obtenerFechaHoraActual();

                // Obtener el valor máximo actual del id en la tabla
                String queryMaxId = "SELECT MAX(id) FROM TablaTemperaturas";
                int maxId = 0;
                try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(queryMaxId)) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    System.out.println("Error al obtener el valor máximo de id: " + e.getMessage());
                }

                // Incrementar el contador de id para la próxima inserción
                idContador = maxId + 1;

                // Determinar el tipo de temperatura
                String tipoTemperatura;
                double temperatura = Double.parseDouble(temperaturaTexto);
                if (temperatura > 30) {
                    tipoTemperatura = "Caliente";
                } else if (temperatura < 10) {
                    tipoTemperatura = "Frío";
                } else {
                    tipoTemperatura = "Templado";
                }

                // Insertar los datos en la base de datos
                insertarDatosEnBaseDeDatos(temperaturaTexto, fechaHoraActual, tipoTemperatura);

                // Agregar la temperatura al modelo de la tabla
                tablaModelo.addRow(new Object[]{idContador, temperaturaTexto, fechaHoraActual, tipoTemperatura});

                idContador++; // Incrementar el contador de id para la próxima inserción

                JOptionPane.showMessageDialog(null, "Dato de Temperatura guardado exitosamente");
                System.out.println("Temperatura guardada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al Guardar Temperatura");
                System.out.println("Error al guardar la temperatura en la base de datos: " + e.getMessage());
                // También puedes mostrar un cuadro de diálogo de error aquí si lo deseas
            }
        } else {
            System.out.println("No hay datos de temperatura para guardar.");
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        listarDatosDesdeBaseDeDatos();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = TablaDatos.getSelectedRow();

        if (filaSeleccionada != -1) { // Se ha seleccionado una fila en la tabla
            int idAEliminar = (int) TablaDatos.getValueAt(filaSeleccionada, 0); // Obtener el id de la fila seleccionada

            try {
                eliminarRegistroEnBaseDeDatos(idAEliminar); // Eliminar el registro de la base de datos
                DefaultTableModel modelo = (DefaultTableModel) TablaDatos.getModel();
                modelo.removeRow(filaSeleccionada); // Eliminar la fila de la tabla
                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                System.out.println("Registro eliminado correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar registro");
                System.out.println("Error al eliminar el registro de la base de datos: " + e.getMessage());
                // También puedes mostrar un cuadro de diálogo de error aquí si lo deseas
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar");
            System.out.println("No se ha seleccionado ninguna fila para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String idTexto = txtBuscar.getText();

        if (!idTexto.isEmpty()) {
            try {
                int idBusqueda = Integer.parseInt(idTexto);
                buscarPorId(idBusqueda);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido (número entero).");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar.");
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = TablaResultados.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idModificar = (int) TablaResultados.getValueAt(filaSeleccionada, 0);

            // Obtener la nueva fecha y hora
            String nuevaFechaHora = obtenerFechaHoraActual();

            // Llamar al método para actualizar la fecha y hora en la base de datos
            try {
                actualizarFechaHoraEnBaseDeDatos(idModificar, nuevaFechaHora);
                // Actualizar la fecha y hora en la tabla de resultados
                DefaultTableModel modeloResultados = (DefaultTableModel) TablaResultados.getModel();
                modeloResultados.setValueAt(nuevaFechaHora, filaSeleccionada, 2);
                JOptionPane.showMessageDialog(null, "Fecha y hora actualizadas correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar la fecha y hora en la base de datos.");
                System.out.println("Error al actualizar la fecha y hora en la base de datos: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un dato para modificar la fecha y hora.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        listarDatosDesdeBaseDeDatosOrdenados();
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnLimpiarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTablaActionPerformed
        eliminarTodosLosDatosEnBaseDeDatos();
        // Después de eliminar los datos, puedes actualizar la tabla para reflejar el cambio
        listarDatosDesdeBaseDeDatosOrdenados();
    }//GEN-LAST:event_btnLimpiarTablaActionPerformed

    private void cmbTipoTemperaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoTemperaturaActionPerformed
        String tipoTemperaturaSeleccionado = cmbTipoTemperatura.getSelectedItem().toString();
        buscarPorTipoTemperatura(tipoTemperaturaSeleccionado);
    }//GEN-LAST:event_cmbTipoTemperaturaActionPerformed

    private void btnOrdenarTemperaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarTemperaturaActionPerformed
        boolean ordenAscendente = !btnOrdenar.getText().contains("Ascendente");
        listarDatosDesdeBaseDeDatosOrdenadosPorTemperatura(ordenAscendente);
    }//GEN-LAST:event_btnOrdenarTemperaturaActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    private void buscarPorId(int id) {
        String query = "SELECT id, temperatura, fecha_hora, "
                + "CASE "
                + "    WHEN temperatura > 30 THEN 'Caliente' "
                + "    WHEN temperatura < 10 THEN 'Frío' "
                + "    ELSE 'Templado' "
                + "END AS tipo_temperatura "
                + "FROM TablaTemperaturas "
                + "WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Limpiar los datos actuales de TablaResultados
            DefaultTableModel modeloResultados = (DefaultTableModel) TablaResultados.getModel();
            modeloResultados.setRowCount(0);

            // Recorrer los resultados de la consulta y agregarlos a TablaResultados
            while (resultSet.next()) {
                int idResultado = resultSet.getInt("id");
                double temperatura = resultSet.getDouble("temperatura");
                String fechaHora = resultSet.getString("fecha_hora");
                String tipoTemperatura = resultSet.getString("tipo_temperatura");

                modeloResultados.addRow(new Object[]{idResultado, temperatura, fechaHora, tipoTemperatura});
            }

            if (modeloResultados.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No se encontraron registros con el ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el dato por ID: " + e.getMessage());
        }
    }

    private void actualizarFechaHoraEnBaseDeDatos(int idModificar, String nuevaFechaHora) throws SQLException {
        String query = "UPDATE TablaTemperaturas SET fecha_hora = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, nuevaFechaHora);
            preparedStatement.setInt(2, idModificar);
            preparedStatement.executeUpdate();
        }
    }

    private void listarDatosDesdeBaseDeDatos() {
        String query = "SELECT id, temperatura, fecha_hora, "
                + "CASE "
                + "    WHEN temperatura > 30 THEN 'Caliente' "
                + "    WHEN temperatura < 10 THEN 'Frío' "
                + "    ELSE 'Templado' "
                + "END AS tipo_temperatura "
                + "FROM TablaTemperaturas";

        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            // Limpiar los datos actuales de la tabla
            DefaultTableModel modelo = (DefaultTableModel) TablaDatos.getModel();
            modelo.setRowCount(0);

            // Recorrer los resultados de la consulta y agregarlos a la tabla
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double temperatura = resultSet.getDouble("temperatura");
                String fechaHora = resultSet.getString("fecha_hora");
                String tipoTemperatura = resultSet.getString("tipo_temperatura");

                modelo.addRow(new Object[]{id, temperatura, fechaHora, tipoTemperatura});
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los datos desde la base de datos: " + e.getMessage());
        }
    }

    private void listarDatosDesdeBaseDeDatosOrdenadosPorTemperatura(boolean ordenAscendente) {
        String query = "SELECT id, temperatura, fecha_hora, tipo_temperatura "
                + "FROM TablaTemperaturas "
                + "ORDER BY temperatura " + (ordenAscendente ? "ASC" : "DESC");

        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            // Limpiar los datos actuales de la tabla
            DefaultTableModel modelo = (DefaultTableModel) TablaResultados.getModel();
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

    private void modificarTipoColumnaTemperatura() {

        String query = "ALTER TABLE TablaTemperaturas MODIFY COLUMN temperatura DOUBLE";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Tipo de columna 'temperatura' modificado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al modificar el tipo de columna 'temperatura': " + e.getMessage());
        }
    }

    private void insertarDatosEnBaseDeDatos(String temperatura, String fechaHora, String tipoTemperatura) throws SQLException {
        connect();
        String query = "INSERT INTO TablaTemperaturas (id, temperatura, fecha_hora, tipo_temperatura) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idContador);
            double temperaturaDouble = Double.parseDouble(temperatura); // Convertir a double
            preparedStatement.setDouble(2, temperaturaDouble); // Usar setDouble para la temperatura
            preparedStatement.setString(3, fechaHora);
            preparedStatement.setString(4, tipoTemperatura); // Agregar el tipo de temperatura
            preparedStatement.executeUpdate();
        }
    }

    private void listarDatosDesdeBaseDeDatosOrdenados() {
        String query = "SELECT id, temperatura, fecha_hora, "
                + "CASE "
                + "    WHEN temperatura > 30 THEN 'Caliente' "
                + "    WHEN temperatura < 10 THEN 'Frío' "
                + "    ELSE 'Templado' "
                + "END AS tipo_temperatura "
                + "FROM TablaTemperaturas "
                + "ORDER BY fecha_hora"; // Ordenar por la columna fecha_hora

        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            // Limpiar los datos actuales de la tabla
            DefaultTableModel modelo = (DefaultTableModel) TablaResultados.getModel();
            modelo.setRowCount(0);

            // Recorrer los resultados de la consulta y agregarlos a la tabla
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double temperatura = resultSet.getDouble("temperatura");
                String fechaHora = resultSet.getString("fecha_hora");
                String tipoTemperatura = resultSet.getString("tipo_temperatura");

                modelo.addRow(new Object[]{id, temperatura, fechaHora, tipoTemperatura});
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los datos desde la base de datos: " + e.getMessage());
        }
    }

    // Método para eliminar un registro de la base de datos según su id
    private void eliminarRegistroEnBaseDeDatos(int idAEliminar) throws SQLException {
        String query = "DELETE FROM TablaTemperaturas WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idAEliminar);
            preparedStatement.executeUpdate();
        }
    }

    private void buscarPorTipoTemperatura(String tipoTemperatura) {
        String query;
        if (tipoTemperatura.equals("Todos")) {
            // Si se selecciona "Todos", mostrar todos los datos
            query = "SELECT id, temperatura, fecha_hora, tipo_temperatura FROM TablaTemperaturas";
        } else {
            // Mostrar solo los datos del tipo de temperatura seleccionado
            query = "SELECT id, temperatura, fecha_hora, tipo_temperatura FROM TablaTemperaturas WHERE tipo_temperatura = '" + tipoTemperatura + "'";
        }

        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            // Limpiar los datos actuales de la tabla
            DefaultTableModel modelo = (DefaultTableModel) TablaResultados.getModel();
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
            System.out.println("Error al buscar datos por tipo de temperatura: " + e.getMessage());
        }
    }

    private void eliminarTodosLosDatosEnBaseDeDatos() {
        String query = "DELETE FROM TablaTemperaturas";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Todos los datos eliminados correctamente.");
            JOptionPane.showMessageDialog(null, "La tabla ha sido limpiada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar todos los datos de la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla");
        }
    }

    //Metodo para inicializar la tabla
    private void inicializarTabla() {
        tablaModelo = new DefaultTableModel();
        tablaModelo.addColumn("ID");
        tablaModelo.addColumn("Temperatura");
        tablaModelo.addColumn("Fecha y Hora");
        tablaModelo.addColumn("Tipo de Temperatura"); // Agregamos la columna "Tipo de Temperatura"
        TablaDatos.setModel(tablaModelo);

    }

    private void inicializarTablaResultados() {
        tablaModeloResultados = new DefaultTableModel();
        tablaModeloResultados.addColumn("ID");
        tablaModeloResultados.addColumn("Temperatura");
        tablaModeloResultados.addColumn("Fecha y Hora");
        tablaModeloResultados.addColumn("Tipo de Temperatura");
        TablaResultados.setModel(tablaModeloResultados);
    }

    //Metodo para obtener la fecha y hora actual
    private String obtenerFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaDatos;
    private javax.swing.JTable TablaResultados;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiarTabla;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnOrdenarTemperatura;
    private javax.swing.JComboBox<String> cmbTipoTemperatura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtTemperatura;
    // End of variables declaration//GEN-END:variables
}
