package com.example.kgf;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CourtAdapter extends ArrayAdapter<Addcourts> {

    private Activity activity1;
    private List<Addcourts> addcourtsList;

    public CourtAdapter(Activity activity1, List<Addcourts> addcourtsList) {
        super(activity1, R.layout.display_court, addcourtsList);
        this.activity1 = activity1;
        this.addcourtsList = addcourtsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity1.getLayoutInflater();
        View liview = layoutInflater.inflate(R.layout.display_court,null,true);

        TextView corname = liview.findViewById(R.id.courtdis_name);
        TextView corcas = liview.findViewById(R.id.courtcsno);
        TextView cordate = liview.findViewById(R.id.courtdis_date);
        TextView cornum = liview.findViewById(R.id.posi);

        Addcourts addds = addcourtsList.get(position);
        cornum.setText(Integer.toString(position+1)+".");
        corname.setText(addds.getCourtName());
        corcas.setText(addds.getCaseid());
        cordate.setText(addds.getDate());

        return liview;
    }
}
