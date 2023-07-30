/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LectorTemperatura;

/**
 *
 * @author MSI
 */
public class TemperaturaData {
        private int id;
        private String temperatura;
    private String fechaHora;

    public TemperaturaData(int id, String temperatura, String fechaHora) {
        this.id = id;
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getFechaHora() {
        return fechaHora;
    }
}
