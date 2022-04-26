package com.example.league_checker;

import java.util.HashMap;
import java.util.Map;

class IconUrlMapper
{
    Map<String,String> tierMap = new HashMap<String,String>();

    String getTierIcon(String tier)
    {
        tierMap.put("IRON", "iron_1.png");
        tierMap.put("BRONZE", "bronze_1.png");
        tierMap.put("SILVER", "silver_1.png");
        tierMap.put("GOLD", "gold_1.png");
        tierMap.put("PLATINUM", "platinum_1.png");
        tierMap.put("DIAMOND", "diamond_1.png");
        tierMap.put("MASTER", "master_1.png");
        tierMap.put("GRANDMASTER", "grandmaster_1.png");
        tierMap.put("CHALLENGER", "challenger_1.png");


        String value = tierMap.get(tier);

        return "https://opgg-static.akamaized.net/images/medals/" + value;
    }
}
