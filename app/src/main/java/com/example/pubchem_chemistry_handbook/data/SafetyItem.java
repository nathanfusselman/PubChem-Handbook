package com.example.pubchem_chemistry_handbook.data;

public class SafetyItem {
    private String name;
    private String url;

    SafetyItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
