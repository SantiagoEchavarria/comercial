package com.example.comercial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.comercial.model.Producto;
import com.example.comercial.service.ProductoServiceIface;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("producto")
public class ProductoController {
    private final ProductoServiceIface productoService;

    public ProductoController(ProductoServiceIface productoService){
        this.productoService = productoService;
    }

    //Mostrar Formulario para crear un nuevo producto
    @GetMapping("/nuevoProducto")
    public String nuevoProducto(Model model){
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        return "vistas/producto_form";
    }

    //Mostrar Formulario para crear un producto existente
    @GetMapping("/editar/{id}")
    public String productoModificar(@PathVariable Long id, Model model){
        Producto producto = productoService.obtenerPorId(id);
        if(producto==null){
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        return "vistas/producto_form";
    }

    // Procesar el formulario crear o editar un producto
    @PostMapping("/procesar")
    public String procesarFormularioProducto(@Valid @ModelAttribute Producto producto, 
    BindingResult errors, Model model, SessionStatus status, RedirectAttributes flash){
        if(errors.hasErrors()){
            model.addAttribute("titulo", "listado djkjd");
            return "vistas/producto_form";
        }
        productoService.guardar(producto);
        status.setComplete();
        return "redirect:/productos";
    }

    //Muestra la lista de todos los productos
    @GetMapping("/productos")
    public String listarProductos(Model model){
        model.addAttribute("productos", productoService.obtenerTodos());
        return "vistas/productos_listar";
    }

    //Muestra los detalles de un producto especifico
    @GetMapping("/consultar/{id}")
    public String consultarProducto(@PathVariable Long id, Model model, RedirectAttributes flash){
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null){
            flash.addFlashAttribute("error", "el empleado no fue encontrado en la base de datos");
            return "redirect:/productos";
        }
        model.addAttribute("titulo", "Detalles productos:");
        model.addAttribute("producto", producto);
        return "vistas/producto_consulta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes flash){
        Producto producto = productoService.obtenerPorId(id);

        if(producto == null){
            return "redirect:/productos";
        }
        flash.addAttribute("exito", "El producto fue eliminado");
        productoService.eliminar(id);
        return "redirect:/productos";
    }

}
