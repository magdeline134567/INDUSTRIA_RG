package besededatos.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Instalacion {

    private final SimpleIntegerProperty idInstalacion;
    private final SimpleStringProperty ordenTrabajo;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty hora;
    private final SimpleStringProperty tecnico;
    private final SimpleStringProperty estado;
    private final SimpleStringProperty notas;

    public Instalacion(String ordenTrabajo, String fecha, String hora,
                       String tecnico, String estado, String notas) {
        this.idInstalacion = new SimpleIntegerProperty(-1);
        this.ordenTrabajo = new SimpleStringProperty(ordenTrabajo);
        this.fecha        = new SimpleStringProperty(fecha);
        this.hora         = new SimpleStringProperty(hora);
        this.tecnico      = new SimpleStringProperty(tecnico);
        this.estado       = new SimpleStringProperty(estado);
        this.notas        = new SimpleStringProperty(notas);
    }

    public Instalacion(String ordenTrabajo, String fecha, String hora,
                       String tecnico, String estado) {
        this(ordenTrabajo, fecha, hora, tecnico, estado, "");
    }

    public int getIdInstalacion() { return idInstalacion.get(); }
    public void setIdInstalacion(int id) { this.idInstalacion.set(id); }
    public SimpleIntegerProperty idInstalacionProperty() { return idInstalacion; }

    public String getOrdenTrabajo() { return ordenTrabajo.get(); }
    public SimpleStringProperty ordenTrabajoProperty() { return ordenTrabajo; }

    public String getFecha() { return fecha.get(); }
    public SimpleStringProperty fechaProperty() { return fecha; }

    public String getHora() { return hora.get(); }
    public SimpleStringProperty horaProperty() { return hora; }

    public String getTecnico() { return tecnico.get(); }
    public SimpleStringProperty tecnicoProperty() { return tecnico; }

    public String getEstado() { return estado.get(); }
    public SimpleStringProperty estadoProperty() { return estado; }

    public String getNotas() { return notas.get(); }
    public SimpleStringProperty notasProperty() { return notas; }
}