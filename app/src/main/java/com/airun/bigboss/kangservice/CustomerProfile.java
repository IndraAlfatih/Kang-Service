package com.airun.bigboss.kangservice;

public class CustomerProfile {
    public String name, email, phone, alamat;

    public CustomerProfile(String name, String email, String phone, String alamat) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.alamat = alamat;
    }

    public String getNama() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getPhone() {

        return phone;
    }

    public String getAlamat() {

        return alamat;
    }

}
