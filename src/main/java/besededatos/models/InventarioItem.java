package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.*;

public class InventarioItem {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty categoria;
    private final SimpleIntegerProperty cantidad;
    private final SimpleDoubleProperty costoUnitario;
    private final SimpleDoubleProperty precioUnitario;
    private final SimpleStringProperty ubicacion;

    public InventarioItem(int id, String nombre, String categoria, int cantidad, double costoUnitario, double precioUnitario, String ubicacion) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.categoria = new SimpleStringProperty(categoria);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.costoUnitario = new SimpleDoubleProperty(costoUnitario);
        this.precioUnitario = new SimpleDoubleProperty(precioUnitario);
        this.ubicacion = new SimpleStringProperty(ubicacion);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    public String getNombre() { return nombre.get(); }
    public SimpleStringProperty nombreProperty() { return nombre; }
    public String getCategoria() { return categoria.get(); }
    public SimpleStringProperty categoriaProperty() { return categoria; }
    public int getCantidad() { return cantidad.get(); }
    public SimpleIntegerProperty cantidadProperty() { return cantidad; }
    public double getCostoUnitario() { return costoUnitario.get(); }
    public SimpleDoubleProperty costoUnitarioProperty() { return costoUnitario; }
    public double getPrecioUnitario() { return precioUnitario.get(); }
    public SimpleDoubleProperty precioUnitarioProperty() { return precioUnitario; }
    public String getUbicacion() { return ubicacion.get(); }
    public SimpleStringProperty ubicacionProperty() { return ubicacion; }
}
