package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.*;

public class Usuario {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty nombreCompleto;
    private final SimpleStringProperty email;
    private final SimpleStringProperty rol;
    private final SimpleStringProperty departamento;
    private final SimpleStringProperty ultimoAcceso;
    private final SimpleStringProperty estado;

    public Usuario(int id, String username, String nombreCompleto, String email, String rol, String departamento, String ultimoAcceso, String estado) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.nombreCompleto = new SimpleStringProperty(nombreCompleto);
        this.email = new SimpleStringProperty(email);
        this.rol = new SimpleStringProperty(rol);
        this.departamento = new SimpleStringProperty(departamento);
        this.ultimoAcceso = new SimpleStringProperty(ultimoAcceso);
        this.estado = new SimpleStringProperty(estado);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getUsername() { return username.get(); }
    public SimpleStringProperty usernameProperty() { return username; }
    public String getNombreCompleto() { return nombreCompleto.get(); }
    public SimpleStringProperty nombreCompletoProperty() { return nombreCompleto; }
    public String getEmail() { return email.get(); }
    public SimpleStringProperty emailProperty() { return email; }
    public String getRol() { return rol.get(); }
    public SimpleStringProperty rolProperty() { return rol; }
    public String getDepartamento() { return departamento.get(); }
    public SimpleStringProperty departamentoProperty() { return departamento; }
    public String getUltimoAcceso() { return ultimoAcceso.get(); }
    public SimpleStringProperty ultimoAccesoProperty() { return ultimoAcceso; }
    public String getEstado() { return estado.get(); }
    public SimpleStringProperty estadoProperty() { return estado; }
}
