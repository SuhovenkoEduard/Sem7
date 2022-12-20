package com.example.lab5v1.entity;


import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {

    private Integer Id;
    private String Surname;
    private String Name;
    private Date BornDate;
    private String Faculties;
    private String Group;

    public Student(Integer Id, String surname, String name, Date bornDate, String faculties, String group) {
        this.Id = Id;
        this.Surname = surname;
        this.Name = name;
        this.BornDate = bornDate;
        this.Faculties = faculties;
        this.Group = group;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(int id){
        this.Id = id;
    }

    public String getSurname() {
        return this.Surname;
    }

    public void setSurname(String surname) {
        this.Surname = surname;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Date getDate(){
        return this.BornDate;
    }

    public String getBornDate() {
        return this.BornDate.getYear() + "-" + this.BornDate.getMonth() + "-" + this.BornDate.getDay();
    }

    public void setBornDate(Date bornDate) {
        this.BornDate = bornDate;
    }

    public String getRuDate(){
        return getDate().getDay() + "-" + getDate().getMonth()
                + "-" + getDate().getYear();
    }

    public String getFaculties() {
        return this.Faculties;
    }

    public void setFaculties(String faculties) {
        this.Faculties = faculties;
    }

    public String getGroup() {
        return this.Group;
    }

    public void setGroup(String group) {
        this.Group = group;
    }

    @Override
    public String toString(){
        return "Surname: " + getSurname() + "\n" +
                "Name: " + getName() + "\n";
    }

    public String getFullInfoString(){
        return "Surname: " + getSurname() + "\n" +
                "Name: " + getName() + "\n" +
                "BornDate: " + getRuDate() + " г." + "\n" +
                "Faculties: " + getFaculties() + "\n" +
                "Group: " + getGroup() + "\n" ;
    }
}
