package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.*;

public class ConfiguracionItem {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty clave;
    private final SimpleStringProperty valor;
    private final SimpleStringProperty descripcion;
    private final SimpleStringProperty fechaAct;

    public ConfiguracionItem(int id, String clave, String valor, String descripcion, String fechaAct) {
        this.id = new SimpleIntegerProperty(id);
        this.clave = new SimpleStringProperty(clave);
        this.valor = new SimpleStringProperty(valor);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.fechaAct = new SimpleStringProperty(fechaAct);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getClave() { return clave.get(); }
    public SimpleStringProperty claveProperty() { return clave; }
    public String getValor() { return valor.get(); }
    public SimpleStringProperty valorProperty() { return valor; }
    public String getDescripcion() { return descripcion.get(); }
    public SimpleStringProperty descripcionProperty() { return descripcion; }
    public String getFechaAct() { return fechaAct.get(); }
    public SimpleStringProperty fechaActProperty() { return fechaAct; }
}
