package besededatos.models;

import besededatos.config.*;
import javafx.beans.property.*;
import java.sql.Timestamp;

public class ClienteCompleto {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty cedula;
    private final SimpleStringProperty telefono;
    private final SimpleStringProperty email;
    private final SimpleStringProperty tipo;
    private final SimpleStringProperty direccion;
    private final SimpleObjectProperty<Timestamp> fechaRegistro;
    private final SimpleDoubleProperty saldo;

    public ClienteCompleto(int id, String nombre, String cedula, String telefono, String email, String tipo, String direccion, Timestamp fechaRegistro) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cedula = new SimpleStringProperty(cedula);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.tipo = new SimpleStringProperty(tipo);
        this.direccion = new SimpleStringProperty(direccion);
        this.fechaRegistro = new SimpleObjectProperty<>(fechaRegistro);
        this.saldo = new SimpleDoubleProperty(0.0);
    }

    // Constructor anterior para compatibilidad si es necesario
    public ClienteCompleto(int id, String nombre, String cedula, String telefono, String email, String tipo, double saldo) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cedula = new SimpleStringProperty(cedula);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.tipo = new SimpleStringProperty(tipo);
        this.direccion = new SimpleStringProperty("");
        this.fechaRegistro = new SimpleObjectProperty<>(null);
        this.saldo = new SimpleDoubleProperty(saldo);
    }

    // Constructor para el nuevo snippet
    public ClienteCompleto(int id, String nombre, String cedula, String telefono, String email, String tipo) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cedula = new SimpleStringProperty(cedula);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.tipo = new SimpleStringProperty(tipo);
        this.direccion = new SimpleStringProperty("");
        this.fechaRegistro = new SimpleObjectProperty<>(null);
        this.saldo = new SimpleDoubleProperty(0.0);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getNombre() { return nombre.get(); }
    public SimpleStringProperty nombreProperty() { return nombre; }
    public String getCedula() { return cedula.get(); }
    public SimpleStringProperty cedulaProperty() { return cedula; }
    public String getTelefono() { return telefono.get(); }
    public SimpleStringProperty telefonoProperty() { return telefono; }
    public String getEmail() { return email.get(); }
    public SimpleStringProperty emailProperty() { return email; }
    public String getTipo() { return tipo.get(); }
    public SimpleStringProperty tipoProperty() { return tipo; }
    public String getDireccion() { return direccion.get(); }
    public SimpleStringProperty direccionProperty() { return direccion; }
    public Timestamp getFechaRegistro() { return fechaRegistro.get(); }
    public SimpleObjectProperty<Timestamp> fechaRegistroProperty() { return fechaRegistro; }
    public double getSaldo() { return saldo.get(); }
    public SimpleDoubleProperty saldoProperty() { return saldo; }
}
