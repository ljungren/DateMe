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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.ljungren.dateme.models.User;

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
        nameTxt2 = (TextView) findViewById(R.id.nameTxt2);

        SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
        String userName = userInfo.getString("userName", "nothing found");
        //System.out.println(userString);
        nameTxt2.setText(userName);

        Button matchButton = (Button) findViewById(R.id.matchButton);
        Button showButton = (Button) findViewById(R.id.showButton);
        Button changeButton = (Button) findViewById(R.id.changeButton);

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchActivity.this, ChangeProfileActivity.class);
                ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);

                //get match
                Call<List<User>> call2 = api.getUserList();
                call2.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call2, Response<List<User>> response) {

                        /*SharedPreferences.Editor prefsEditor = listPrefs.edit();

                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        prefsEditor.putString("match", json);
                        prefsEditor.commit();

                        /*for(int i=0 ; i<response.body().size() ; i++){
                            System.out.println(response.body().get(i).getName());
                        }*/
                    }

                    @Override
                    public void onFailure(Call<List<User>> call2, Throwable t) {
                        System.out.println(t);
                    }
                });
                startActivity(intent);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchActivity.this, ShowAllActivity.class);
                startActivity(intent);
            }
        });
    }
}
