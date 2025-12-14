package com.desarrolloweb.comercial.model.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "documentos")
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty 
    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false, length = 255)
    private String archivo;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_carga")
    private Date fechaCarga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    
    public Documento() {
    }

    public Documento(Long id, String descripcion, String archivo, Date fechaCarga) {
        this.id = id;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.fechaCarga = fechaCarga;
    }

    
    @Override
    public String toString() {
        return "{id: " + id + ", descripcion: " + descripcion +
                ", archivo: " + archivo + ", fechaCarga: " + fechaCarga + "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}