package com.ticketplus.ms_analitica.service;

import com.ticketplus.ms_analitica.dto.PagoEventoDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnaliticaService {

    private final AtomicInteger totalTicketsVendidos = new AtomicInteger(0);
    private final AtomicReference<Double> recaudacionTotal = new AtomicReference<>(0.0);
    private final Map<Long, Integer> ventasPorEvento = new ConcurrentHashMap<>();

    public void registrarPago(PagoEventoDto pago) {
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new IllegalArgumentException("Monto inválido en evento de pago: " + pago.getMonto());
        }
        totalTicketsVendidos.incrementAndGet();
        recaudacionTotal.updateAndGet(actual -> actual + pago.getMonto());
        ventasPorEvento.merge(pago.getEventoId(), 1, Integer::sum);
    }

    public Map<String, Object> obtenerReporte() {
        return Map.of(
            "totalTicketsVendidos", totalTicketsVendidos.get(),
            "recaudacionTotal", recaudacionTotal.get(),
            "ventasPorEvento", ventasPorEvento
        );
    }
}