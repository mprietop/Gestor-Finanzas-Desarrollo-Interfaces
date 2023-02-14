package com.afundacion.gestorfinanzasdesarrollointerfaces.Utils;

public class Transactions {
    private String descripcionHistorial;
    private String cantidadHistorial;
    private String tipoTransaccion;
    private int id;

    public Transactions(String descripcionHistorial, String cantidadHistorial, String tipoTransaccion, int id) {
        this.descripcionHistorial = descripcionHistorial;
        this.cantidadHistorial = cantidadHistorial;
        this.tipoTransaccion = tipoTransaccion;
        this.id = id;
    }

    public String getDescripcionHistorial() {return descripcionHistorial; }

    public String getCantidadHistorial() {
        return cantidadHistorial;
    }

    public String getTipoTransaccion() { return tipoTransaccion; }

    public int getId() { return id; }
}
