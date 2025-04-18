package com.example.comercial.service;

import java.util.List;

import com.example.comercial.model.Producto;

public interface ProductoServiceIface {
    public List<Producto> obtenerTodos();
    public void guardar(Producto producto);
    public Producto obtenerPorId(Long id);
    public void eliminar(Long id);
}
