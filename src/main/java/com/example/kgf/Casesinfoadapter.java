package com.example.kgf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Casesinfoadapter extends ArrayAdapter<Case> {

    private Activity context;
    private List<Case> caseslist;

    Casesinfoadapter(Activity context, List<Case> caseslist)
    {
        super(context,R.layout.display_cases,caseslist);
        this.context = context;
        this.caseslist = caseslist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listview = inflater.inflate(R.layout.display_cases,null,true);

        TextView casefir = listview.findViewById(R.id.casefir);
        TextView caseclaimant = listview.findViewById(R.id.caseclaimant);
        TextView casemobile = listview.findViewById(R.id.casemobile);
        TextView caseoccupation = listview.findViewById(R.id.caseoccupation);
        TextView casecmpltype = listview.findViewById(R.id.casecmpltype);
        TextView casedate = listview.findViewById(R.id.casedate);
        TextView caseaddress = listview.findViewById(R.id.caseaddress);
        TextView casestatement = listview.findViewById(R.id.casestatement);

        Case complns = caseslist.get(position);
        casefir.setText(complns.getFir());
        caseclaimant.setText(complns.getName());
        casemobile.setText(complns.getMobile());
        caseaddress.setText(complns.getAddress());
        casecmpltype.setText(complns.getComplainttype());
        casedate.setText(complns.getDate());
        caseoccupation.setText(complns.getOccupation());
        casestatement.setText(complns.getStatement());

        return listview;

    }
}
