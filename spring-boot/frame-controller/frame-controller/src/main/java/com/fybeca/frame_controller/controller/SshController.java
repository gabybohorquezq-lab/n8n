package com.fybeca.frame_controller.controller;

import com.fybeca.frame_controller.model.SshRequest;
import com.fybeca.frame_controller.service.SshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ssh")
public class SshController {

    @Autowired
    private SshService sshService;

    @PostMapping("/ejecutar")
    public String ejecutar(@RequestBody SshRequest request) {
        return sshService.ejecutarScript(
                request.getIp(),
                request.getUsuario(),
                request.getPassword(),
                request.getRutaShell(),
                request.getServicio()
        );
    }
}