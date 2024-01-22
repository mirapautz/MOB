package de.fh_kiel.iue.mob;


//Klasse um globale Variabeln abzuspeichern. Nach dem Singelton DesignPattern programmiert.
public class GlobalVariables {

    private static GlobalVariables instance;

    //Funktion gibt Instanz von GlobalVariables zurück, oder erstellt eine Instanz, wenn noch keine existiert.
    public static GlobalVariables getInstance() {
        if (instance == null)
            instance = new GlobalVariables();
        return instance;
    }

    private GlobalVariables() {
    }

    private boolean simulatedDownload = false; //Beschreibt, ob der simulierte Download gesetzt ist.
    private int maxInfections = 0; //Beschreibt die maximal für den Download zulässige Anzahl Infektionen.
    private boolean maxInfectionsSet = false; //Beschreibt ob die maximale Anzahl Infektionen gesetzt ist.

    //Getter - Setter Methoden.
    public boolean simulatedDownloadStatus() {
        return simulatedDownload;
    }

    public void setSimulatedDownload(Boolean value) {
        simulatedDownload = value;
    }

    public int getMaxInfections() {
        return maxInfections;
    }

    public void setMaxInfections(int maxInfections) {
        this.maxInfections = maxInfections;
    }

    public boolean isMaxInfectionsSet() {
        return maxInfectionsSet;
    }

    public void setMaxInfectionsSet(boolean maxInfectionsSet) {
        this.maxInfectionsSet = maxInfectionsSet;
    }
}
