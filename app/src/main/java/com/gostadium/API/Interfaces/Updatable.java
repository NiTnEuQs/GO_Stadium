package com.gostadium.API.Interfaces;

import retrofit2.Response;

/**
 * L'interface permettant de mettre à jour
 * Elle est surtout utilisé pour pouvoir faire une action une fois la réponse d'une requête acquise
 */
public interface Updatable {

    void update(Response response);

}
