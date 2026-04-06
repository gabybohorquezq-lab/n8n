package com.fybeca.frame_controller.model.ge;

import jakarta.persistence.*;

@Entity
@Table(name = "SERVIDORES_APLICACION")
public class ServidorAplicacion {

    @Id
    @Column(name = "ID_FC")
    private Long idFc;

    @Column(name = "IP")
    private String ip;

    @Column(name = "NOMBRE_SERVIDOR")
    private String nombreServidor;

    @Column(name = "ACTIVO")
    private Integer activo;

    @Column(name = "OBSERVACION")
    private String observacion;

    public Long getIdFc() { return idFc; }
    public void setIdFc(Long idFc) { this.idFc = idFc; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getNombreServidor() { return nombreServidor; }
    public void setNombreServidor(String nombreServidor) { this.nombreServidor = nombreServidor; }

    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
