package com.gostadium.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gostadium.API.Team;
import com.gostadium.R;

/**
 * Classe du ViewHolder pour les favoris
 */
class FavoritesViewHolder extends RecyclerView.ViewHolder{

    private TextView tv_favorites;

    // itemView est la vue correspondante à 1 cellule
    FavoritesViewHolder(View itemView) {
        super(itemView);

        // On initialise les variables des vues
        tv_favorites = itemView.findViewById(R.id.tv_favorite);
    }

    // Puis ajouter une fonction pour remplir la cellule en fonction d'un String
    void bind(Team favorite){
        tv_favorites.setText(favorite.getName());
    }

}