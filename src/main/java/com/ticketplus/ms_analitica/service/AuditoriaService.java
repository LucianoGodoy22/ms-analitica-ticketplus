package com.ticketplus.ms_analitica.service;

import com.ticketplus.ms_analitica.dto.AuditoriaEventoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AuditoriaService {

    private final List<AuditoriaEventoDto> registros = new CopyOnWriteArrayList<>();

    public void registrarAuditoria(AuditoriaEventoDto evento) {
        registros.add(evento);
    }

    public List<AuditoriaEventoDto> listarAuditoria(String accion, String usuario) {
        return registros.stream()
            .filter(r -> accion == null || accion.isBlank() || r.getAccion().equalsIgnoreCase(accion))
            .filter(r -> usuario == null || usuario.isBlank() || r.getUsuarioAdmin().toLowerCase().contains(usuario.toLowerCase()))
            .toList();
    }
}