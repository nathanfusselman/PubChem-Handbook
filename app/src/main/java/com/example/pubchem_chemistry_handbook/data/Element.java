package com.example.pubchem_chemistry_handbook.data;

public class Element {
    int AtomicNumber;
    String Symbol;
    String Name;
    String ChemicalGroupBlock;

    public Element(int AN, String S, String N, String CGB) {
        AtomicNumber = AN;
        Symbol = S;
        Name = N;
        ChemicalGroupBlock = CGB;
    }

    public int getAtomicNumber() {
        return AtomicNumber;
    }

    public String getChemicalGroupBlock() {
        return ChemicalGroupBlock;
    }

    public String getName() {
        return Name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setAtomicNumber(int atomicNumber) {
        AtomicNumber = atomicNumber;
    }

    public void setChemicalGroupBlock(String chemicalGroupBlock) {
        ChemicalGroupBlock = chemicalGroupBlock;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}