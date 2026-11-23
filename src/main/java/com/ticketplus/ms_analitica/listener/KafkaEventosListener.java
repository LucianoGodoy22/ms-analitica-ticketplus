package com.ticketplus.ms_analitica.listener;

import com.ticketplus.ms_analitica.dto.AuditoriaEventoDto;
import com.ticketplus.ms_analitica.dto.PagoEventoDto;
import com.ticketplus.ms_analitica.service.AnaliticaService;
import com.ticketplus.ms_analitica.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventosListener {

    private final AnaliticaService analiticaService;
    private final AuditoriaService auditoriaService;


    
    @KafkaListener(
        topics = "${kafka.topic.pagos}",
        groupId = "${kafka.group.analitica}",
        properties = {"spring.json.value.default.type=com.ticketplus.ms_analitica.dto.PagoEventoDto"}
    )
    public void consumirPago(PagoEventoDto pago) {
        log.info("[ANALÍTICA] Evento de pago recibido: {}", pago);
        analiticaService.registrarPago(pago);
    }

    @KafkaListener(
        topics = "${kafka.topic.auditoria}",
        groupId = "${kafka.group.auditoria}",
        properties = {"spring.json.value.default.type=com.ticketplus.ms_analitica.dto.AuditoriaEventoDto"}
    )
    public void consumirAuditoria(AuditoriaEventoDto auditoria) {
        log.info("[AUDITORÍA] Evento de auditoría recibido: {}", auditoria);
        auditoriaService.registrarAuditoria(auditoria);
    }
}