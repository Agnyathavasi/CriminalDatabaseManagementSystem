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

public class CriminalAdapter extends ArrayAdapter<New_criminals> {

    private Activity Acti;
    private List<New_criminals> newcriminals;

    public CriminalAdapter(Activity act,List<New_criminals> lis){
        super(act,R.layout.display_criminal,lis);
        this.Acti = act;
        this.newcriminals = lis;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = Acti.getLayoutInflater();
        View lv = inf.inflate(R.layout.display_criminal,null,true);

        TextView crimfir = (TextView) lv.findViewById(R.id.crfir);
        TextView crimname = (TextView) lv.findViewById(R.id.crname);
        TextView crimpun = (TextView) lv.findViewById(R.id.crpunishment);
        TextView crimdoa = (TextView) lv.findViewById(R.id.crdoa);
        TextView crimheig = (TextView) lv.findViewById(R.id.crheight);
        TextView crimsex = (TextView) lv.findViewById(R.id.crsex);
        TextView crimcapt = (TextView) lv.findViewById(R.id.crcaptby);
        ImageView crimpic = (ImageView) lv.findViewById(R.id.crpic);

        New_criminals new_riminals = newcriminals.get(position);
        crimfir.setText(new_riminals.getCase_Number());
        crimname.setText(new_riminals.getCriminal_Name());
        crimpun.setText(new_riminals.getPunishment());
        crimdoa.setText(new_riminals.getCdate());
        crimheig.setText(new_riminals.getHeight());
        crimsex.setText(new_riminals.getSex());
        crimcapt.setText(new_riminals.getCaptured_by());
        String url = new_riminals.getCrimImgurl();
        Picasso.with(Acti).load(url).into(crimpic);


        return lv;
    }
}



