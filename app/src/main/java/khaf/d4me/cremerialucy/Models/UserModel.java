package khaf.d4me.cremerialucy.Models;

public class UserModel {
    private int Id_Usuario;
    private String Usuario;
    private String Contrasena;
    private String Nombre;
    private String Apellido_P;
    private String Apellido_M;
    private String Correo;
    private String Telefono;
    private int Tipo_Usuario_Id_Tipo_Usuario;

    public int getIdUsuario() {
        return Id_Usuario;
    }
    public String getUsuario() {
        return Usuario;
    }
    public String getContrasena() {
        return Contrasena;
    }
    public String getNombre() {
        return Nombre;
    }
    public String getApellidoP() {
        return Apellido_P;
    }
    public String getApellidoM() {
        return Apellido_M;
    }
    public String getTelefono() {
        return Telefono;
    }
    public String getCorreo() {
        return Correo;
    }

    public int getTipo() {
        return Tipo_Usuario_Id_Tipo_Usuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.Id_Usuario = idUsuario;
    }
    public void setUsuario(String usuario) {
        this.Usuario = usuario;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
    public void setApellidoP(String apellidoP) {
        this.Apellido_P = apellidoP;
    }
    public void setApellidoM(String apellidoM) {
        this.Apellido_M = apellidoM;
    }
    public void setCorreo(String correo) {
        this.Correo = correo;
    }
    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }
    public void setContrasena(String contra) {
        this.Contrasena = contra;
    }
    public void setTipo(int tipo) {
        this.Tipo_Usuario_Id_Tipo_Usuario = tipo;
    }
}
