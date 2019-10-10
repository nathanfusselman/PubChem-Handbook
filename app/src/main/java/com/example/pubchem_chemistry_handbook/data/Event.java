package com.example.pubchem_chemistry_handbook.data;

import java.net.URL;

public class Event {
    String title;
    String description;
    String link;

    public Event(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link=link;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) { this.link = link; }
}
