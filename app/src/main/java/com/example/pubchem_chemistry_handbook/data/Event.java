package com.example.pubchem_chemistry_handbook.data;


public class Event {
    private String title;
    private String description;
    private String link;
    private String date;

    public Event(){}

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) { this.link = link; }
}
