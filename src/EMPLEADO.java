public class EMPLEADO {

    private String cedula;
    private String nombre;
    private double salarioBase;
    private String tipo;
    private double bonificacion;

    public EMPLEADO(String cedula, String nombre, double salarioBase,
                    String tipo, double bonificacion) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
        this.tipo = tipo;
        this.bonificacion = bonificacion;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public String getTipo() {
        return tipo;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public double getSalarioTotal() {
        return salarioBase + bonificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }
}
