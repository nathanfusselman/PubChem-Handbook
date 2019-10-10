package com.example.pubchem_chemistry_handbook.data;

import java.util.List;

public class SafetyItem {
    String name;
    String url;

    public SafetyItem(String name, String url) {
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
