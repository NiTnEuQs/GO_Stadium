package com.gostadium.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gostadium.API.Team;
import com.gostadium.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de l'adapter pour les favoris
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    // Liste utilisé dans l'adapter. Permet de préciser le type de la liste
    private List<Team> list = new ArrayList<>();

    // Ajouter un constructeur prenant en entrée une liste
    public FavoritesAdapter(List<Team> list) {
        if (list != null) this.list = list;
        else this.list = new ArrayList<>();
    }

    /*
      Cette fonction permet de créer les viewHolder
      et par la même occasion, indiquer la vue à inflater (à partir des layout xml)
    */
    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_favorite_item, viewGroup,false);
        return new FavoritesViewHolder(view);
    }

    // C'est ici que nous allons remplir notre cellule avec la Team de chaque objet
    @Override
    public void onBindViewHolder(FavoritesViewHolder viewHolder, int position) {
        Team team = list.get(position);
        viewHolder.bind(team);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}