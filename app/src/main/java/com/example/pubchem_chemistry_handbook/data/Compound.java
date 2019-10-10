package com.example.pubchem_chemistry_handbook.data;

import java.util.ArrayList;
import java.util.List;

public class Compound {
    int eid;
    String name,formula;
    List<SafetyItem> safetyItems = new ArrayList<>();

    public Compound(int eid, String name, String formula) {
        this.eid = eid;
        this.name = name;
        this.formula = formula;
    }

    public String getName() {
        return name;
    }

    public int getEID() { return eid; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEID(int eid) { this.eid = eid; }

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
}