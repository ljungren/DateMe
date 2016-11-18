package se.ljungren.dateme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.ljungren.dateme.models.User;
import java.util.Random;


public class MatchActivity extends Activity {

    public static final String PREFS = "userInfo";

    public static final String BASE_URL = "https://dmweb.herokuapp.com/api/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        final TextView nameTxt2;
        final TextView matchNameTxt;
        final TextView matchInfoTxt;
        final TextView matchTelTxt;
        nameTxt2 = (TextView) findViewById(R.id.nameTxt2);
        matchNameTxt = (TextView) findViewById(R.id.matchNameTxt);
        matchInfoTxt = (TextView) findViewById(R.id.matchInfoTxt);
        matchTelTxt = (TextView) findViewById(R.id.matchTelTxt);

        Button matchButton = (Button) findViewById(R.id.matchButton);
        Button showButton = (Button) findViewById(R.id.showButton);
        Button changeButton = (Button) findViewById(R.id.changeButton);

        SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
        if(userInfo.getString("userId", null)==null){
            Intent intent = new Intent(MatchActivity.this, MainActivity.class);
            startActivity(intent);
        }

        String userName = userInfo.getString("userName", null);
        nameTxt2.setText(userName);

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);

                //get match
                SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                Call<List<User>> call = api.getUser(userInfo.getString("userId", null));
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        //get random match and display result
                        Random rand = new Random();
                        int randomNum = (int)(Math.random() * ((response.body().size()-1) + 1));

                        matchNameTxt.setText(response.body().get(randomNum).getName());
                        matchInfoTxt.setText(response.body().get(randomNum).getInfo());
                        matchTelTxt.setText(response.body().get(randomNum).getTelno());

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchActivity.this, ShowAllActivity.class);
                startActivity(intent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchActivity.this, ChangeProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
