package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private final IntegerProperty idCliente = new SimpleIntegerProperty(0);
    private final StringProperty  nombre    = new SimpleStringProperty("");
    private final StringProperty  telefono  = new SimpleStringProperty("");


    public Cliente(int idCliente, String nombre, String telefono) {
        this.idCliente.set(idCliente);
        this.nombre.set(nombre);
        this.telefono.set(telefono);
    }


    public int getIdCliente()                  { return idCliente.get(); }
    public void setIdCliente(int v)            { idCliente.set(v); }
    public IntegerProperty idClienteProperty() { return idCliente; }


    public String getNombre()              { return nombre.get(); }
    public void   setNombre(String v)      { nombre.set(v); }
    public StringProperty nombreProperty() { return nombre; }


    public String getTelefono()              { return telefono.get(); }
    public void   setTelefono(String v)      { telefono.set(v); }
    public StringProperty telefonoProperty() { return telefono; }

    @Override
    public String toString() {
        return idCliente.get() + " — " + nombre.get();
    }
}