package com.example.league_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etSummonerName;
    private TextView tvSummonerName;
    private ImageView ivSummonerIcon;

    private TextView tvSoloqTier;
    private ImageView ivSoloqTierIcon;
    private TextView tvSoloqInfo;

    private TextView tvFlexqTier;
    private ImageView ivFlexqTierIcon;
    private TextView tvFlexqInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSummonerName = findViewById(R.id.etSummonerName);
        tvSummonerName = findViewById(R.id.tvSummonerName);
        ivSummonerIcon = findViewById(R.id.ivSummonerIcon);

        tvSoloqTier     = findViewById(R.id.tvSoloqTier);
        ivSoloqTierIcon = findViewById(R.id.ivSoloqTierIcon);
        tvSoloqInfo     = findViewById(R.id.tvSoloqInfo);

        tvFlexqTier     = findViewById(R.id.tvFlexqTier);
        ivFlexqTierIcon = findViewById(R.id.ivFlexqTierIcon);
        tvFlexqInfo     = findViewById(R.id.tvFlexqInfo);
    }

    public void buttonSearchForSummonerClicked(View view) {
        System.out.println("[Info] Button Search For Summoner was clicked.\n");
        System.out.println("[Info] Looking for Summoner name with name: " + etSummonerName.getText());

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://eun1.api.riotgames.com")
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();

        ApiRiotGamesClient client = retrofit.create(ApiRiotGamesClient.class);
        Call<ApiSummonerResponse> summonerProfileCall = client.getSummonerByName(etSummonerName.getText().toString(), ApiRiotGamesClient.API_KEY);

        summonerProfileCall.enqueue(new Callback<ApiSummonerResponse>() {

            @Override
            public void onResponse(Call<ApiSummonerResponse> summonerProfileCall, Response<ApiSummonerResponse> response) {
                System.out.println("[INFO] Got the response for player");

                ApiSummonerResponse body = response.body();

                if (response.code() == 200)
                {
                    Toast.makeText(getApplicationContext(), "Found summoner with name: " + response.body().name, Toast.LENGTH_SHORT).show();

                    tvSummonerName.setVisibility(View.VISIBLE);
                    tvSummonerName.setText(response.body().name);

                    String summonerIconUrl = String.format("https://ddragon.leagueoflegends.com/cdn/12.7.1/img/profileicon/%s.png",
                                                            body.profileIconId);

                    Picasso.get().load(summonerIconUrl).into(ivSummonerIcon);

                    Call<List<ApiTierResponse>> summonerTierCall = client.getTiersBySummonerId(response.body().id.toString(), ApiRiotGamesClient.API_KEY);
                    summonerTierCall.enqueue(new Callback<List<ApiTierResponse>>() {
                        @Override
                        public void onResponse(Call<List<ApiTierResponse>> call, Response<List<ApiTierResponse>> response) {
                            System.out.println("[INFO] Got response from tier api.");

                            IconUrlMapper iconUrlMapper = new IconUrlMapper();
                            final String rankedSolo5x5 = "RANKED_SOLO_5x5";

                            List<ApiTierResponse> res = response.body();

                            for (ApiTierResponse r : res)
                            {
                                if (r.queueType.equals("RANKED_SOLO_5x5"))
                                {
                                    tvSoloqTier.setText(r.tier + " " + r.rank);
                                    String tierIconUrl = iconUrlMapper.getTierIcon(r.tier);
                                    Picasso.get().load(tierIconUrl).into(ivSoloqTierIcon);

                                    int totalGames = r.wins + r.losses;
                                    float winRatio = r.wins * 100 / totalGames;

                                    tvSoloqInfo.setText("W: " + r.wins + " L: " + r.losses + ", " + r.leaguePoints + "LP\n" +
                                                        " WR: " + winRatio + "%");
                                }
                                else if (r.queueType.equals("RANKED_FLEX_SR"))
                                {
                                    tvFlexqTier.setText(r.tier + " " + r.rank);
                                    String tierIconUrl = iconUrlMapper.getTierIcon(r.tier);
                                    Picasso.get().load(tierIconUrl).into(ivFlexqTierIcon);

                                    int totalGames = r.wins + r.losses;
                                    float winRatio = r.wins * 100 / totalGames;

                                    tvFlexqInfo.setText("W: " + r.wins + " L: " + r.losses + ", " + r.leaguePoints + "LP\n" +
                                            " WR: " + winRatio + "%");                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ApiTierResponse>> call, Throwable t) {
                            System.out.println("[INFO] Didn't get response from tier api.");
                        }
                    });
                }
                else if (response.code() == 429)
                {
                    Toast.makeText(getApplicationContext(), "Wait 60 seconds before checking again!", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == 404)
                {
                    Toast.makeText(getApplicationContext(), "Summoner not found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiSummonerResponse> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}