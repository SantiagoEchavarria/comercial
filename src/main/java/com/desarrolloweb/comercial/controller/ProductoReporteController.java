package com.desarrolloweb.comercial.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.util.JRLoader;
import com.desarrolloweb.comercial.model.entity.Producto;

@Controller
@RequestMapping("/comercial")
public class ProductoReporteController {
    
    private final ComercialServiceIface comercialService;

    public ProductoReporteController(ComercialServiceIface comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/reporte-productos-pdf")
    public void generarReporteProductos(HttpServletResponse response) throws Exception {
        InputStream jrxmlStream = getClass().getResourceAsStream("/reports/productos.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // InputStream jasperStream= getClass().getResourceAsStream("/reports/productos.jasper");
        // JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        List<Producto> productos = comercialService.buscarProductosTodos();

        // convertir LocalDate → java.util.Date para JasperReports
        List<Map<String, Object>> productosMap = productos.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("descripcion", p.getDescripcion());
            map.put("existencia", p.getExistencia());
            map.put("precio", p.getPrecio());
            // Conversión segura de LocalDate a java.util.Date
            if (p.getFechaUltIngreso() != null) {
                map.put("fechaUltIngreso", java.sql.Date.valueOf(p.getFechaUltIngreso()));
            } else {
                map.put("fechaUltIngreso", null);
            }

            map.put("disponible", p.isDisponible());
            return map;
        }).collect(Collectors.toList());        


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productosMap);

        Map<String, Object> params = new HashMap<>();
        params.put("usuario", "Pepe");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=reporte-productos.pdf");

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}
