package com.example.logbook2023_3_60_057;

public class ActivityList {
    String id, name, email, phone, dob, present, permanent;

    public ActivityList(String id, String name, String email, String phone, String dob, String present, String permanent) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.present = present;
        this.permanent = permanent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getPresent() {
        return present;
    }

    public String getPermanent() {
        return permanent;
    }
}
