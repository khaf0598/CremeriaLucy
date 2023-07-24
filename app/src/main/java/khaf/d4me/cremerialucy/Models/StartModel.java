package khaf.d4me.cremerialucy.Models;

public class StartModel {
    private String title;
    private int imgid;
    private int tamano;
    private int tamanoTexto;
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

    public StartModel(String title, int imgid, int tam, int tamTex) {
        this.title = title;
        this.imgid = imgid;
        this.tamano = tam;
        this.tamanoTexto = tamTex;
    }
}
