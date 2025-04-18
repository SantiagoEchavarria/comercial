package com.example.comercial.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @NotNull(message = "La existencia es obligatoria")
    @Min(value = 0, message = "La existencia no puede ser negativa")
    private Integer existencia;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private Double precio;

    @PastOrPresent(message = "La fecha no puede ser futura")
    @NotNull(message = "La fecha de ingreso es obligatoria")
    @Column(name = "fecha_ult_ingreso")
    private LocalDate fechaUltIngreso;

    @AssertTrue(message = "El producto debe estar disponible")
    private boolean disponible;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(min = 3, max = 50, message = "La categoría debe tener entre 3 y 50 caracteres")
    private String categoria;

    // Constructor vacío
    public Producto() {}

    // Constructor completo
    public Producto(Long id, String descripcion, Integer existencia, Double precio,
                    LocalDate fechaUltIngreso, boolean disponible, String categoria) {
        this.id = id;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.precio = precio;
        this.fechaUltIngreso = fechaUltIngreso;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    // Getters y Setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getExistencia() { return existencia; }
    public void setExistencia(Integer existencia) { this.existencia = existencia; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDate getFechaUltIngreso() { return fechaUltIngreso; }
    public void setFechaUltIngreso(LocalDate fechaUltIngreso) { this.fechaUltIngreso = fechaUltIngreso; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", existencia=" + existencia +
                ", precio=" + precio +
                ", fechaUltIngreso=" + fechaUltIngreso +
                ", disponible=" + disponible +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
