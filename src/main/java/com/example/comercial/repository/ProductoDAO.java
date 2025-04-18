package com.example.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comercial.model.Producto;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long> {
    
}
