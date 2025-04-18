package com.example.comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.comercial.model.Producto;
import com.example.comercial.repository.ProductoDAO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService implements ProductoServiceIface {

    private final ProductoDAO productoDAO;

    public ProductoService(ProductoDAO productoDAO){
        this.productoDAO = productoDAO;
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Producto producto) {
        productoDAO.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        productoDAO.deleteById(id);
    }
    
}
