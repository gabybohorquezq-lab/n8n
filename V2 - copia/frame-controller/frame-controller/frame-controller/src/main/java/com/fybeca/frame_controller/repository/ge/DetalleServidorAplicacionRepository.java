package com.fybeca.frame_controller.repository.ge;

import com.fybeca.frame_controller.model.ge.DetalleServidorAplicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleServidorAplicacionRepository extends JpaRepository<DetalleServidorAplicacion, Long> {

    List<DetalleServidorAplicacion> findByIdFc(Long idFc);
}
