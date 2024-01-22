package de.fh_kiel.iue.mob;

//Klasse die die Struktur eines Datensatzes beschreibt.
public class Data {

    private String countryName;
    private int value;
    private int newConfirmed;

    //Datensatz benötigt Namen des Landes, Value, und Zahl Neuinfizierte.
    public Data(String countryName, int value, int newConfirmed) {
        this.countryName = countryName;
        this.value = value;
        this.newConfirmed = newConfirmed;
    }

    //Getter & Setter Variabeln.
    public String getCountry() {
        return countryName;
    }

    public void setCountry(String countryName) {
        this.countryName = countryName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNewConfirmed() {
        return this.newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed = newConfirmed;
    }
}