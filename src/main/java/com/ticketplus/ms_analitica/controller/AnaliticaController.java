package com.ticketplus.ms_analitica.controller;

import com.ticketplus.ms_analitica.dto.AuditoriaEventoDto;
import com.ticketplus.ms_analitica.service.AnaliticaService;
import com.ticketplus.ms_analitica.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/analitica")
@RequiredArgsConstructor
public class AnaliticaController {

    private final AnaliticaService analiticaService;
    private final AuditoriaService auditoriaService;

    @GetMapping("/reportes")
    public ResponseEntity<?> obtenerReportes() {
        return ResponseEntity.ok(analiticaService.obtenerReporte());
    }

    @GetMapping("/auditoria")
    public ResponseEntity<List<AuditoriaEventoDto>> obtenerAuditoria(
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String usuario) {

        return ResponseEntity.ok(
                auditoriaService.listarAuditoria(accion, usuario)
        );
    }
}