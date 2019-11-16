package com.example.pubchem_chemistry_handbook.data;


import java.util.ArrayList;
import java.util.List;

public class global {
    int search_type_startsWith;
    int results;
    int safetyItems;
    List<Element> elements;
    List<Compound> fav, recents, compounds, compoundListFull;

    public  global(int search_type_startsWith, int results) {
        this.search_type_startsWith = search_type_startsWith;
        this.results = results;
        fav = new ArrayList<>();
        recents = new ArrayList<>();
        compounds = new ArrayList<>();
        compoundListFull = new ArrayList<>();
        elements = new ArrayList<>();
        safetyItems = 0;
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

    public int getSafetyItems() {
        return safetyItems;
    }

    public void setSafetyItems(int value) {
        safetyItems = value;
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

    public void addFav(Compound fav) {
        this.fav.add(fav);
    }

    public void addRecent(Compound recent) {
        this.recents.add(recent);
    }

    public void removeFavorite(int id) {
        this.fav.remove(id);
    }

    public void removeRecent(int id) {
        this.recents.remove(id);
    }

    public List<Element> getElements() {
        return elements;
    }
}
