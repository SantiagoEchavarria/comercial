package com.desarrolloweb.comercial.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.comercial.model.entity.Cliente;

@Repository
public interface ClienteDAOIface extends JpaRepository<Cliente, Long>{
}
