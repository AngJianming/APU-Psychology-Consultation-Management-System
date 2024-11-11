package com.example.psychologyconsultationfinalproject.Model;

public class ConsultationModel {
    private int id;
    private int userId; // Foreign key referencing the user
    private String date;
    private String session;
    private String topic;

    // Constructors
    public ConsultationModel() {
    }

    public ConsultationModel(int id, int userId, String session, String date, String topic) {
        this.id = id;
        this.userId = userId;
        this.session = session;
        this.date = date;
        this.topic = topic;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
