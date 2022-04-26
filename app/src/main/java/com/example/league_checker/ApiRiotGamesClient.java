package com.example.league_checker;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRiotGamesClient {

    static final String API_KEY = "Not for you.";

    @GET("/lol/summoner/v4/summoners/by-name/{user}?")
    Call<ApiSummonerResponse> getSummonerByName(@Path("user") String summonerName, @Query("api_key") String API_KEY);

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}?")
    Call<List<ApiTierResponse>> getTiersBySummonerId(@Path("encryptedSummonerId") String encryptedId, @Query("api_key") String API_KEY);
}
