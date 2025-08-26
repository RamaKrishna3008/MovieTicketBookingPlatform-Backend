package com.movieticket.showhunt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "theatreowner_table")
public class TheatreOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, unique = true, length = 30)
    private String contact;

    @Column(nullable = false, length = 60)
    private String bankname;

    @Column(nullable = false, length = 60)
    private String accountnumber;

    @Column(nullable = false, length = 60)
    private String ifsccode;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 60)
    private String city;

    @Column(nullable = false, length = 60)
    private String state;

    @Column(nullable = false, length = 60)
    private String country;

    @Column(nullable = false, length = 255)
    private String theatrename;

    @Column(nullable = false)
    private boolean status=true;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBankname() {
        return bankname;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountnumber() {
        return accountnumber;
    }
    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getIfsccode() {
        return ifsccode;
    }
    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getTheatrename() {
        return theatrename;
    }
    public void setTheatrename(String theatrename) {
        this.theatrename = theatrename;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
