package com.gostadium.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gostadium.API.Fixture;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de l'adapter pour les matchs (fixtures)
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {

    // Liste utilisé dans l'adapter. Permet de préciser le type de la liste
    private List<Fixture> list = new ArrayList<>();

    // Ajouter un constructeur prenant en entrée une liste
    public MatchAdapter(List<Fixture> list) {
        if (list != null) this.list = list;
        else this.list = new ArrayList<>();
    }

    /*
      Cette fonction permet de créer les viewHolder
      et par la même occasion, indiquer la vue à inflater (à partir des layout xml)
    */
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_match_item, viewGroup,false);
        return new MatchViewHolder(view);
    }

    // C'est ici que nous allons remplir notre cellule avec la Fixture de chaque objet
    @Override
    public void onBindViewHolder(MatchViewHolder viewHolder, int position) {
        Fixture fixture = list.get(position);
        viewHolder.bind(fixture);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}