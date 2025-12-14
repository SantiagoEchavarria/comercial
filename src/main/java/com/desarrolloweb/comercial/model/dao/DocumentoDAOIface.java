package com.desarrolloweb.comercial.model.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.desarrolloweb.comercial.model.entity.Documento;


public interface DocumentoDAOIface extends JpaRepository <Documento, Long> {
    
}
