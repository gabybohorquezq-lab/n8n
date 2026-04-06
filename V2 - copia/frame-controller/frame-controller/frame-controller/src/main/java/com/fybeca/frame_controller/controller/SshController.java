package com.fybeca.frame_controller.controller;

// 1. IMPORTS NECESARIOS
import com.fybeca.frame_controller.model.SshRequest;
import com.fybeca.frame_controller.model.ge.DetalleServidorAplicacion;
import com.fybeca.frame_controller.model.ge.ServidorAplicacion;
import com.fybeca.frame_controller.service.DetalleServidorAplicacionService;
import com.fybeca.frame_controller.service.ServidorAplicacionService;
import com.fybeca.frame_controller.service.SshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ssh")
public class SshController {

    @Autowired
    private SshService sshService;

    @Autowired
    private DetalleServidorAplicacionService detalleService;

    @Autowired
    private ServidorAplicacionService servidorService;

    /**
     * Endpoint que une la Tabla 1 (IP) y la Tabla 2 (Credenciales)
     * y muestra un reporte de los datos encontrados en Oracle.
     */
    @PostMapping("/ejecutar-con-bd")
    public String ejecutarDesdeBd(@RequestBody SshRequest request) {
        try {
            // --- PASO A: CONSULTA A TABLA 1 (SERVIDORES_APLICACION) ---
            ServidorAplicacion servidor = servidorService.buscarPorId(request.getIdFc());
            if (servidor == null) {
                return "ERROR ORACLE [Tabla 1]: No existe registro para el ID " + request.getIdFc();
            }
            // Importante: Verifica si en tu entidad es .getIp() o .getIpServidor()
            String ipFinal = servidor.getIp();

            // --- PASO B: CONSULTA A TABLA 2 (DETALLE_SERVIDOR_APLICACIONES) ---
            List<DetalleServidorAplicacion> detalles = detalleService.buscarPorIdFc(request.getIdFc());
            if (detalles.isEmpty()) {
                return "ERROR ORACLE [Tabla 2]: No hay credenciales configuradas para el ID " + request.getIdFc();
            }
            DetalleServidorAplicacion data = detalles.get(0);

            // --- PASO C: EJECUCIÓN DEL MOTOR SSH ---
            String resultadoSsh = sshService.ejecutarScript(
                    ipFinal,
                    data.getUsuarioFc(),
                    data.getPasswordFc(),
                    data.getRutaShellFc(),
                    request.getServicio()
            );

            // --- PASO D: ARMADO DEL REPORTE VISUAL PARA POSTMAN ---
            StringBuilder reporte = new StringBuilder();
            reporte.append("╔═══════════════════════════════════════════════════╗\n");
            reporte.append("║       REPORTE DE CONSULTA MULTI-TABLA (ORACLE)    ║\n");
            reporte.append("╠═══════════════════════════════════════════════════╣\n");
            reporte.append("  > ID BUSCADO      : ").append(request.getIdFc()).append("\n");
            reporte.append("  > IP (TABLA 1)    : ").append(ipFinal).append("\n");
            reporte.append("  > USUARIO (TABLA 2): ").append(data.getUsuarioFc()).append("\n");
            reporte.append("  > RUTA (TABLA 2)   : ").append(data.getRutaShellFc()).append("\n");
            reporte.append("  > SERVICIO        : ").append(request.getServicio()).append("\n");
            reporte.append("╠═══════════════════════════════════════════════════╣\n");
            reporte.append("║          RESULTADO DEL SERVIDOR LINUX             ║\n");
            reporte.append("╚═══════════════════════════════════════════════════╝\n\n");
            reporte.append(resultadoSsh);

            return reporte.toString();

        } catch (Exception e) {
            return "❌ ERROR CRÍTICO EN EL ENLACE: " + e.getMessage();
        }
    }
}