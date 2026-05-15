package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.*;

public class TecnicoCompleto {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty especialidad;
    private final SimpleDoubleProperty calificacion;
    private final SimpleIntegerProperty ordenesActivas;
    private final SimpleStringProperty telefono;
    private final SimpleStringProperty disponibilidad;

    public TecnicoCompleto(int id, String nombre, String especialidad, double calificacion, int ordenesActivas, String telefono, String disponibilidad) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.especialidad = new SimpleStringProperty(especialidad);
        this.calificacion = new SimpleDoubleProperty(calificacion);
        this.ordenesActivas = new SimpleIntegerProperty(ordenesActivas);
        this.telefono = new SimpleStringProperty(telefono);
        this.disponibilidad = new SimpleStringProperty(disponibilidad);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getNombre() { return nombre.get(); }
    public SimpleStringProperty nombreProperty() { return nombre; }
    public String getEspecialidad() { return especialidad.get(); }
    public SimpleStringProperty especialidadProperty() { return especialidad; }
    public double getCalificacion() { return calificacion.get(); }
    public SimpleDoubleProperty calificacionProperty() { return calificacion; }
    public int getOrdenesActivas() { return ordenesActivas.get(); }
    public SimpleIntegerProperty ordenesActivasProperty() { return ordenesActivas; }
    public String getTelefono() { return telefono.get(); }
    public SimpleStringProperty telefonoProperty() { return telefono; }
    public String getDisponibilidad() { return disponibilidad.get(); }
    public SimpleStringProperty disponibilidadProperty() { return disponibilidad; }
}
