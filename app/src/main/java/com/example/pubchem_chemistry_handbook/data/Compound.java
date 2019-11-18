package com.example.pubchem_chemistry_handbook.data;

import java.util.ArrayList;
import java.util.List;

public class Compound {
    int CID;
    String name,formula, notes;
    List<SafetyItem> safetyItems = new ArrayList<>();
    List<String> nProperties = new ArrayList<>();
    List<String> vProperties = new ArrayList<>();
    List<String> uProperties = new ArrayList<>();

    public Compound(int CID, String name, String formula) {
        this.CID = CID;
        this.name = name;
        this.formula = formula;
        this.notes = "";
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public int getCID() { return CID; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCID(int CID) { this.CID = CID; }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void addSafetyItem(String name, String url) {
        this.safetyItems.add(new SafetyItem(name, url));
    }

    public List<SafetyItem> getSafetyItems() {
        return safetyItems;
    }

    public  List<String> getnProperties() {
        return nProperties;
    }

    public  List<String> getvProperties() {
        return vProperties;
    }

    public  List<String> getuProperties() {
        return uProperties;
    }

}