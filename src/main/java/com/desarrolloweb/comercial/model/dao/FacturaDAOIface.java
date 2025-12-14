package com.desarrolloweb.comercial.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desarrolloweb.comercial.model.entity.Factura;

@Repository
public interface FacturaDAOIface extends JpaRepository<Factura, Long>{

    @Query("select f from Factura f join fetch f.cliente c join fetch f.detalles d join fetch d.producto p where f.nroFactura = ?1")
    public Factura buscarPorIdConClienteDetalleProducto(Long nroFactura);
    
}
