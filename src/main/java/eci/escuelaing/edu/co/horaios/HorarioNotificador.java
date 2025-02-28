package eci.escuelaing.edu.co.horaios;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class HorarioNotificador {

    public void notificarEliminacion(String laboratorio, LocalDateTime fecha) {
        String mensaje = "Horario eliminado: " + laboratorio + " - " + fecha;
        HorarioWebSocketHandler.sendMessage(mensaje);
    }
}
