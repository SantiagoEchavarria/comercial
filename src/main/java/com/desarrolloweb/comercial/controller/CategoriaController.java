package com.desarrolloweb.comercial.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desarrolloweb.comercial.model.entity.Categoria;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;
import com.desarrolloweb.comercial.utils.paginator.PageRender;

@Controller
@RequestMapping("/comercial")
@SessionAttributes("categoria")
public class CategoriaController {

    private final ComercialServiceIface comercialService;

    public CategoriaController(ComercialServiceIface comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/categoriaslistar")
    public String categoriasListar(@RequestParam(value = "pag", defaultValue = "0") int pag, Model model) {

		Pageable pagina = PageRequest.of(pag, 5);
		Page<Categoria> categorias = comercialService.buscarCategoriasTodos(pagina);

		PageRender<Categoria> pageRender = new PageRender<>("/comercial/categoriaslistar", categorias);

		model.addAttribute("pageRender", pageRender);

        model.addAttribute("titulo", "Listado de categorías");
        model.addAttribute("categorias", categorias);
        return "categoria/listado_categorias";
    }

    @GetMapping("/categoriaconsultar/{id}")
    public String categoriaConsultar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Categoria categoria = comercialService.buscarCategoriaPorId(id);
        if (categoria == null) {
            flash.addFlashAttribute("warning", "La categoría con ID " + id + " no está en la base de datos");
            return "redirect:/comercial/categoriaslistar";
        }
        model.addAttribute("titulo", "Consulta de una categoría");
        model.addAttribute("categoria", categoria);
        return "categoria/consulta_categoria";
    }
}
