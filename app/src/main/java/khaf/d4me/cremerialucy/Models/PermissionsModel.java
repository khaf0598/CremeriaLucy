package khaf.d4me.cremerialucy.Models;

public class PermissionsModel {
    private int idPermiso;
    private String Permiso;

    public String getPermiso() {
        return Permiso;
    }
    public int getIdPermiso() {
        return idPermiso;
    }

    public void setPermiso(String perm) {
        this.Permiso = perm;
    }
    public void setIdPermiso(int idperm) {
        this.idPermiso = idperm;
    }

    public PermissionsModel(String perm, int idPerm) {
        this.Permiso = perm;
        this.idPermiso = idPerm;
    }
}

