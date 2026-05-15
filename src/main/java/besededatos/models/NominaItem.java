package besededatos.models;

import javafx.beans.property.*;
import java.sql.Date;

public class NominaItem {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty empleado; // Nombre del empleado
    private final SimpleStringProperty puesto;
    private final SimpleDoubleProperty salarioBase;
    private final SimpleDoubleProperty horasExtras;
    private final SimpleDoubleProperty deducciones;
    private final SimpleDoubleProperty salarioNeto;
    
    // Original fields for compatibility if needed
    private SimpleIntegerProperty empleadoId;
    private SimpleObjectProperty<Date> periodo;
    private SimpleDoubleProperty totalHorasExtras;
    private SimpleObjectProperty<Date> fechaPago;
    private SimpleStringProperty estadoNomina;

    // Constructor used in user snippet
    public NominaItem(int id, int empleadoId, String empleado, String puesto, double salarioBase, double horasExtras, double deducciones, double salarioNeto) {
        this.id = new SimpleIntegerProperty(id);
        this.empleadoId = new SimpleIntegerProperty(empleadoId);
        this.empleado = new SimpleStringProperty(empleado);
        this.puesto = new SimpleStringProperty(puesto);
        this.salarioBase = new SimpleDoubleProperty(salarioBase);
        this.horasExtras = new SimpleDoubleProperty(horasExtras);
        this.deducciones = new SimpleDoubleProperty(deducciones);
        this.salarioNeto = new SimpleDoubleProperty(salarioNeto);
    }

    // Original constructor for compatibility
    public NominaItem(int id, int empleadoId, Date periodo, double horasExtras, double salarioBase, 
                     double totalHorasExtras, double deducciones, double salarioNeto, Date fechaPago, String estadoNomina) {
        this.id = new SimpleIntegerProperty(id);
        this.empleadoId = new SimpleIntegerProperty(empleadoId);
        this.empleado = new SimpleStringProperty(String.valueOf(empleadoId));
        this.puesto = new SimpleStringProperty("");
        this.periodo = new SimpleObjectProperty<>(periodo);
        this.horasExtras = new SimpleDoubleProperty(horasExtras);
        this.salarioBase = new SimpleDoubleProperty(salarioBase);
        this.totalHorasExtras = new SimpleDoubleProperty(totalHorasExtras);
        this.deducciones = new SimpleDoubleProperty(deducciones);
        this.salarioNeto = new SimpleDoubleProperty(salarioNeto);
        this.fechaPago = new SimpleObjectProperty<>(fechaPago);
        this.estadoNomina = new SimpleStringProperty(estadoNomina);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }
    
    public String getEmpleado() { return empleado.get(); }
    public SimpleStringProperty empleadoProperty() { return empleado; }
    
    public String getPuesto() { return puesto.get(); }
    public SimpleStringProperty puestoProperty() { return puesto; }
    
    public double getSalarioBase() { return salarioBase.get(); }
    public SimpleDoubleProperty salarioBaseProperty() { return salarioBase; }
    
    public double getHorasExtras() { return horasExtras.get(); }
    public SimpleDoubleProperty horasExtrasProperty() { return horasExtras; }
    
    public double getDeducciones() { return deducciones.get(); }
    public SimpleDoubleProperty deduccionesProperty() { return deducciones; }
    
    public double getSalarioNeto() { return salarioNeto.get(); }
    public SimpleDoubleProperty salarioNetoProperty() { return salarioNeto; }

    // Getters for compatibility fields
    public int getEmpleadoId() { return empleadoId != null ? empleadoId.get() : 0; }
    public Date getPeriodo() { return periodo != null ? periodo.get() : null; }
    public double getTotalHorasExtras() { return totalHorasExtras != null ? totalHorasExtras.get() : 0.0; }
    public Date getFechaPago() { return fechaPago != null ? fechaPago.get() : null; }
    public String getEstadoNomina() { return estadoNomina != null ? estadoNomina.get() : ""; }
}
