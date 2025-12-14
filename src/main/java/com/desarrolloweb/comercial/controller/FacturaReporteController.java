package com.desarrolloweb.comercial.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.desarrolloweb.comercial.model.entity.Detalle;
import com.desarrolloweb.comercial.model.entity.Factura;
import com.desarrolloweb.comercial.model.service.ComercialServiceIface;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/comercial")
public class FacturaReporteController {

    private final ComercialServiceIface comercialService;

    public FacturaReporteController(ComercialServiceIface comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/reporte-factura-pdf/{id}")
public void generarReporteFactura(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {

    InputStream jrxmlStream = getClass().getResourceAsStream("/reports/factura.jrxml");
    JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

    Factura factura = comercialService.buscarFacturaPorNroFactura(id);

    List<Map<String, Object>> detallesMap = factura.getDetalles().stream().map(d -> {
        Map<String, Object> map = new HashMap<>();
        map.put("producto", d.getProducto().getDescripcion());
        map.put("cantidad", d.getCantidad());
        map.put("precioUnitario", d.getProducto().getPrecio());
        map.put("subtotal", d.subtotalDetalle());
        return map;
    }).collect(Collectors.toList());

    JRBeanCollectionDataSource detallesDataSource =
            new JRBeanCollectionDataSource(detallesMap);

    Map<String, Object> params = new HashMap<>();
    params.put("nroFactura", factura.getNroFactura());
    params.put("descripcion", factura.getDescripcion());
    params.put("usuario", "Pepe");

    
    JasperPrint jasperPrint =
            JasperFillManager.fillReport(jasperReport, params, detallesDataSource);

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "inline; filename=detalles.pdf");
    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
}
/* 

    @GetMapping("/reporte-factura-pdf/{id}")
    public void generarReporteFactura(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {

        // Cargar el archivo .jrxml
        InputStream jrxmlStream = getClass().getResourceAsStream("/reports/factura.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // Obtener la factura (ejemplo: la factura #1)
        Factura factura = comercialService.buscarFacturaPorNroFactura(id);

        // Mapear la factura → Map<String,Object> (igual que haces con productos)
        Map<String, Object> facturaMap = new HashMap<>();

        facturaMap.put("nroFactura", factura.getNroFactura());
        facturaMap.put("descripcion", factura.getDescripcion());
        facturaMap.put("observacion", factura.getObservacion());

        // Conversión de fecha
        if (factura.getFechaVenta() != null) {
            facturaMap.put("fechaVenta", new java.sql.Date(factura.getFechaVenta().getTime()));
        } else {
            facturaMap.put("fechaVenta", null);
        }

        List<Map<String, Object>> detallesMap = factura.getDetalles().stream().map(d -> {
            Map<String, Object> map = new HashMap<>();
            map.put("producto", d.getProducto().getDescripcion());     // String
            map.put("cantidad", d.getCantidad());                      // Integer
            map.put("precioUnitario", d.getProducto().getPrecio());    // Double
            map.put("subtotal", d.subtotalDetalle());                  // Double
            return map;
        }).collect(Collectors.toList());


        // Datasource principal (una sola factura)
        JRBeanCollectionDataSource facturaDataSource =
                new JRBeanCollectionDataSource(List.of(facturaMap));

        // Datasource de los detalles (subdataset del Table)
        JRBeanCollectionDataSource detallesDataSource =
                new JRBeanCollectionDataSource(detallesMap);

        // Parámetros para pasar el datasource al subreport/table
        Map<String, Object> params = new HashMap<>();
        params.put("DETALLES_DATASET", detallesDataSource);
        params.put("usuario", "Pepe");

        // Llenar reporte
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, params, facturaDataSource);

        // Respuesta PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura.pdf");

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        response.getOutputStream().flush();
        response.getOutputStream().close();
    }*/
}
