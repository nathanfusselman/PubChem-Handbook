package com.example.pubchem_chemistry_handbook.data;

public class Element {
    int AtomicNumber;
    String Symbol;
    String Name;
    String ChemicalGroupBlock;
    double AtomicMass;
    String ElectronConfiguration;
    double Electronegativity;
    int AtomicRadius;
    double IonizationEnergy;
    double ElectronAffinity;
    String OxidationStates;
    String StandardState;
    double MeltingPoint;
    double BoilingPoint;
    double Density;
    String CPKHexColor;
    String YearDiscoverd;

    public Element(int atomicNumber, String symbol, String name, String chemicalGroupBlock, double atomicMass, String electronConfiguration, double electronegativity, int atomicRadius, double ionizationEnergy, double electronAffinity, String oxidationStates, String standardState, double meltingPoint, double boilingPoint, double density, String CPKHexColor, String yearDiscoverd) {
        this.AtomicNumber = atomicNumber;
        this.Symbol = symbol;
        this.Name = name;
        this.ChemicalGroupBlock = chemicalGroupBlock;
        this.AtomicMass = atomicMass;
        this.ElectronConfiguration = electronConfiguration;
        this.Electronegativity = electronegativity;
        this.AtomicRadius = atomicRadius;
        this.IonizationEnergy = ionizationEnergy;
        this.ElectronAffinity = electronAffinity;
        this.OxidationStates = oxidationStates;
        this.StandardState = standardState;
        this.MeltingPoint = meltingPoint;
        this.BoilingPoint = boilingPoint;
        this.Density = density;
        this.CPKHexColor = CPKHexColor;
        this.YearDiscoverd = yearDiscoverd;
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

    public double getAtomicMass() {
        return AtomicMass;
    }

    public double getBoilingPoint() {
        return BoilingPoint;
    }

    public double getDensity() {
        return Density;
    }

    public double getElectronAffinity() {
        return ElectronAffinity;
    }

    public double getElectronegativity() {
        return Electronegativity;
    }

    public double getIonizationEnergy() {
        return IonizationEnergy;
    }

    public double getMeltingPoint() {
        return MeltingPoint;
    }

    public int getAtomicRadius() {
        return AtomicRadius;
    }

    public String getElectronConfiguration() {
        return ElectronConfiguration;
    }

    public String getCPKHexColor() {
        return CPKHexColor;
    }

    public String getOxidationStates() {
        return OxidationStates;
    }

    public String getStandardState() {
        return StandardState;
    }

    public String getYearDiscoverd() {
        return YearDiscoverd;
    }

    public void setAtomicRadius(int atomicRadius) {
        AtomicRadius = atomicRadius;
    }

    public void setBoilingPoint(double boilingPoint) {
        BoilingPoint = boilingPoint;
    }

    public void setCPKHexColor(String CPKHexColor) {
        this.CPKHexColor = CPKHexColor;
    }

    public void setDensity(double density) {
        Density = density;
    }

    public void setElectronAffinity(double electronAffinity) {
        ElectronAffinity = electronAffinity;
    }

    public void setElectronConfiguration(String electronConfiguration) {
        ElectronConfiguration = electronConfiguration;
    }

    public void setElectronegativity(double electronegativity) {
        Electronegativity = electronegativity;
    }

    public void setIonizationEnergy(double ionizationEnergy) {
        IonizationEnergy = ionizationEnergy;
    }

    public void setMeltingPoint(double meltingPoint) {
        MeltingPoint = meltingPoint;
    }

    public void setOxidationStates(String oxidationStates) {
        OxidationStates = oxidationStates;
    }

    public void setStandardState(String standardState) {
        StandardState = standardState;
    }

    public void setYearDiscoverd(String yearDiscoverd) {
        YearDiscoverd = yearDiscoverd;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.AtomicNumber = atomicNumber;
    }

    public void setChemicalGroupBlock(String chemicalGroupBlock) {
        this.ChemicalGroupBlock = chemicalGroupBlock;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setSymbol(String symbol) {
        this.Symbol = symbol;
    }

    public void setAtomicMass(double atomicMass) {
        this.AtomicMass = atomicMass;
    }
}