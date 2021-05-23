
package com.mycompany.ficheros;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Opositor implements Serializable {
    // Atributos de instancia
    private String apellido1;
    private String apellido2;
    private String nombre;
    private LocalDate fechaSolicitud;
    
    
    // Constructor
    public Opositor(String pApe1, String pApe2, String pNom, LocalDate pFecSol) {
        this.apellido1 = pApe1;
        this.apellido2 = pApe2;
        this.nombre = pNom;
        this.fechaSolicitud = pFecSol;
    }
    
    // Getters y setters
    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    // Redefino el método equals para que dos opositores 
    // sean iguales cuando su nombre y apellidos son el mismo
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Opositor other = (Opositor) obj;
        if (!Objects.equals(this.apellido1, other.apellido1)) {
            return false;
        }
        if (!Objects.equals(this.apellido2, other.apellido2)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    // Método toString

    @Override
    public String toString() {
        return "Opositor{" + "apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nombre=" + nombre + ", fechaSolicitud=" + fechaSolicitud + "}\n";
    }
    
    
}
