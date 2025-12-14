package com.desarrolloweb.comercial.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.desarrolloweb.comercial.model.entity.Categoria;
import com.desarrolloweb.comercial.model.entity.Cliente;
import com.desarrolloweb.comercial.model.entity.Documento;
import com.desarrolloweb.comercial.model.entity.Factura;
import com.desarrolloweb.comercial.model.entity.Producto;

public interface ComercialServiceIface {

	// servicios para Cliente
	public List<Cliente> buscarClientesTodos();
	public Page<Cliente> buscarClientesTodos(Pageable pageable);
	public void guardarCliente(Cliente cliente);
	public Cliente buscarClientePorId(Long id);
	public void eliminarClientePorId(Long id);	
    
    // servicios para Categoria
	public List<Categoria> buscarCategoriasTodos();
	public Page<Categoria> buscarCategoriasTodos(Pageable pageable);
	public Categoria buscarCategoriaPorId(Long id);

	// servicios para Producto
	public List<Producto> buscarProductosTodos();
	public Page<Producto> buscarProductosTodos(Pageable pageable);
	public void guardarProducto(Producto producto);
	public Producto buscarProductoPorId(Long id);
	public void eliminarProductoPorId(Long id);
	public List<Producto> buscarProductosPorDescripcion(String term);

	// servicios para Factura
	public void guardarFactura(Factura factura);
	public Factura buscarFacturaPorNroFactura(Long nroFactura);
	public void eliminarFacturaPorNroFactura(Long nroFactura);
	public Factura buscarFacturaPorIdConClienteDetalleProducto(Long nroFactura);

	// servicios para Documento
	public void guardarDocumento(Documento documento);
	public Documento buscarDocumentoPorId(Long id);
	public void eliminarDocumentoPorId(Long id);
	public List<Documento> buscarDocumentosTodos();
	
}
