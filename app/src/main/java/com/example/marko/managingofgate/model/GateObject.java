package com.example.marko.managingofgate.model;

public class GateObject {

    private int id;
    private String nameObject;
    private String phoneNumber;
    private boolean isFill;

    private int numberObject;

    public GateObject () {}

    public void setNameObject(String nameObject) {this.nameObject = nameObject;}
    public String getNameObject() {return nameObject;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}

    public void setId(int id) {this.id = id;}
    public int getId() {return id;}

    public void setIsFill(boolean isFill) {this.isFill = isFill;}
    public boolean isIsFill() {return isFill;}

    public void setNumberObject(int numberObject) {this.numberObject = numberObject;}
    public int getNumberObject() {return numberObject;}

    @Override
    public String toString() {
        return nameObject;
    }
}
