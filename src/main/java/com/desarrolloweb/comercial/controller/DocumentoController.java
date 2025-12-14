package com.desarrolloweb.comercial.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrolloweb.comercial.model.entity.Cliente;
import com.desarrolloweb.comercial.model.entity.Documento;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/comercial")
@SessionAttributes("documento")
public class DocumentoController {

    @Value("${uploads.path}")
	private String uploadsDir;
    
	private String uploadsDirDocumento;

   
    
	@PostConstruct
	public void init() {
		this.uploadsDirDocumento = Paths.get(uploadsDir, "documento").toString();
	}

    private final ComercialServiceIface comercialService;

    public DocumentoController(ComercialServiceIface comercialService) {
        this.comercialService = comercialService;
    }

    // ----------------------------------------------------
    // NUEVO DOCUMENTO PARA UN CLIENTE
    // ----------------------------------------------------
    @GetMapping("/documentonuevo/{id}")
    public String documentoNuevo(@PathVariable Long id, Model model, RedirectAttributes flash) {

        Cliente cliente = comercialService.buscarClientePorId(id);

        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos.");
            return "redirect:/comercial/clienteslistar";
        }

        Documento documento = new Documento();
        documento.setCliente(cliente);

        model.addAttribute("titulo", "Nuevo documento");
        model.addAttribute("btn_accion", "Guardar documento");
        model.addAttribute("cliente", cliente);
        model.addAttribute("documento", documento);

        return "documento/documento_nuevo";
    }

    // ----------------------------------------------------
    // GUARDAR DOCUMENTO
    // ----------------------------------------------------
 /*   @PostMapping("/guardardocumento")
public String guardarDocumento(
        @Valid @ModelAttribute Documento documento,
        BindingResult result,
        @RequestParam("file") MultipartFile file,
        RedirectAttributes flash,
        Model model
) {

    if (result.hasErrors()) {
        model.addAttribute("titulo", "Nuevo documento");
        model.addAttribute("btn_accion", "Guardar");
        return "documento/documento_nuevo";
    }

    if (file.isEmpty()) {
        model.addAttribute("error", "Debe seleccionar un archivo.");
        return "documento/documento_nuevo";
    }

    // Validar tipo MIME permitido (solo PDF e imágenes)
    String contentType = file.getContentType();
    if (contentType == null ||
        !(contentType.equals("application/pdf") ||
          contentType.startsWith("image/"))) {

        model.addAttribute("error", "Solo se permiten PDF o imágenes.");
        return "documento/documento_nuevo";
    }

    try {

        // Carpeta: uploads/cliente/documentos/
        Path directorio = Paths.get(uploadsDirDocumento).resolve(documento.getArchivo());
        Files.createDirectories(directorio);

        // Nombre único
        String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destino = directorio.resolve(nombreArchivo);

        // Guardar archivo físico
        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        // Guardar en base de datos
        documento.setArchivo(nombreArchivo);
        documento.setFechaCarga(new Date());

        comercialService.guardarDocumento(documento);

        flash.addFlashAttribute("success", "Documento guardado correctamente.");

    } catch (IOException e) {
        flash.addFlashAttribute("error", "Error al guardar el archivo: " + e.getMessage());
        return "redirect:/comercial/documentonuevo/" + documento.getCliente().getId();
    }

    return "redirect:/comercial/clienteconsultar/" + documento.getCliente().getId();
}*/

@PostMapping("/guardardocumento")
public String guardarDocumento(
        @Valid @ModelAttribute Documento documento,
        BindingResult result,
        @RequestParam("file") MultipartFile file,
        RedirectAttributes flash,
        Model model
) {

    Cliente clienteRecuperado = null;
    if (documento.getCliente() != null && documento.getCliente().getId() != null) {
         clienteRecuperado = comercialService.buscarClientePorId(documento.getCliente().getId());
         System.out.println("Cliente recuperado con ID: " + clienteRecuperado.getId());
    }

    if (result.hasErrors()) {
   
    result.getAllErrors().forEach(error -> {
        System.out.println("ERROR DE VALIDACIÓN: " + error.toString());
    });

    model.addAttribute("titulo", "Nuevo documento");
    model.addAttribute("btn_accion", "Guardar");
    model.addAttribute("cliente", clienteRecuperado); 
    return "documento/documento_nuevo";
}
    if (file.isEmpty()) {
        model.addAttribute("error", "Debe seleccionar un archivo.");
        model.addAttribute("titulo", "Nuevo documento"); 
        model.addAttribute("btn_accion", "Guardar");
        model.addAttribute("cliente", clienteRecuperado);
        return "documento/documento_nuevo";
    }

    String contentType = file.getContentType();
    if (contentType == null ||
        !(contentType.equals("application/pdf") ||
          contentType.startsWith("image/"))) {

        model.addAttribute("error", "Solo se permiten PDF o imágenes.");
        model.addAttribute("titulo", "Nuevo documento");
        model.addAttribute("btn_accion", "Guardar");
        // IMPORTANTE: Volver a pasar el cliente al modelo
        model.addAttribute("cliente", clienteRecuperado);
        return "documento/documento_nuevo";
    }

    try {
        
        Path directorio = Paths.get(uploadsDirDocumento); // Corregido para apuntar a la base
        Files.createDirectories(directorio);

        String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destino = directorio.resolve(nombreArchivo);

        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        documento.setArchivo(nombreArchivo);
        documento.setFechaCarga(new Date());

        comercialService.guardarDocumento(documento);

        flash.addFlashAttribute("success", "Documento guardado correctamente.");

    } catch (IOException e) {
        flash.addFlashAttribute("error", "Error al guardar el archivo: " + e.getMessage());
        return "redirect:/comercial/documentonuevo/" + documento.getCliente().getId();
    }

    return "redirect:/comercial/clienteconsultar/" + documento.getCliente().getId();
}

    @GetMapping("/documentoconsultar/{id}")
    public String documentoConsultar(@PathVariable Long id, Model model, RedirectAttributes flash) {

        Documento documento = comercialService.buscarDocumentoPorId(id);

        if (documento == null) {
            flash.addFlashAttribute("warning", "El documento no fue hallado.");
            return "redirect:/comercial/clienteslistar";
        }

        model.addAttribute("titulo", "Consulta de documento N° " + documento.getId());
        model.addAttribute("documento", documento);

        return "documento/consulta_documento";
    }

    @GetMapping("/documentoeliminar/{id}")
    public String eliminarDocumento(@PathVariable Long id, RedirectAttributes flash) {

        Documento documento = comercialService.buscarDocumentoPorId(id);

        if (documento == null) {
            flash.addFlashAttribute("warning", "No se pudo eliminar. El documento no existe.");
            return "redirect:/comercial/clienteslistar";
        }

        Long clienteId = documento.getCliente().getId();

        comercialService.eliminarDocumentoPorId(id);
        flash.addFlashAttribute("success", "Documento eliminado correctamente");

        return "redirect:/comercial/clienteconsultar/" + clienteId;
    }
}
