package com.example.psychologyconsultationfinalproject.Model;

public class UserModel {
    Integer id;
    String fullName, email, password, gender, batch;


    public UserModel(Integer id, String fullName, String password, String batch) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.batch = batch;
    }

    // Add a default constructor
    public UserModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
