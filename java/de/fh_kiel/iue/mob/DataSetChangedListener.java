package de.fh_kiel.iue.mob;

//Interface für Klassen die abhängig von Änderung des Datensatzes sind.
public interface DataSetChangedListener {

    //Wird aufgerufen, wenn sich der Datensatz geändert hat.
    void DataChanged(long position, boolean finishedChange);

}
