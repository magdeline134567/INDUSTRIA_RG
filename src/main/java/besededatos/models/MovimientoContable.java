package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.*;

public class MovimientoContable {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty tipo;
    private final SimpleStringProperty subTipo;
    private final SimpleStringProperty descripcion;
    private final SimpleDoubleProperty monto;

    public MovimientoContable(int id, String fecha, String tipo, String subTipo, String descripcion, double monto) {
        this.id = new SimpleIntegerProperty(id);
        this.fecha = new SimpleStringProperty(fecha);
        this.tipo = new SimpleStringProperty(tipo);
        this.subTipo = new SimpleStringProperty(subTipo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.monto = new SimpleDoubleProperty(monto);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getFecha() { return fecha.get(); }
    public SimpleStringProperty fechaProperty() { return fecha; }
    public String getTipo() { return tipo.get(); }
    public SimpleStringProperty tipoProperty() { return tipo; }
    public String getSubTipo() { return subTipo.get(); }
    public SimpleStringProperty subTipoProperty() { return subTipo; }
    public String getDescripcion() { return descripcion.get(); }
    public SimpleStringProperty descripcionProperty() { return descripcion; }
    public double getMonto() { return monto.get(); }
    public SimpleDoubleProperty montoProperty() { return monto; }
}
