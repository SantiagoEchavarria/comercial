package com.desarrolloweb.comercial.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.desarrolloweb.comercial.model.entity.Cliente;
import com.desarrolloweb.comercial.model.entity.Documento;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;
import com.desarrolloweb.comercial.utils.paginator.PageRender;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/comercial")
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${uploads.path}")
	private String uploadsDir;
	
	private String uploadsDirCliente;

    private final ComercialServiceIface comercialService;
	
    public ClienteController(ComercialServiceIface comercialService) {
		this.comercialService = comercialService;
    }
    
	@PostConstruct
	public void init() {
		this.uploadsDirCliente = Paths.get(uploadsDir, "cliente").toString();
	}

	@GetMapping("/clienteslistar")
	public String clientesListar(@RequestParam(name = "pag", defaultValue = "0") int pag, Model model) {
		
		System.out.println(passwordEncoder.encode("Abc123"));

		Pageable pagina = PageRequest.of(pag, 5);
		Page<Cliente> clientes = comercialService.buscarClientesTodos(pagina);

		PageRender<Cliente> pageRender = new PageRender<>("/comercial/clienteslistar", clientes);

		model.addAttribute("pageRender", pageRender);
		model.addAttribute("titulo", "Listado de clientes activos");
		model.addAttribute("clientes", clientes);
		return "cliente/listado_clientes";
	}

	@GetMapping("/clientenuevo")
	public String clienteFormNuevo(Model model) {
		model.addAttribute("titulo", "Nuevo cliente");
		model.addAttribute("accion", "Crear");
		model.addAttribute("cliente", new Cliente());
		return "cliente/formulario_cliente";
	}

	@PostMapping("/clienteguardar")
	public String clienteGuardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, 
			Model model, SessionStatus status, RedirectAttributes flash, 
			@RequestParam(name = "file") MultipartFile imagen) {

		String accion = (cliente.getId() == null) ? "agregado" : "modificado";
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo cliente");
			model.addAttribute("accion", accion);
			model.addAttribute("info", "Complemente o corrija la información de los campos del formulario");
			return "cliente/formulario_cliente";
		}

		if(!imagen.isEmpty()) {
			if(cliente.getId() != null && cliente.getImagen().length() > 0 
				&& cliente.getId() > 0 && cliente.getImagen() != null) {

					Path rutaAbsUploads = Paths.get(uploadsDirCliente).resolve(cliente.getImagen()).toAbsolutePath();
					File archivo = rutaAbsUploads.toFile();
					if(archivo.exists() && archivo.canRead()) {
						archivo.delete();
					}
			}

			String nombreArchivo = UUID.randomUUID().toString().substring(0, 8) + "_" + imagen.getOriginalFilename();
			Path rutaUploads = Paths.get(uploadsDirCliente).resolve(nombreArchivo);
			Path rutaAbsUploads = rutaUploads.toAbsolutePath();

			try {
				// copia el archivo desde el cliente a la carpeta uploads
				Files.copy(imagen.getInputStream(), rutaAbsUploads);
				// setear el nombre del archivo en el campo imagen de la clase
				cliente.setImagen(nombreArchivo);
				flash.addFlashAttribute("info", "El archivo " + nombreArchivo + " fue cargado");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		comercialService.guardarCliente(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", "El cliente fue " + accion + " de forma correcta");
		return "redirect:/comercial/clienteslistar";
	}
/*
	@GetMapping("/clienteconsultar/{id}")
	public String clienteConsultar(@PathVariable Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = comercialService.buscarClientePorId(id);
		if (cliente == null) {
			flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
			return "redirect:/comercial/clienteslistar";
		}
		model.addAttribute("titulo", "Consulta del cliente: " + cliente.getNombreCompleto());
		model.addAttribute("cliente", cliente);
		return "cliente/consulta_cliente";
	}	/ */

 @GetMapping("/clienteconsultar/{id}")
    public String clienteConsultar(@PathVariable Long id, RedirectAttributes flash, Model model) {

        Cliente cliente = comercialService.buscarClientePorId(id);

        if (cliente == null) {
            flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
            return "redirect:/comercial/clienteslistar";
        }

        model.addAttribute("titulo", "Consulta del cliente: " + cliente.getNombreCompleto());
        model.addAttribute("cliente", cliente);

        return "cliente/consulta_cliente";
    }

    @PostMapping("/documentosubir")
    public String subirDocumento(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("archivo") MultipartFile archivo,
            RedirectAttributes flash) {

        Cliente cliente = comercialService.buscarClientePorId(clienteId);

        if (cliente == null) {
            flash.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/comercial/clienteslistar";
        }

        if (archivo.isEmpty()) {
            flash.addFlashAttribute("warning", "Debe seleccionar un archivo");
            return "redirect:/comercial/clienteconsultar/" + clienteId;
        }

        try {
            String nombreArchivo = archivo.getOriginalFilename();
           //String ruta = "uploads/documentos/" + nombreArchivo;

            java.nio.file.Path destino = java.nio.file.Paths.get("uploads/documentos").resolve(nombreArchivo);
            java.nio.file.Files.copy(archivo.getInputStream(), destino);

            Documento documento = new Documento();
            documento.setDescripcion(descripcion);
            documento.setArchivo(nombreArchivo);
            documento.setFechaCarga(new Date());
            documento.setCliente(cliente);

            cliente.addDocumento(documento);
            comercialService.guardarDocumento(documento);

            flash.addFlashAttribute("success", "Documento cargado correctamente");

        } catch (IOException e) {
            flash.addFlashAttribute("error", "Error subiendo el archivo");
        }

        return "redirect:/comercial/clienteconsultar/" + clienteId;
    }

	@GetMapping("/clientemodificar/{id}")
	public String clienteFormModificar(@PathVariable Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = comercialService.buscarClientePorId(id);
			if (cliente == null) {
				flash.addFlashAttribute("warning", "El registro no fue hallado en la base de datos");
				return "redirect:/comercial/clienteslistar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
			return "redirect:/comercial/clienteslistar";
		}
		model.addAttribute("accion", "Modificar");
		model.addAttribute("titulo", "Modificar cliente");
		model.addAttribute("cliente", cliente);
		return "cliente/formulario_cliente";
	}
	
	@GetMapping("/clienteeliminar/{id}")
	public String clienteEliminar(@PathVariable Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = comercialService.buscarClientePorId(id);
			if(cliente != null) {
				comercialService.eliminarClientePorId(id);
				flash.addFlashAttribute("success", "El registro fue eliminado de la base de datos");
			
				Path rutaAbsUploads = Paths.get(uploadsDirCliente).resolve(cliente.getImagen());
				File archivo = rutaAbsUploads.toFile();
				archivo.delete();
			}
			else {
				flash.addFlashAttribute("warning", "El cliente con ID " + id + " no está en la base de datos");
			}
		}
		else {
			flash.addFlashAttribute("error", "Error, el ID no es válido !!");
		}
		return "redirect:/comercial/clienteslistar";
	}	
} 