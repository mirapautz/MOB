package de.fh_kiel.iue.mob;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter
{
    //Klasse die Datensatz und ViewHolder aneinander binden soll.
    private ArrayList<Data> demoDataSet;
    private ButtonListener btListener;

    //Konstruktor bekommt Datensatz Demodataset & Buttonlistener um diesen an jeden ViewHolder weiter zu geben.
    public Adapter(ArrayList<Data> demoDataSet, ButtonListener btListener )
    {
        this.demoDataSet = demoDataSet;
        this.btListener = btListener;
    }

    @Override
    //Methode bestimmt, ob bei Position 0 ein Header ViewHolder, oder ansonsten ein normaler View Holder erstellt werden soll.
    public int getItemViewType(int position){
        if(position == 0){
            return 0;
        }

        return 1;
    }


    @NonNull
    @Override
    //Gibt wenn angefordert einen ViewHolder zurück.
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        //Bei ViewType 0 wird ein Header ViewHolder zurückgegeben. Ansonsten ein normaler ViewHolder (normales Listenelement).
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headerline, parent, false);
            return new ViewHolderHeader(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemline, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    //Wird aufgerufen, wenn ein Datensatz an ein View Element gebunden wird.
    public void onBindViewHolder( @NonNull RecyclerView.ViewHolder holder, int position )
    {
        //Headerview (Position 0) wird nicht an Datensatz gebunden.
        if(position != 0) {
            Log.i("onBindViewHolder", "View Holder has been updated.");
            Data dataAtPos = demoDataSet.get(position-1);
            ViewHolder tempHolder = (ViewHolder) holder;
            tempHolder.getTvLeft().setText(dataAtPos.getCountry());
            tempHolder.getTvRight().setText(String.valueOf(dataAtPos.getValue()));
            //Registriert für jeden ViewHolder die Activity als btListener.
            tempHolder.registerBtListener(this.btListener);
        }
    }

    @Override
    public int getItemCount()
    {
        return demoDataSet.size() +1;
    }

}