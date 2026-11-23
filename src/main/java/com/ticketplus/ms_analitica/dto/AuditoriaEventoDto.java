package com.ticketplus.ms_analitica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaEventoDto {
    private String accion;       // Ej: EVENTO_CREADO, EVENTO_ACTUALIZADO, EVENTO_ELIMINADO
    private Long eventoId;
    private String usuarioAdmin; // Email o nombre del admin
    private String detalle;
    private LocalDateTime fecha;
}