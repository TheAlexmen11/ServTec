package Utils;

import Dao.ClienteDAO;
import Dao.DatosEmpresaDAO;
import Models.OrdenReparacion;
import Models.TClientes;
import Models.TDatosEmpresa;
import Models.THistorialDesarrollo;
import Models.TOrdenesReparacion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

public class GenerarOrdenServicio {

    private static final Font FONT_TITLE = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10);
    private static final Font FONT_SMALL = new Font(Font.FontFamily.HELVETICA, 8);
    private static final BaseColor GRAY_BACKGROUND = new BaseColor(227, 227, 227);
    private static TOrdenesReparacion ordenReparacion;
    private ClienteDAO client = new ClienteDAO();
    private static THistorialDesarrollo historialDesarrollo;
    private static TClientes clienteSeleccionado;
    private static TDatosEmpresa tdatosEmpresa = null;
    private static DatosEmpresaDAO datempre = new DatosEmpresaDAO();

    public GenerarOrdenServicio(TOrdenesReparacion ordenReparacion, THistorialDesarrollo historialDesarrollo) {
        GenerarOrdenServicio.ordenReparacion = ordenReparacion;
        GenerarOrdenServicio.historialDesarrollo = historialDesarrollo;

        clienteSeleccionado = client.consultarPorId(ordenReparacion.getIdCliente());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Orden de Servicio");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String destino = fileToSave.getAbsolutePath() + ".pdf";

            try {
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(destino));
                document.open();

                // Agregar encabezado
                document.add(createHeaderTable());

                // Agregar tabla de cliente y equipo
                document.add(createDataTable());
                // Agregar secciones dinámicas
                document.add(createSection("PROBLEMA REPORTADO:", ordenReparacion.getProblemaReportado()));
                document.add(createSection("DESARROLLO TÉCNICO", historialDesarrollo.getDesarrolloTecnico()));
                document.add(createSection("OBSERVACIONES:", ordenReparacion.getObservaciones()));
                document.add(createSection("COMENTARIOS:", ordenReparacion.getComentarios()));

                // Agregar términos y condiciones
                document.add(createTermsAndConditions());

                document.close();
                System.out.println("Orden de servicio generada con éxito en " + destino);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static PdfPTable createHeaderTable() throws DocumentException {

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 3, 1});
        tdatosEmpresa = datempre.consultarPorId(1);
        String empresaInfo = Stream.of(tdatosEmpresa)
                .map(e -> "RUC: 0987654321001\n"
                + "NOMBRE: " + e.getNombre() + "\n"
                + "TELÉFONO: " + e.getTelefono() + "\n"
                + "DIRECCIÓN: " + e.getDireccion() + "\n"
                + "RAZÓN SOCIAL: " + e.getRazonSocial())
                .collect(Collectors.joining());

        Paragraph paragraph = new Paragraph(empresaInfo, FONT_NORMAL);
        PdfPCell cellEmpresa = new PdfPCell(paragraph);

        cellEmpresa.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellEmpresa);

        // Crear una tabla con una sola columna para mantener el formato
        PdfPTable tableOrden = new PdfPTable(1);
        tableOrden.setWidthPercentage(100);

        // Crear un párrafo con diferentes tamaños de fuente
        Paragraph p = new Paragraph();
        p.add(new Chunk("ORDEN DE SERVICIO\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        p.add(new Chunk("FECHA DE IMPRESIÓN: " + CalendarTime.obtenerFechaFormateada() + "\n", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        p.add(new Chunk("ORDEN: " + ordenReparacion.getOrdenTrabajo(), new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD))); // Tamaño más grande

        // Agregar el párrafo a una celda sin borde
        PdfPCell cellOrden = new PdfPCell(p);
        cellOrden.setBorder(Rectangle.NO_BORDER);
        cellOrden.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellOrden);

        // Código QR
        try {
            Image qrImage = Image.getInstance("src/Resources/qr.png");
            qrImage.scaleAbsolute(80, 80);
            PdfPCell qrCell = new PdfPCell(qrImage, false);
            qrCell.setBorder(Rectangle.NO_BORDER);
            qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(qrCell);
        } catch (Exception e) {
            table.addCell(new PdfPCell(new Paragraph("QR no disponible", FONT_NORMAL)));
        }

        return table;
    }

    private static PdfPTable createDataTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 1});

        table.addCell(createHeaderCell("DATOS DEL CLIENTE:"));
        table.addCell(createHeaderCell("DATOS DEL EQUIPO:"));

        table.addCell(new PdfPCell(new Paragraph(
                "Nombre: " + clienteSeleccionado.getNombre() + "\nTeléfono: " + clienteSeleccionado.getTelefono() + "\n"
                + "Correo: " + clienteSeleccionado.getCorreo() + "\nDirección: " + clienteSeleccionado.getDireccion(), FONT_NORMAL)));

        table.addCell(new PdfPCell(new Paragraph(
                "Tipo: " + ordenReparacion.getTipoDispositivo() + "\nMarca: " + ordenReparacion.getMarca() + "nModelo: " + ordenReparacion.getModelo() + "\n"
                + "N° Serie: " + ordenReparacion.getNroSerie() + "\nRecepción: " + ordenReparacion.getFechaRecepcion() + "\nEntrega: " + ordenReparacion.getFechaEntrega() + "", FONT_NORMAL)));

        return table;
    }

    private static PdfPTable createSection(String title, String content) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.addCell(createHeaderCell(title));
        table.addCell(new PdfPCell(new Paragraph(content, FONT_NORMAL)));
        return table;
    }

    private static PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FONT_TITLE));
        cell.setBackgroundColor(GRAY_BACKGROUND);
        return cell;
    }

    private static Paragraph createTermsAndConditions() {
        return new Paragraph(
                "EL CLIENTE ACEPTA QUE:\n"
                + "1.- Será informado inmediatamente a penas el equipo esté listo para su retiro y entrega.\n"
                + "2.- Tiene un plazo máximo de 90 días para retirar los equipos reparados o no reparables.\n"
                + "3.- Vencido el plazo indicado, se procederá a embodegar los equipos por un valor de S/ 1 diario hasta 1 mes máximo.\n"
                + "4.- Si no ha retirado los equipos en los plazos indicados, Tecnopc Sistemas Ecuador NO SE RESPONSABILIZA por pérdidas parciales o totales.\n"
                + "5.- En caso de requerirse repuestos, el cliente DEBE CANCELAR EL TOTAL o abonar el 70% del valor total de los elementos a usarse.\n"
                + "6.- Si no responde a nuestros llamados o no retira sus equipos en el tiempo estipulado, estos pasarán a ser rematados sin derecho a reclamación.\n"
                + "7.- Todo equipo se entrega probado y funcionando correctamente en presencia del cliente.", FONT_SMALL);
    }
}
