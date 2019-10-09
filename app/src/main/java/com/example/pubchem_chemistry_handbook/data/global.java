package com.example.pubchem_chemistry_handbook.data;

public class global {
    int search_type_exact;
    int results;

    public  global(int search_type_exact, int results) {
        this.search_type_exact = search_type_exact;
        this.results = results;
    }

    public int getSearch_type_exact() {
        return search_type_exact;
    }

    public void setSearch_type_exact(int type) {
        this.search_type_exact = type;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }
}
