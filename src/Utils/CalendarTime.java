package Utils;

import java.sql.Timestamp;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarTime {


    public Timestamp JDateChooserATimestamp(JDateChooser dateChooser) {
        Date fechaDate = dateChooser.getDate(); // Obtener la fecha seleccionada
        return (fechaDate != null) ? new Timestamp(fechaDate.getTime()) : null;
    }

    public Calendar convertirTimestampACalendar(Timestamp timestamp) {
        if (timestamp == null) {
            return null; // Evita errores si el valor es nulo
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime()); // Asignar el tiempo
        return calendar;
    }

    public static String obtenerFechaFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
        return sdf.format(new Date()); // Devuelve la fecha actual formateada
    }
}
