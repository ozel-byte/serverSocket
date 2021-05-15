package sample.model;

import javafx.collections.ObservableArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 65296850982101010L;
    private String name;
    private Integer puntos;
    private Double posicionlineStartx;
    private Double posicionlineStarty;
    private Double posicionlineendx;
    private Double posicionlineendy;
    private Double layoutyCirculo;
    private Double layoutxCirculo;
    private ArrayList<String> listamjs = new ArrayList<String>();


    public Cliente(String _name, Integer _points) {
        this.name = _name;
        this.puntos = _points;
    }

    public String getName() {
        return name;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void agregarMsj(String msj){
        listamjs.add(msj);
    }
    public ArrayList getlistamsj(){
        return this.listamjs;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Double getPosicionlineStartx() {
        return posicionlineStartx;
    }

    public Double getPosicionlineStarty() {
        return posicionlineStarty;
    }

    public Double getPosicionlineendx() {
        return posicionlineendx;
    }

    public Double getPosicionlineendy() {
        return posicionlineendy;
    }

    public Double getLayoutyCirculo() {
        return layoutyCirculo;
    }

    public Double getLayoutxCirculo() {
        return layoutxCirculo;
    }

    public ArrayList<String> getListamjs() {
        return listamjs;
    }
}
