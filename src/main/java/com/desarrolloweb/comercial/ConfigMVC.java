package com.desarrolloweb.comercial;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMVC implements WebMvcConfigurer {

	@Value("${uploads.path}")
	private String uploadsDir;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        String rutaAbsUploadsCliente = Paths.get(uploadsDir).toUri().toString() + "cliente/";
        logger.info("Ruta absoluta de 'uploads' para cliente: " + rutaAbsUploadsCliente + " en " + getClass());
        registry.addResourceHandler("/uploads/cliente/**").addResourceLocations(rutaAbsUploadsCliente);

        String rutaAbsUploadsProducto = Paths.get(uploadsDir).toUri().toString() + "producto/";
        logger.info("Ruta absoluta de 'uploads' para producto: " + rutaAbsUploadsProducto + " en " + getClass());
        registry.addResourceHandler("/uploads/producto/**").addResourceLocations(rutaAbsUploadsProducto);

         String rutaAbsUploadsDocumento = Paths.get(uploadsDir).toUri().toString() + "documento/";
        logger.info("Ruta absoluta de 'uploads' para documento: " + rutaAbsUploadsDocumento + " en " + getClass());
        registry.addResourceHandler("/uploads/documento/**").addResourceLocations(rutaAbsUploadsDocumento);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("inicio");
        registry.addViewController("/error_403").setViewName("error/error_403");
        registry.addViewController("/mantenimiento").setViewName("mantenimiento/inicio");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    

}
