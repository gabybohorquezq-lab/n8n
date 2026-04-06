package com.fybeca.frame_controller.controller;

import com.fybeca.frame_controller.model.ge.DetalleServidorAplicacion;
import com.fybeca.frame_controller.service.DetalleServidorAplicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detalle-servidor")
public class DetalleServidorAplicacionController {

    @Autowired
    private DetalleServidorAplicacionService service;

    @PostMapping("/buscar")
    public ResponseEntity<List<DetalleServidorAplicacion>> buscar(@RequestBody Map<String, Long> body) {
        Long idFc = body.get("idFc");
        return ResponseEntity.ok(service.buscarPorIdFc(idFc));
    }
}
