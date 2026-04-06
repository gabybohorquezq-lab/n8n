package com.fybeca.frame_controller.service;

import com.fybeca.frame_controller.model.ge.DetalleServidorAplicacion;
import com.fybeca.frame_controller.repository.ge.DetalleServidorAplicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleServidorAplicacionService {

    @Autowired
    private DetalleServidorAplicacionRepository repository;

    @Transactional("geTransactionManager")
    public List<DetalleServidorAplicacion> buscarPorIdFc(Long idFc) {
        return repository.findByIdFc(idFc);
    }
}
