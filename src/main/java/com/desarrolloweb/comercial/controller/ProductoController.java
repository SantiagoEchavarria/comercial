package com.desarrolloweb.comercial.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrolloweb.comercial.model.entity.Categoria;
import com.desarrolloweb.comercial.model.entity.Producto;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;
import com.desarrolloweb.comercial.utils.paginator.PageRender;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/comercial")
@SessionAttributes("producto")
public class ProductoController {

	@Value("${uploads.path}")
	private String uploadsDir;
    
	private String uploadsDirProducto;

   
    
	@PostConstruct
	public void init() {
		this.uploadsDirProducto = Paths.get(uploadsDir, "producto").toString();
	}    
 private final ComercialServiceIface comercialService;

    public ProductoController(ComercialServiceIface comercialService) {
        this.comercialService = comercialService;
    }
    @GetMapping("/productoslistar")
    public String productosListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {

		Pageable pagina = PageRequest.of(pag, 5);
		Page<Producto> productos = comercialService.buscarProductosTodos(pagina);

		PageRender<Producto> pageRender = new PageRender<>("/comercial/productoslistar", productos);

		model.addAttribute("pageRender", pageRender);
        model.addAttribute("titulo", "Listado de productos disponibles");
        model.addAttribute("productos", productos);
        return "producto/listado_productos";
    }

    @GetMapping("/productonuevo")
    public String productoFormNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo producto");
        model.addAttribute("accion", "Agregar");
        model.addAttribute("producto", new Producto());
        return "producto/formulario_producto";
    }

    @PostMapping("/productoguardar")
    public String productoGuardar(@Valid @ModelAttribute Producto producto, BindingResult result, RedirectAttributes flash, 
            Model model, SessionStatus status, @RequestParam(name = "file") MultipartFile imagen) {

        String accion = (producto.getId() == null) ? "agregado" : "modificado";

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo producto");
            model.addAttribute("accion", accion);
            model.addAttribute("info", "Complemente o corrija la información de los campos del formulario");
            return "producto/formulario_producto";
        }

		if(!imagen.isEmpty()) {
			if(producto.getId() != null && producto.getImagen().length() > 0 
				&& producto.getId() > 0 && producto.getImagen() != null) {

					Path rutaAbsUploads = Paths.get(uploadsDirProducto).resolve(producto.getImagen()).toAbsolutePath();
					File archivo = rutaAbsUploads.toFile();
					if(archivo.exists() && archivo.canRead()) {
						archivo.delete();
					}
			}

			String nombreArchivo = UUID.randomUUID().toString().substring(0, 8) + "_" + imagen.getOriginalFilename();
			Path rutaUploads = Paths.get(uploadsDirProducto).resolve(nombreArchivo);
			Path rutaAbsUploads = rutaUploads.toAbsolutePath();

			try {
				// copia el archivo desde el cliente a la carpeta uploads
				Files.copy(imagen.getInputStream(), rutaAbsUploads);
				// setear el nombre del archivo en el campo imagen de la clase
				producto.setImagen(nombreArchivo);
				flash.addFlashAttribute("info", "El archivo " + nombreArchivo + " fue cargado");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}       


        comercialService.guardarProducto(producto);
        status.setComplete();
        flash.addFlashAttribute("success", "El producto fue " + accion + " de forma correcta");
        return "redirect:/comercial/productoslistar";
    }

    @GetMapping("/productoconsultar/{id}")
    public String productoConsultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Producto producto = comercialService.buscarProductoPorId(id);
        if (producto == null) {
            flash.addFlashAttribute("warning", "El producto con ID " + id + " no está en la base de datos");
            return "redirect:/comercial/productoslistar";
        }
        model.addAttribute("titulo", "Consulta del producto: " + producto.getDescripcion());
        model.addAttribute("producto", producto);
        return "producto/consulta_producto";
    }

    @GetMapping("/productoeliminar/{id}")
    public String productoEliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            Producto producto = comercialService.buscarProductoPorId(id);
            if (producto != null) {
                comercialService.eliminarProductoPorId(id);
                flash.addFlashAttribute("success", "El producto fue eliminado");

    		    Path rutaAbsUploads = Paths.get(uploadsDirProducto).resolve(producto.getImagen());
				File archivo = rutaAbsUploads.toFile();
				archivo.delete();            
            }
            else {
                flash.addFlashAttribute("warning", "El producto con ID " + id + " no está en la base de datos");
            }
        }
        else {
            flash.addFlashAttribute("error", "Error, el ID no es válido !!");
        }
        return "redirect:/comercial/productoslistar";
    }

    @GetMapping("/productomodificar/{id}")
    public String productoFormModificar(@PathVariable(value = "id") Long id, Model model) {
        Producto producto = null;
        if (id > 0) {
            producto = comercialService.buscarProductoPorId(id);
            if (producto == null) {
                return "redirect:/comercial/productoslistar";
            }
        }
        model.addAttribute("accion", "Modificar");
        model.addAttribute("titulo", "Modificar producto");
        model.addAttribute("producto", producto);
        return "producto/formulario_producto";
    }

    @ModelAttribute("categorias")
    public List<Categoria> obtenerCategorias() {
        return comercialService.buscarCategoriasTodos();
    }

}