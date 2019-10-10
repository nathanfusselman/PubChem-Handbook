package com.example.pubchem_chemistry_handbook.data;


public class global {
    int search_type_startsWith;
    int results;

    public  global(int search_type_startsWith, int results) {
        this.search_type_startsWith = search_type_startsWith;
        this.results = results;
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
}
