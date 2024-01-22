package de.fh_kiel.iue.mob;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderHeader extends RecyclerView.ViewHolder
{
    //View Holder für den Header des RecyclerView.
    private TextView headerLeft;
    private TextView headerRight;


    public ViewHolderHeader( @NonNull View itemView )
    {
        super(itemView);
        headerLeft = itemView.findViewById( R.id.tvCountryname);
        headerRight = itemView.findViewById( R.id.tvInfectionsvalue);

    }

}