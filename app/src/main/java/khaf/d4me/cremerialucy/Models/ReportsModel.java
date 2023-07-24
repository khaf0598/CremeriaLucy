package khaf.d4me.cremerialucy.Models;

public class ReportsModel {
    private int idReporte;
    private String Reporte;

    public String getReporte() {
        return Reporte;
    }
    public int getIdReporte() {
        return idReporte;
    }

    public void setReporte(String repor) {
        this.Reporte = repor;
    }
    public void setIdReporte(int idrepor) {
        this.idReporte = idrepor;
    }

    public ReportsModel(String rep, int idrep) {
        this.Reporte = rep;
        this.idReporte = idrep;
    }
}
