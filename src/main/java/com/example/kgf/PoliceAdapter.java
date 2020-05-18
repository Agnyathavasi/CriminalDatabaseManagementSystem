package com.example.kgf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PoliceAdapter extends ArrayAdapter<PoliceDB> {

    Activity act2;
    List<PoliceDB> policeDBS;

    public PoliceAdapter (Activity peas, List<PoliceDB> pol){
        super(peas,R.layout.display_police,pol);
        this.act2 = peas;
        this.policeDBS = pol;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf2 = act2.getLayoutInflater();
        View lv2 = inf2.inflate(R.layout.display_police,null,true);

        TextView polname = (TextView) lv2.findViewById(R.id.offiname);
        TextView polid = (TextView) lv2.findViewById(R.id.offiid);
        TextView polarea = (TextView) lv2.findViewById(R.id.offiarea);
        TextView poldob = (TextView) lv2.findViewById(R.id.offidob);
        TextView polph = (TextView) lv2.findViewById(R.id.offimobile);
        TextView poldoj = (TextView) lv2.findViewById(R.id.offidoj);
        ImageView polpic = (ImageView) lv2.findViewById(R.id.offipic);

        PoliceDB poli = policeDBS.get(position);
        polname.setText(poli.getOffname());
        polid.setText(poli.getOffid());
        polarea.setText(poli.getOffarea());
        poldob.setText(poli.getOffdob());
        polph.setText(poli.getOffmobile());
        poldoj.setText(poli.getOffduty());

        String url2 = poli.getOffimgurl();
        Picasso.with(act2).load(url2).into(polpic);


        return lv2;
    }
}
