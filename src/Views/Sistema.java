/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Models.Reparaciones;
import Models.THistorialDesarrollo;
import Models.TImagenes;
import Models.TOrdenesReparacion;
import ViewModels.HistorialDesarrolloVM;
import ViewModels.ImagenesVM;
import ViewModels.OrdenesReparacionesVM;
import ViewModels.ReparacionesVM;
import static Views.FrmCliente.mostrarConfirmacion;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ALEX
 */
public class Sistema extends javax.swing.JFrame {

    FrmCliente frmcliente = new FrmCliente();
    FrmUsuario frmusuario = new FrmUsuario();
    ReparacionesVM repair = new ReparacionesVM();
    OrdenesReparacionesVM orVM = new OrdenesReparacionesVM();
    HistorialDesarrolloVM hroVM = new HistorialDesarrolloVM();
    ImagenesVM imgVM = new ImagenesVM();
    TOrdenesReparacion ordenReparacion = new TOrdenesReparacion();
    List<TImagenes> imagenes = new ArrayList<>();
    List<THistorialDesarrollo> historialDesarrollo  = new ArrayList<>();
    private DefaultTableModel tableModel;
    FrmReparacion frmreparacion = new FrmReparacion();

    public void forms() {

        frmcliente.setLocationRelativeTo(null);
        frmcliente.setVisible(true);
    }

    public void formsU() {

        frmusuario.setLocationRelativeTo(null);
        frmusuario.setVisible(true);
    }

    /**
     * Creates new form Sistema
     */
    public Sistema() {
        initComponents();
        getContentPane().setBackground(new Color(248, 248, 248));
        setExtendedState(MAXIMIZED_BOTH);
        tableModel = new DefaultTableModel(new String[]{"Orden", "Nombre de Cliente", "Dispositivo", "Marca", "Modelo", "Fecha Entrega", "Tecnico", " Estado"}, 0);

        tablaPrincipal.getTableHeader().setFont(new Font("Segie UI", Font.BOLD, 12));
        tablaPrincipal.getTableHeader().setOpaque(false);
        tablaPrincipal.getTableHeader().setBackground(new Color(32, 136, 203));
        tablaPrincipal.getTableHeader().setForeground(new Color(0, 0, 0));
        tablaPrincipal.setRowHeight(25);
        tablaPrincipal.getTableHeader().setReorderingAllowed(false);

        tablaPrincipal.setModel(tableModel);
        repair.cargarDatosReparacion(tableModel);

    }

    public static boolean mostrarConfirmacion(String mensaje, String titulo) {
        int opcion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return opcion == JOptionPane.YES_OPTION;
    }

    private void eliminarOrden() {
        int filaseleccionado = tablaPrincipal.getSelectedRow();
        if (filaseleccionado == -1 && filaseleccionado == 0) {
            JOptionPane.showMessageDialog(null, "Cliente no seleccionado");
        } else {
            if (mostrarConfirmacion("¿Estás seguro de eliminar cliente id : " + ordenReparacion.getOrdenTrabajo()+ "?", "Confirmar Eliminación")) {
                hroVM.eliminarHistorialReparacion(historialDesarrollo.get(historialDesarrollo.size() - 1).getOrden_trabajo());
                orVM.eliminarOrdenReparacion(ordenReparacion.getOrdenTrabajo());
                
            }

        }
    }

    private void modificarOrden() {
        int filaseleccionado = tablaPrincipal.getSelectedRow();
        if (filaseleccionado == -1) {
            JOptionPane.showMessageDialog(null, "orden no seleccionado");
        } else {
            FrmReparacion frmRepair = new FrmReparacion(repair,ordenReparacion,tableModel,historialDesarrollo.get(historialDesarrollo.size() - 1),imagenes);
            if (!frmRepair.isVisible()) {
                frmRepair.setLocationRelativeTo(null);
                frmRepair.setVisible(true);
            }
            //this.dispose();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PnlReparacion = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PnlClientes = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        PnlUsuarios = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        PnlConfiguracion = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        PnlAbout = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        PnlExportacion = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnNuevaOrden = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPrincipal = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/logo.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PnlReparacion.setBackground(new java.awt.Color(255, 255, 255));
        PnlReparacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlReparacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlReparacionMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/house.png"))); // NOI18N
        jLabel2.setText("Reparaciones");

        javax.swing.GroupLayout PnlReparacionLayout = new javax.swing.GroupLayout(PnlReparacion);
        PnlReparacion.setLayout(PnlReparacionLayout);
        PnlReparacionLayout.setHorizontalGroup(
            PnlReparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlReparacionLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlReparacionLayout.setVerticalGroup(
            PnlReparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlReparacionLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        PnlClientes.setBackground(new java.awt.Color(255, 255, 255));
        PnlClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PnlClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlClientesMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/client.png"))); // NOI18N
        jLabel8.setText("Clientes");

        javax.swing.GroupLayout PnlClientesLayout = new javax.swing.GroupLayout(PnlClientes);
        PnlClientes.setLayout(PnlClientesLayout);
        PnlClientesLayout.setHorizontalGroup(
            PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlClientesLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlClientesLayout.setVerticalGroup(
            PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlClientesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        PnlUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        PnlUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PnlUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlUsuariosMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/user.png"))); // NOI18N
        jLabel9.setText("Usuarios");

        javax.swing.GroupLayout PnlUsuariosLayout = new javax.swing.GroupLayout(PnlUsuarios);
        PnlUsuarios.setLayout(PnlUsuariosLayout);
        PnlUsuariosLayout.setHorizontalGroup(
            PnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlUsuariosLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlUsuariosLayout.setVerticalGroup(
            PnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlUsuariosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        PnlConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        PnlConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlConfiguracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlConfiguracionMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/config.png"))); // NOI18N
        jLabel10.setText("Configuracion");

        javax.swing.GroupLayout PnlConfiguracionLayout = new javax.swing.GroupLayout(PnlConfiguracion);
        PnlConfiguracion.setLayout(PnlConfiguracionLayout);
        PnlConfiguracionLayout.setHorizontalGroup(
            PnlConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlConfiguracionLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlConfiguracionLayout.setVerticalGroup(
            PnlConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlConfiguracionLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        PnlAbout.setBackground(new java.awt.Color(255, 255, 255));
        PnlAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlAboutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlAboutMouseExited(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/about.png"))); // NOI18N
        jLabel11.setText("Acerca de");

        javax.swing.GroupLayout PnlAboutLayout = new javax.swing.GroupLayout(PnlAbout);
        PnlAbout.setLayout(PnlAboutLayout);
        PnlAboutLayout.setHorizontalGroup(
            PnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlAboutLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlAboutLayout.setVerticalGroup(
            PnlAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlAboutLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlReparacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addComponent(PnlClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PnlUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PnlConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PnlAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(PnlReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PnlClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PnlUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PnlConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PnlAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        PnlExportacion.setBackground(new java.awt.Color(255, 255, 255));
        PnlExportacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnlExportacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PnlExportacionMouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/export.png"))); // NOI18N
        jLabel12.setText("Exportacion");

        javax.swing.GroupLayout PnlExportacionLayout = new javax.swing.GroupLayout(PnlExportacion);
        PnlExportacion.setLayout(PnlExportacionLayout);
        PnlExportacionLayout.setHorizontalGroup(
            PnlExportacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlExportacionLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlExportacionLayout.setVerticalGroup(
            PnlExportacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlExportacionLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlExportacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(PnlExportacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(308, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnEliminar.setBackground(new java.awt.Color(51, 153, 255));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevaOrden.setBackground(new java.awt.Color(51, 153, 255));
        btnNuevaOrden.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNuevaOrden.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaOrden.setText("Nueva Orden");
        btnNuevaOrden.setBorder(null);
        btnNuevaOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevaOrdenMouseClicked(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(51, 153, 255));
        btnModificar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.setBorder(null);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnRefrescar.setBackground(new java.awt.Color(84, 168, 0));
        btnRefrescar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefrescar.setForeground(new java.awt.Color(255, 255, 255));
        btnRefrescar.setText("Refrescar");
        btnRefrescar.setBorder(null);
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(675, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(btnNuevaOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1146, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(btnRefrescar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnNuevaOrden, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nº de pedido", "Nombre de cliente", "Dispositivo", "Marca", "Modelo", "Fecha de entrega", "Tecnico", "Estado"
            }
        ));
        tablaPrincipal.setGridColor(new java.awt.Color(229, 229, 229));
        tablaPrincipal.setRowHeight(30);
        tablaPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaPrincipalMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPrincipal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setText("filtro");

        jTextField2.setText("Buscar ");

        jLabel3.setText("Filtrar por estado:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1106, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PnlClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlClientesMouseClicked
        // TODO add your handling code here:
        forms();


    }//GEN-LAST:event_PnlClientesMouseClicked

    private void PnlUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlUsuariosMouseClicked

        formsU();


    }//GEN-LAST:event_PnlUsuariosMouseClicked

    private void PnlClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlClientesMouseEntered
        PnlClientes.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlClientesMouseEntered

    private void PnlClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlClientesMouseExited
        PnlClientes.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlClientesMouseExited

    private void PnlReparacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlReparacionMouseEntered
        PnlReparacion.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlReparacionMouseEntered

    private void PnlReparacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlReparacionMouseExited
        PnlReparacion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlReparacionMouseExited

    private void PnlUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlUsuariosMouseEntered
        PnlUsuarios.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlUsuariosMouseEntered

    private void PnlUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlUsuariosMouseExited
        PnlUsuarios.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlUsuariosMouseExited

    private void PnlConfiguracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlConfiguracionMouseEntered
        PnlConfiguracion.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlConfiguracionMouseEntered

    private void PnlConfiguracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlConfiguracionMouseExited
        PnlConfiguracion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlConfiguracionMouseExited

    private void PnlAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlAboutMouseExited
        PnlAbout.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlAboutMouseExited

    private void PnlAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlAboutMouseEntered
        PnlAbout.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlAboutMouseEntered

    private void PnlExportacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlExportacionMouseEntered
        PnlExportacion.setBackground(new Color(243, 242, 242));
    }//GEN-LAST:event_PnlExportacionMouseEntered

    private void PnlExportacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlExportacionMouseExited
        PnlExportacion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_PnlExportacionMouseExited

    private void btnNuevaOrdenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevaOrdenMouseClicked
        if (!frmreparacion.isVisible()) {
            frmreparacion.setLocationRelativeTo(null);
            frmreparacion.setVisible(true);
        }
    }//GEN-LAST:event_btnNuevaOrdenMouseClicked

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        repair.cargarDatosReparacion(tableModel);
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void tablaPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPrincipalMousePressed
        int fila = tablaPrincipal.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "orden no seleccionado");
        } else {
            String id = (String)tablaPrincipal.getValueAt(fila, 0);
            ordenReparacion = orVM.consultarPorId(id);
            historialDesarrollo = hroVM.consultarPorId(id);
            imagenes = imgVM.consultarPorId(id);
            System.out.println(id);
        }
    }//GEN-LAST:event_tablaPrincipalMousePressed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarOrden();
        repair.cargarDatosReparacion(tableModel);
        ordenReparacion = null;
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarOrden();
        repair.cargarDatosReparacion(tableModel);
    }//GEN-LAST:event_btnModificarActionPerformed

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
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlAbout;
    private javax.swing.JPanel PnlClientes;
    private javax.swing.JPanel PnlConfiguracion;
    private javax.swing.JPanel PnlExportacion;
    private javax.swing.JPanel PnlReparacion;
    private javax.swing.JPanel PnlUsuarios;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaOrden;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tablaPrincipal;
    // End of variables declaration//GEN-END:variables
}
