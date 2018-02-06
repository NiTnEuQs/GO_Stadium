package com.gostadium.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gostadium.API.Fixture;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Fixture> list = new ArrayList<>();

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapter(List<Fixture> list) {
        if (list != null) this.list = list;
        else this.list = new ArrayList<>();
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_match_item, viewGroup,false);
        return new MyViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Fixture fixture = list.get(position);
        myViewHolder.bind(fixture);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}