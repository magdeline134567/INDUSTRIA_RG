package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.SimpleStringProperty;

public class OrdenTrabajo {

    private final SimpleStringProperty codigo;
    private final SimpleStringProperty cliente;
    private final SimpleStringProperty desglose;
    private final SimpleStringProperty fechaInicio;
    private final SimpleStringProperty fechaEstimada;
    private final SimpleStringProperty estado;
    private final SimpleStringProperty tecnico;
    private final SimpleStringProperty observaciones;


    public OrdenTrabajo(String codigo, String cliente, String desglose,
                        String fechaInicio, String fechaEstimada,
                        String estado, String tecnico, String observaciones) {
        this.codigo        = new SimpleStringProperty(codigo);
        this.cliente       = new SimpleStringProperty(cliente);
        this.desglose      = new SimpleStringProperty(desglose);
        this.fechaInicio   = new SimpleStringProperty(fechaInicio);
        this.fechaEstimada = new SimpleStringProperty(fechaEstimada);
        this.estado        = new SimpleStringProperty(estado);
        this.tecnico       = new SimpleStringProperty(tecnico);
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    // Constructor con 7 parámetros (sin observaciones)
    public OrdenTrabajo(String codigo, String cliente, String desglose,
                        String fechaInicio, String fechaEstimada,
                        String estado, String tecnico) {
        this(codigo, cliente, desglose, fechaInicio, fechaEstimada, estado, tecnico, "");
    }

    public SimpleStringProperty codigoProperty()        { return codigo; }
    public SimpleStringProperty clienteProperty()       { return cliente; }
    public SimpleStringProperty desgloseProperty()      { return desglose; }
    public SimpleStringProperty fechaInicioProperty()   { return fechaInicio; }
    public SimpleStringProperty fechaEstimadaProperty() { return fechaEstimada; }
    public SimpleStringProperty estadoProperty()        { return estado; }
    public SimpleStringProperty tecnicoProperty()       { return tecnico; }
    public SimpleStringProperty observacionesProperty() { return observaciones; }

    public String getCodigo()        { return codigo.get(); }
    public String getCliente()       { return cliente.get(); }
    public String getDesglose()      { return desglose.get(); }
    public String getFechaInicio()   { return fechaInicio.get(); }
    public String getFechaEstimada() { return fechaEstimada.get(); }
    public String getEstado()        { return estado.get(); }
    public String getTecnico()       { return tecnico.get(); }
    public String getObservaciones() { return observaciones.get(); }
}