package khaf.d4me.cremerialucy.Models;

import android.content.Context;

public class StartModel {
    private int imgid;
    private int tamano;
    private int tamanoTexto;
    private String paginaIr, title,rutaImagen;
    private int TipoAccion;

    public String getImagen() {
        return rutaImagen;
    }

    public void setImagen(String rutIm) {
        this.rutaImagen = rutIm;
    }

    public int getTipoAccion() {
        return TipoAccion;
    }

    public void setTipoAccion(int tipA) {
        this.TipoAccion = tipA;
    }

    public String getPagina() {
        return paginaIr;
    }

    public void setPagina(String pag) {
        this.paginaIr = pag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getTamano() {
        return tamano;
    }

    public int getTamanoTexto() {
        return tamanoTexto;
    }

    public StartModel(String title, int imgid, int tam, int tamTex, String cla, int tipAc, String ruti) {
        this.title = title;
        this.imgid = imgid;
        this.tamano = tam;
        this.tamanoTexto = tamTex;
        this.paginaIr = cla;
        this.TipoAccion = tipAc;
        this.rutaImagen = ruti;
    }
}
