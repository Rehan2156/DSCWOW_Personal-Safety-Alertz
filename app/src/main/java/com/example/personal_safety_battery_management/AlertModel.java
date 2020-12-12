package com.example.personal_safety_battery_management;

public class AlertModel {
    private int id;
    private int battery;
    private String location;
    private String alertMsg,createdAt;
    private String contactName1,contactName2,contactName3;
    private String contactPhone1,contactPhone2,contactPhone3;

    public AlertModel(int id, int battery, String location, String alertMsg, String contactName1, String contactName2, String contactName3, String contactPhone1, String contactPhone2, String contactPhone3) {
        this.id = id;
        this.battery = battery;
        this.location = location;
        this.alertMsg = alertMsg;
        this.contactName1 = contactName1;
        this.contactName2 = contactName2;
        this.contactName3 = contactName3;
        this.contactPhone1 = contactPhone1;
        this.contactPhone2 = contactPhone2;
        this.contactPhone3 = contactPhone3;
    }

    public AlertModel(int id, int battery, String location, String alertMsg,String createdAt, String contactName1, String contactName2, String contactName3, String contactPhone1, String contactPhone2, String contactPhone3) {
        this.id = id;
        this.battery = battery;
        this.location = location;
        this.alertMsg = alertMsg;
        this.createdAt = createdAt;
        this.contactName1 = contactName1;
        this.contactName2 = contactName2;
        this.contactName3 = contactName3;
        this.contactPhone1 = contactPhone1;
        this.contactPhone2 = contactPhone2;
        this.contactPhone3 = contactPhone3;
    }

    @Override
    public String toString() {
        return "AlertModel{" +
                "\nid=" + id +
                ", \nbattery=" + battery +
                ", \nlocation='" + location + '\'' +
                ", \nalertMsg='" + alertMsg + '\'' +
                ", \ncreatedAt='" + createdAt + '\'' +
                ", \ncontactName1='" + contactName1 + '\'' +
                ", \ncontactPhone1='" + contactPhone1 + '\'' +
                ", \ncontactName2='" + contactName2 + '\'' +
                ", \ncontactPhone2='" + contactPhone2 + '\'' +
                ", \ncontactName3='" + contactName3 + '\'' +
                ", \ncontactPhone3='" + contactPhone3 + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2;
    }

    public String getContactName3() {
        return contactName3;
    }

    public void setContactName3(String contactName3) {
        this.contactName3 = contactName3;
    }

    public String getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactPhone3() {
        return contactPhone3;
    }

    public void setContactPhone3(String contactPhone3) {
        this.contactPhone3 = contactPhone3;
    }
}
