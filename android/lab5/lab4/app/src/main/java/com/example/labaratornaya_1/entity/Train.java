package com.example.labaratornaya_1.entity;


import java.io.Serializable;
import java.util.Date;

public class Train implements Serializable {
    private Integer Id;
    private String Destination;
    private String Number;
    private String DateArrive;
    private String Coupe;
    private String Platskart;

    public Train(Integer Id, String destination, String number, String dateArrive, String coupe, String platskart) {
        this.Id = Id;
        this.Destination = destination;
        this.Number = number;
        this.DateArrive = dateArrive;
        this.Coupe = coupe;
        this.Platskart = platskart;
    }

    public Train(String destination, String number) {
        this.Destination = destination;
        this.Number = number;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(int id){
        this.Id = id;
    }

    public String getDestination() {
        return this.Destination;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public String getNumber() {
        return this.Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public String getDate(){
        return this.DateArrive;
    }

    public String getDateArrive() {
        return this.DateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.DateArrive = dateArrive;
    }

    public String getCoupe() {
        return this.Coupe;
    }

    public void setCoupe(String coupe) {
        this.Coupe = coupe;
    }

    public String getPlatskart() {
        return this.Platskart;
    }

    public void setPlatskart(String platskart) {
        this.Platskart = platskart;
    }

    @Override
    public String toString(){
        return "Destination: " + getDestination() + " " +
            "Number: " + getNumber();
    }

    public String getFullInfoString() {
        return "Destination: " + getDestination() + " " +
            "Number: " + getNumber() + " " +
            "DateArrive: " + getDateArrive() + " " +
            "Coupe: " + getCoupe() + " " +
            "Platskart: " + getPlatskart() + " " ;
    }
}
