package com.example.league_checker;

import com.google.gson.annotations.SerializedName;

public class ApiSummonerResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("accountId")
    public String accountId;

    @SerializedName("puuid")
    public String puuid;

    @SerializedName("name")
    public String name;

    @SerializedName("profileIconId")
    public int profileIconId;

    @SerializedName("summonerLevel")
    public int summonerLevel;
}
