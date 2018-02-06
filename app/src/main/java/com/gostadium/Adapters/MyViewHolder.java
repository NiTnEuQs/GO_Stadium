package com.gostadium.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gostadium.API.Fixture;
import com.gostadium.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView tv_result_home_team;
    private TextView tv_result;
    private TextView tv_result_away_team;
    //private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        tv_result_home_team = itemView.findViewById(R.id.tv_result_home_team);
        tv_result = itemView.findViewById(R.id.tv_result);
        tv_result_away_team = itemView.findViewById(R.id.tv_result_away_team);
        //imageView = itemView.findViewById(R.id.image);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un String
    public void bind(Fixture fixture){
        tv_result_home_team.setText(fixture.getHomeTeamName());
        tv_result.setText(
                fixture.getStatus().equals("FINISHED")
                ? fixture.getResult().getGoalsHomeTeam() + " - " + fixture.getResult().getGoalsAwayTeam()
                : " - "
        );
        tv_result_away_team.setText(fixture.getAwayTeamName());
        //Picasso.with(imageView.getContext()).load(myObject.getImageUrl()).centerCrop().fit().into(imageView);
    }

}