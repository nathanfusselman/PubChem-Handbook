package com.example.pubchem_chemistry_handbook.data;


import java.util.ArrayList;
import java.util.List;

public class global {
    private int search_type_startsWith;
    private int search_compound;
    private int results;
    private int safetyItems;
    private List<Element> elements;
    private List<Compound> fav, recents, compounds, compoundListFull;
    private int style;

    public  global(int search_type_startsWith, int results) {
        this.search_type_startsWith = search_type_startsWith;
        search_compound = 0;
        this.results = results;
        fav = new ArrayList<>();
        recents = new ArrayList<>();
        compounds = new ArrayList<>();
        compoundListFull = new ArrayList<>();
        elements = new ArrayList<>();
        safetyItems = 0;
        style = 0;
    }

    public void setSearch_compound(int search_compound) {
        this.search_compound = search_compound;
    }

    public int getSearch_compound() {
        return search_compound;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getSearch_type_startsWith() {
        return search_type_startsWith;
    }

    public void setSearch_type_startsWith(int type) {
        this.search_type_startsWith = type;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public void setSafetyItems(int value) {
        safetyItems = value;
    }

    public int getSafetyItems(){
        return safetyItems;
    }

    public List<Compound> getFav() {
        return fav;
    }

    public List<Compound> getRecents() {
        return recents;
    }

    public List<Compound> getCompounds() {
        return compounds;
    }

    public List<Compound> getCompoundListFull() {
        return compoundListFull;
    }

    public List<Element> getElements() {
        return elements;
    }
}
