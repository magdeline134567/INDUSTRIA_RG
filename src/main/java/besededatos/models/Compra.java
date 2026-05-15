package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Compra {

    private final SimpleStringProperty codigo;
    private final SimpleStringProperty proveedor;
    private final SimpleStringProperty material;
    private final SimpleStringProperty fechaEntrega;
    private final SimpleDoubleProperty cantidad;
    private final SimpleDoubleProperty precioUnitario;
    private final SimpleDoubleProperty total;
    private final SimpleStringProperty estado;

    public Compra(String codigo, String proveedor, String material,
                  String fechaEntrega, double cantidad,
                  double precioUnitario, double total, String estado) {
        this.codigo         = new SimpleStringProperty(codigo);
        this.proveedor      = new SimpleStringProperty(proveedor);
        this.material       = new SimpleStringProperty(material);
        this.fechaEntrega   = new SimpleStringProperty(fechaEntrega);
        this.cantidad       = new SimpleDoubleProperty(cantidad);
        this.precioUnitario = new SimpleDoubleProperty(precioUnitario);
        this.total          = new SimpleDoubleProperty(total);
        this.estado         = new SimpleStringProperty(estado);
    }


    public SimpleStringProperty codigoProperty()         { return codigo; }
    public SimpleStringProperty proveedorProperty()      { return proveedor; }
    public SimpleStringProperty materialProperty()       { return material; }
    public SimpleStringProperty fechaEntregaProperty()   { return fechaEntrega; }
    public SimpleDoubleProperty cantidadProperty()       { return cantidad; }
    public SimpleDoubleProperty precioUnitarioProperty() { return precioUnitario; }
    public SimpleDoubleProperty totalProperty()          { return total; }
    public SimpleStringProperty estadoProperty()         { return estado; }

    public String getCodigo()         { return codigo.get(); }
    public String getProveedor()      { return proveedor.get(); }
    public String getMaterial()       { return material.get(); }
    public String getFechaEntrega()   { return fechaEntrega.get(); }
    public double getCantidad()       { return cantidad.get(); }
    public double getPrecioUnitario() { return precioUnitario.get(); }
    public double getTotal()          { return total.get(); }
    public String getEstado()         { return estado.get(); }
}