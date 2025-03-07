/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import Models.TClientes;
import Models.TEstado;
import Models.THistorialDesarrollo;
import Models.TImagenes;
import Models.TOrdenesReparacion;
import Models.TUsuarios;
import Utils.CalendarTime;
import Dao.ClienteDAO;
import Dao.EstadoDAO;
import Dao.HistorialDesarrolloDAO;
import Dao.ImagenesDAO;
import Dao.OrdenesReparacionesDAO;
import Dao.ReparacionesDAO;
import Dao.UsuarioDAO;
import Utils.GenerarOrdenServicio;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ALEX
 */
public class FrmReparacion extends javax.swing.JFrame {

    private ArrayList<ImageIcon> imagenes = new ArrayList<>();
    private ArrayList<String> rutasImagenes = new ArrayList<>();
    private ArrayList<String> rutasFinalImagen = new ArrayList<>();
    private List<byte[]> imagenesBytes = new ArrayList<>();
    private int indiceActual = 0;
    ClienteDAO client = new ClienteDAO();
    EstadoDAO est = new EstadoDAO();
    UsuarioDAO usr = new UsuarioDAO();
    ReparacionesDAO repair = new ReparacionesDAO();
    OrdenesReparacionesDAO or = new OrdenesReparacionesDAO();
    HistorialDesarrolloDAO hor = new HistorialDesarrolloDAO();
    ImagenesDAO imgVM = new ImagenesDAO();
    Calendar fechaActual = new GregorianCalendar();
    TOrdenesReparacion tor = new TOrdenesReparacion();
    THistorialDesarrollo htor = new THistorialDesarrollo();
    List<TImagenes> timgs = new ArrayList<>();
    CalendarTime cCal = new CalendarTime();
    File archivoSeleccionado, destino;
    private String id;
    private Sistema sistema;
    private DefaultTableModel tableModel;

    public FrmReparacion() {
        initComponents();
        client.llenarComboBox(cmbCliente);
        est.llenarComboBox(cmbEstado);
        usr.llenarComboBox(cmbTecnico);
        txtOrden.setText(repair.generarOrden());
        dateRecepcion.setCalendar(fechaActual);
        btnImprimir.setEnabled(false);
        btnHistorialDesarrollo.setEnabled(false);
        btnMensaje.setEnabled(false);
    }

    public FrmReparacion(DefaultTableModel tableModel) {
        initComponents();
        this.tableModel = tableModel;
        client.llenarComboBox(cmbCliente);
        est.llenarComboBox(cmbEstado);
        usr.llenarComboBox(cmbTecnico);
        txtOrden.setText(repair.generarOrden());
        dateRecepcion.setCalendar(fechaActual);
        btnImprimir.setEnabled(false);
        btnHistorialDesarrollo.setEnabled(false);
        btnMensaje.setEnabled(false);
    }

    public FrmReparacion(ReparacionesDAO repair, TOrdenesReparacion ordenReparacion, DefaultTableModel tableModel, THistorialDesarrollo historialDesarrollo, List<TImagenes> imagenes) {
        tor = ordenReparacion;
        htor = historialDesarrollo;
        this.repair = repair;
        this.tableModel = tableModel;
        initComponents();
        lblTitulo.setText("Estas editando una orden: ");
        client.llenarComboBox(cmbCliente);
        est.llenarComboBox(cmbEstado);
        usr.llenarComboBox(cmbTecnico);

        id = ordenReparacion.getOrdenTrabajo();

        txtOrden.setText(ordenReparacion.getOrdenTrabajo());
        dateRecepcion.setCalendar(cCal.convertirTimestampACalendar(ordenReparacion.getFechaRecepcion()));

        cmbTecnico.setSelectedItem(usr.consultarPorId(ordenReparacion.getIdUsuario()));

        TUsuarios usuarioSeleccionado = usr.consultarPorId(ordenReparacion.getIdUsuario());
        for (int i = 0; i < cmbTecnico.getItemCount(); i++) {
            TUsuarios usuarioItem = (TUsuarios) cmbTecnico.getItemAt(i);
            if (usuarioItem.getIdUsuario() == usuarioSeleccionado.getIdUsuario()) {
                cmbTecnico.setSelectedIndex(i);
                break;
            }
        }

        TClientes clienteSeleccionado = client.consultarPorId(ordenReparacion.getIdCliente());
        for (int i = 0; i < cmbCliente.getItemCount(); i++) {
            TClientes clienteItem = (TClientes) cmbCliente.getItemAt(i);
            if (clienteItem.getId_cliente() == clienteSeleccionado.getId_cliente()) {
                cmbCliente.setSelectedIndex(i);
                break;
            }
        }

        TEstado estadoSeleccionado = est.consultarPorId(ordenReparacion.getIdEstado());
        for (int i = 0; i < cmbEstado.getItemCount(); i++) {
            TEstado estadoItem = (TEstado) cmbEstado.getItemAt(i);
            if (estadoItem.getIdEstado() == estadoSeleccionado.getIdEstado()) {
                cmbEstado.setSelectedIndex(i);
                break;
            }
        }

        txtTipoDispositivo.setText(ordenReparacion.getTipoDispositivo());
        txtMarca.setText(ordenReparacion.getMarca());
        txtModelo.setText(ordenReparacion.getModelo());
        txtPassword.setText(ordenReparacion.getPassword());
        txtSerie.setText(ordenReparacion.getNroSerie());

        txtValorDiagnostico.setText(String.valueOf(ordenReparacion.getValorDiagnostico()));
        txtValorReparacion.setText(String.valueOf(ordenReparacion.getValorReparacion()));

        txtProblemaReportado.setText(ordenReparacion.getProblemaReportado());
        txtDesarrolloTecnico.setText(historialDesarrollo.getDesarrolloTecnico());

        dateEntrega.setCalendar(cCal.convertirTimestampACalendar(ordenReparacion.getFechaEntrega()));

        txtObservaciones.setText(ordenReparacion.getObservaciones());
        txtComentarios.setText(ordenReparacion.getComentarios());

        cargarImagenesDesdeLista(imagenes);
    }

    private void mostrarImagenActual() {
        if (!imagenesBytes.isEmpty() && indiceActual >= 0 && indiceActual < imagenesBytes.size()) {
            byte[] imgData = imagenesBytes.get(indiceActual);

            try {
                ImageIcon icono = new ImageIcon(imgData);
                Image imgEscalada = icono.getImage().getScaledInstance(
                        lblImagen.getWidth(),
                        lblImagen.getHeight(),
                        Image.SCALE_SMOOTH
                );
                lblImagen.setIcon(new ImageIcon(imgEscalada));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertirImagenABytes(File archivo) throws IOException {
        BufferedImage bImage = ImageIO.read(archivo);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos);
        return bos.toByteArray();
    }

    public void guardarImagenes() {
        // Crear carpeta destino con el nombre de la orden
        File carpetaDestino = new File("src/resources/img/" + txtOrden.getText());
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs();
        }

        for (String rutaTemp : rutasImagenes) {

            File archivoTemp = new File(rutaTemp);
            String extension = archivoTemp.getName().substring(archivoTemp.getName().lastIndexOf("."));
            String nuevoNombre = "img_" + System.currentTimeMillis() + extension;
            File destinoFinal = new File(carpetaDestino, nuevoNombre);

            System.out.println(destinoFinal.getPath());
            rutasFinalImagen.add(destinoFinal.getPath());

            try {
                Files.copy(archivoTemp.toPath(), destinoFinal.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarImagenesDesdeLista(List<TImagenes> imagenes) {
        imagenesBytes.clear(); // Limpiar imágenes previas en memoria

        for (TImagenes img : imagenes) {
            imagenesBytes.add(img.getImagen()); // Guardar los bytes de las imágenes en la lista
        }

        if (!imagenesBytes.isEmpty()) {
            indiceActual = 0; // Reiniciar índice
            mostrarImagenActual(); // Mostrar la primera imagen
        } else {
            System.out.println("No hay imágenes disponibles.");
        }
    }

    public void reiniciarForm() {
        client.llenarComboBox(cmbCliente);
        est.llenarComboBox(cmbEstado);
        usr.llenarComboBox(cmbTecnico);
        txtOrden.setText(repair.generarOrden());
        dateRecepcion.setCalendar(fechaActual);
        txtTipoDispositivo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtPassword.setText("");
        txtSerie.setText("");
        txtValorDiagnostico.setText("");
        txtValorReparacion.setText("");
        txtProblemaReportado.setText("");
        txtDesarrolloTecnico.setText("");
        dateEntrega.cleanup();
        txtObservaciones.setText("");
        txtComentarios.setText("");
        repair.cargarDatosTabla(tableModel);
        if (!imagenesBytes.isEmpty()) {
            imagenesBytes.clear();  // Limpiar la lista de imágenes en memoria
            indiceActual = 0;       // Reiniciar el índice

            lblImagen.setIcon(null); // Limpiar la imagen mostrada en la etiqueta

            System.out.println("Se eliminaron todas las imágenes del visor (temporalmente)");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtOrden = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTipoDispositivo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtValorReparacion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtValorDiagnostico = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtProblemaReportado = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmbTecnico = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        cmbCliente = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesarrolloTecnico = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        dateRecepcion = new com.toedter.calendar.JDateChooser();
        dateEntrega = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        btnEliminar = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        PnlImagenes = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        btnAgregarImagen = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnMensaje = new javax.swing.JButton();
        btnHistorialDesarrollo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Estas ingresando una nueva orden: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTitulo)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nº de orden: ");
        jLabel2.setToolTipText("");
        jLabel2.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel3.setText("Fecha recepcion:");

        txtOrden.setEditable(false);
        txtOrden.setBackground(new java.awt.Color(231, 231, 231));
        txtOrden.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        txtOrden.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtOrden.setFocusable(false);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tecnico o cargo:");
        jLabel5.setToolTipText("");
        jLabel5.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel5.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Cliente:");
        jLabel7.setToolTipText("");
        jLabel7.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel7.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Estado:");
        jLabel9.setToolTipText("");
        jLabel9.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel9.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel10.setText("Marca:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tipo de dispositivo:");
        jLabel11.setToolTipText("");
        jLabel11.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel11.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel12.setText("Contraseña:");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Modelo:");
        jLabel13.setToolTipText("");
        jLabel13.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel13.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel13.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Nº de Serie:");
        jLabel15.setToolTipText("");
        jLabel15.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel15.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel15.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel16.setText("Valor de reparacion:");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Valor de diagnostico:");
        jLabel17.setToolTipText("");
        jLabel17.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel17.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Problema reportado:");
        jLabel19.setToolTipText("");
        jLabel19.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel19.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Fecha entrega:");
        jLabel20.setToolTipText("");
        jLabel20.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel20.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 16));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Desarrollo tecnico:");
        jLabel21.setToolTipText("");
        jLabel21.setMaximumSize(new java.awt.Dimension(120, 16));
        jLabel21.setMinimumSize(new java.awt.Dimension(120, 16));
        jLabel21.setPreferredSize(new java.awt.Dimension(120, 16));

        jButton1.setText("Agregar");

        txtDesarrolloTecnico.setColumns(20);
        txtDesarrolloTecnico.setRows(5);
        jScrollPane1.setViewportView(txtDesarrolloTecnico);

        jCheckBox1.setText("El equipo ingresa por garantia");

        dateRecepcion.setDateFormatString("yyyy/MM/dd HH:mm:ss ");

        dateEntrega.setDateFormatString("yyyy/MM/dd HH:mm:ss ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProblemaReportado))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtValorDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorReparacion))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSerie))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateRecepcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cmbTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTipoDispositivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbEstado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cmbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))))
                .addGap(23, 27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoDispositivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtValorReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemaReportado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(dateEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Imagenes del equipo:");

        jLabel6.setText("Observaciones:");

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        btnEliminar.setText("Eliminar Galeria");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSiguiente.setText(">");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAnterior.setText("<");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        PnlImagenes.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout PnlImagenesLayout = new javax.swing.GroupLayout(PnlImagenes);
        PnlImagenes.setLayout(PnlImagenesLayout);
        PnlImagenesLayout.setHorizontalGroup(
            PnlImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PnlImagenesLayout.setVerticalGroup(
            PnlImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnAgregarImagen.setText("Agregar Imagen");
        btnAgregarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnAgregarImagen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(PnlImagenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PnlImagenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSiguiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PnlImagenes.getAccessibleContext().setAccessibleName("");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnMensaje.setText("Mensaje");
        btnMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMensajeActionPerformed(evt);
            }
        });

        btnHistorialDesarrollo.setText("Historial de desarrollo tecnico");
        btnHistorialDesarrollo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialDesarrolloActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHistorialDesarrollo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnHistorialDesarrollo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Comentarios:");

        txtComentarios.setColumns(20);
        txtComentarios.setRows(5);
        jScrollPane3.setViewportView(txtComentarios);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarImagenActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = chooser.getSelectedFile();

            try {
                // Convertir imagen a bytes
                byte[] imagenBytes = convertirImagenABytes(archivoSeleccionado);
                imagenesBytes.add(imagenBytes); // Guardar en lista

                // Mostrar la imagen en el JLabel
                ImageIcon imagenOriginal = new ImageIcon(archivoSeleccionado.getAbsolutePath());
                Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(
                        lblImagen.getWidth(),
                        lblImagen.getHeight(),
                        Image.SCALE_SMOOTH
                );

                lblImagen.setIcon(new ImageIcon(imagenEscalada));

                // Actualizar índice y mostrar imagen actual
                indiceActual = imagenesBytes.size() - 1;
                mostrarImagenActual();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnAgregarImagenActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        if (indiceActual > 0) {
            indiceActual--;
            mostrarImagenActual();
        }
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        if (indiceActual < imagenesBytes.size() - 1) {
            indiceActual++;
            mostrarImagenActual();
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (!imagenesBytes.isEmpty()) {
            imagenesBytes.clear();  // Limpiar la lista de imágenes en memoria
            indiceActual = 0;       // Reiniciar el índice

            lblImagen.setIcon(null); // Limpiar la imagen mostrada en la etiqueta

            System.out.println("Se eliminaron todas las imágenes del visor (temporalmente)");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (id == null) {
            tor.setOrdenTrabajo(txtOrden.getText());
            tor.setFechaRecepcion(cCal.JDateChooserATimestamp(dateRecepcion));
            tor.setIdUsuario(cmbTecnico.getItemAt(cmbTecnico.getSelectedIndex()).getIdUsuario());
            tor.setIdCliente(cmbCliente.getItemAt(cmbCliente.getSelectedIndex()).getId_cliente());
            tor.setIdEstado(cmbEstado.getItemAt(cmbEstado.getSelectedIndex()).getIdEstado());
            tor.setTipoDispositivo(txtTipoDispositivo.getText());
            tor.setMarca(txtMarca.getText());
            tor.setModelo(txtModelo.getText());
            tor.setPassword(txtPassword.getText());
            tor.setNroSerie(txtSerie.getText());
            tor.setValorDiagnostico(Double.parseDouble(txtValorDiagnostico.getText()));
            tor.setValorReparacion(Double.parseDouble(txtValorReparacion.getText()));
            tor.setProblemaReportado(txtProblemaReportado.getText());
            tor.setFechaEntrega(cCal.JDateChooserATimestamp(dateEntrega));
            tor.setObservaciones(txtObservaciones.getText());
            tor.setComentarios(txtComentarios.getText());

            htor.setDesarrolloTecnico(txtDesarrolloTecnico.getText());
            htor.setOrden_trabajo(txtOrden.getText());
            htor.setFechaCambio(cCal.JDateChooserATimestamp(dateRecepcion));

            or.registrar(tor);
            hor.registrar(htor);

            String OrdenTrabajo = txtOrden.getText();

            for (byte[] imagen : imagenesBytes) {
                TImagenes nuevaImagen = new TImagenes();
                nuevaImagen.setOrden_trabajo(OrdenTrabajo);
                nuevaImagen.setImagen(imagen);
                imgVM.registrar(nuevaImagen); // Enviar a la BD
            }

            dateRecepcion.setCalendar(fechaActual);
            btnImprimir.setEnabled(true);
            btnHistorialDesarrollo.setEnabled(true);
            btnMensaje.setEnabled(true);

        } else {
            tor.setOrdenTrabajo(txtOrden.getText());
            tor.setFechaRecepcion(cCal.JDateChooserATimestamp(dateRecepcion));
            tor.setIdUsuario(cmbTecnico.getItemAt(cmbTecnico.getSelectedIndex()).getIdUsuario());
            tor.setIdCliente(cmbCliente.getItemAt(cmbCliente.getSelectedIndex()).getId_cliente());
            tor.setIdEstado(cmbEstado.getItemAt(cmbEstado.getSelectedIndex()).getIdEstado());
            tor.setTipoDispositivo(txtTipoDispositivo.getText());
            tor.setMarca(txtMarca.getText());
            tor.setModelo(txtModelo.getText());
            tor.setPassword(txtPassword.getText());
            tor.setNroSerie(txtSerie.getText());
            tor.setValorDiagnostico(Double.parseDouble(txtValorDiagnostico.getText()));
            tor.setValorReparacion(Double.parseDouble(txtValorReparacion.getText()));
            tor.setProblemaReportado(txtProblemaReportado.getText());
            tor.setFechaEntrega(cCal.JDateChooserATimestamp(dateEntrega));
            tor.setObservaciones(txtObservaciones.getText());
            tor.setComentarios(txtComentarios.getText());

            htor.setDesarrolloTecnico(txtDesarrolloTecnico.getText());
            htor.setOrden_trabajo(txtOrden.getText());
            htor.setFechaCambio(cCal.JDateChooserATimestamp(dateRecepcion));

            or.actualizar(tor);
            hor.registrar(htor);

            imgVM.eliminar(id);

            String OrdenTrabajo = txtOrden.getText();

            for (byte[] imagen : imagenesBytes) {
                TImagenes nuevaImagen = new TImagenes();
                nuevaImagen.setOrden_trabajo(OrdenTrabajo);
                nuevaImagen.setImagen(imagen);
                imgVM.registrar(nuevaImagen); // Enviar a la BD
            }

        }
        if (repair != null && tableModel != null) {
            repair.cargarDatosTabla(tableModel);  // Ahora pasamos el modelo correcto
        }
        reiniciarForm();
        this.dispose();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnHistorialDesarrolloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialDesarrolloActionPerformed

        String orden = txtOrden.getText();
        FrmHistorial frmhistorial = new FrmHistorial(orden);
        if (!frmhistorial.isVisible()) {
            frmhistorial.setLocationRelativeTo(null);
            frmhistorial.setVisible(true);
            //this.dispose();
        }


    }//GEN-LAST:event_btnHistorialDesarrolloActionPerformed

    private void btnMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMensajeActionPerformed

    }//GEN-LAST:event_btnMensajeActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        GenerarOrdenServicio gen = new GenerarOrdenServicio(tor, htor);
    }//GEN-LAST:event_btnImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(FrmReparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReparacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReparacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlImagenes;
    private javax.swing.JButton btnAgregarImagen;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHistorialDesarrollo;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMensaje;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<TClientes> cmbCliente;
    private javax.swing.JComboBox<TEstado> cmbEstado;
    private javax.swing.JComboBox<TUsuarios> cmbTecnico;
    private com.toedter.calendar.JDateChooser dateEntrega;
    private com.toedter.calendar.JDateChooser dateRecepcion;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextArea txtDesarrolloTecnico;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtOrden;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtProblemaReportado;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtTipoDispositivo;
    private javax.swing.JTextField txtValorDiagnostico;
    private javax.swing.JTextField txtValorReparacion;
    // End of variables declaration//GEN-END:variables
}
