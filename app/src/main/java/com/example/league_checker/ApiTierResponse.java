package com.example.league_checker;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiTierResponse {

    @SerializedName("leagueId")
    public String leagueId;

    @SerializedName("queueType")
    public String queueType;

    @SerializedName("tier")
    public String tier;

    @SerializedName("rank")
    public String rank;

    @SerializedName("summonerId")
    public String summonerId;

    @SerializedName("summonerName")
    public String summonerName;

    @SerializedName("leaguePoints")
    public int leaguePoints;

    @SerializedName("wins")
    public int wins;

    @SerializedName("losses")
    public int losses;

    @SerializedName("veteran")
    public boolean veteran;

    @SerializedName("inactive")
    public boolean inactive;

    @SerializedName("freshBlood")
    public boolean freshBlood;

    @SerializedName("hotStreak")
    public boolean hotStreak;
}
