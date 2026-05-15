package besededatos.models;

import besededatos.config.*;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Desglose {

    private final SimpleStringProperty codigo;
    private final SimpleStringProperty cliente;
    private final SimpleStringProperty tipoVentana;
    private final SimpleDoubleProperty ancho;
    private final SimpleDoubleProperty alto;
    private final SimpleStringProperty tipoPerfil;
    private final SimpleStringProperty grosorMm;
    private final SimpleStringProperty tipoVidrio;
    private final SimpleStringProperty espesorMm;
    private final SimpleStringProperty herrajes;
    private final SimpleStringProperty sellador;
    private final SimpleStringProperty acabado;
    private final SimpleIntegerProperty cantHojas;
    private final SimpleDoubleProperty costoMateriales;
    private final SimpleDoubleProperty manoObra;
    private final SimpleDoubleProperty gastosIndirectos;
    private final SimpleDoubleProperty precioVenta;


    public Desglose(String codigo, String cliente, String tipoVentana,
                    double ancho, double alto,
                    String tipoPerfil, String grosorMm,
                    String tipoVidrio, String espesorMm,
                    String herrajes, String sellador, String acabado,
                    int cantHojas,
                    double costoMateriales, double manoObra,
                    double gastosIndirectos, double precioVenta) {
        this.codigo           = new SimpleStringProperty(codigo);
        this.cliente          = new SimpleStringProperty(cliente);
        this.tipoVentana      = new SimpleStringProperty(tipoVentana);
        this.ancho            = new SimpleDoubleProperty(ancho);
        this.alto             = new SimpleDoubleProperty(alto);
        this.tipoPerfil       = new SimpleStringProperty(tipoPerfil);
        this.grosorMm         = new SimpleStringProperty(grosorMm);
        this.tipoVidrio       = new SimpleStringProperty(tipoVidrio);
        this.espesorMm        = new SimpleStringProperty(espesorMm);
        this.herrajes         = new SimpleStringProperty(herrajes);
        this.sellador         = new SimpleStringProperty(sellador);
        this.acabado          = new SimpleStringProperty(acabado);
        this.cantHojas        = new SimpleIntegerProperty(cantHojas);
        this.costoMateriales  = new SimpleDoubleProperty(costoMateriales);
        this.manoObra         = new SimpleDoubleProperty(manoObra);
        this.gastosIndirectos = new SimpleDoubleProperty(gastosIndirectos);
        this.precioVenta      = new SimpleDoubleProperty(precioVenta);
    }

    // Constructor adicional con 6 parámetros (para la vista de tabla)
    public Desglose(String codigo, String cliente, String tipoVentana,
                    double ancho, double alto, double precioVenta) {
        this(codigo, cliente, tipoVentana, ancho, alto,
                "", "", "", "", "", "", "", 0, 0.0, 0.0, 0.0, precioVenta);
    }

    public SimpleStringProperty  codigoProperty()           { return codigo; }
    public SimpleStringProperty  clienteProperty()          { return cliente; }
    public SimpleStringProperty  tipoVentanaProperty()      { return tipoVentana; }
    public SimpleDoubleProperty  anchoProperty()            { return ancho; }
    public SimpleDoubleProperty  altoProperty()             { return alto; }
    public SimpleStringProperty  tipoPerfilProperty()       { return tipoPerfil; }
    public SimpleStringProperty  grosorMmProperty()         { return grosorMm; }
    public SimpleStringProperty  tipoVidrioProperty()       { return tipoVidrio; }
    public SimpleStringProperty  espesorMmProperty()        { return espesorMm; }
    public SimpleStringProperty  herrajesProperty()         { return herrajes; }
    public SimpleStringProperty  selladorProperty()         { return sellador; }
    public SimpleStringProperty  acabadoProperty()          { return acabado; }
    public SimpleIntegerProperty cantHojasProperty()        { return cantHojas; }
    public SimpleDoubleProperty  costoMaterialesProperty()  { return costoMateriales; }
    public SimpleDoubleProperty  manoObraProperty()         { return manoObra; }
    public SimpleDoubleProperty  gastosIndirectosProperty() { return gastosIndirectos; }
    public SimpleDoubleProperty  precioVentaProperty()      { return precioVenta; }

    public String  getCodigo()           { return codigo.get(); }
    public String  getCliente()          { return cliente.get(); }
    public String  getTipoVentana()      { return tipoVentana.get(); }
    public double  getAncho()            { return ancho.get(); }
    public double  getAlto()             { return alto.get(); }
    public String  getTipoPerfil()       { return tipoPerfil.get(); }
    public String  getGrosorMm()         { return grosorMm.get(); }
    public String  getTipoVidrio()       { return tipoVidrio.get(); }
    public String  getEspesorMm()        { return espesorMm.get(); }
    public String  getHerrajes()         { return herrajes.get(); }
    public String  getSellador()         { return sellador.get(); }
    public String  getAcabado()          { return acabado.get(); }
    public int     getCantHojas()        { return cantHojas.get(); }
    public double  getCostoMateriales()  { return costoMateriales.get(); }
    public double  getManoObra()         { return manoObra.get(); }
    public double  getGastosIndirectos() { return gastosIndirectos.get(); }
    public double  getPrecioVenta()      { return precioVenta.get(); }
}