package com.example.pubchem_chemistry_handbook.data;

public class global {
    int search_type;

    public  global(int search_type) {
        this.search_type = search_type;
    }

    public int getSearch_type() {
        return search_type;
    }

    public void setSearch_type(int type) {
        this.search_type = type;
    }
}
