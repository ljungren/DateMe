package se.ljungren.dateme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.google.gson.Gson;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import se.ljungren.dateme.models.User;


//compile 'com.squareup.retrofit2:converter-gson:2.1.0'


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "userInfo";

    public static final String BASE_URL = "https://dmweb.herokuapp.com/api/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameTxt;
        final EditText infoTxt;
        final EditText telTxt;
        final CheckBox activeBox;
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        infoTxt = (EditText) findViewById(R.id.infoTxt);
        telTxt = (EditText) findViewById(R.id.telTxt);
        activeBox = (CheckBox) findViewById(R.id.active);

        //Read pref and update fields
        SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
        if(userInfo.getString("userId", null)!=null){
            Intent intent = new Intent(MainActivity.this, MatchActivity.class);
            startActivity(intent);
        }

        Button regButton = (Button) findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //save data
                String name = nameTxt.getText().toString();
                String info = infoTxt.getText().toString();
                String tel = telTxt.getText().toString();
                Boolean active = activeBox.isChecked();

                //create user object with data
                user = new User(name, info, tel, active);

                //register, send POST to web service
                ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);
                Call<User> call = api.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Intent intent = new Intent(MainActivity.this, MatchActivity.class);

                        //set response id
                        user.setId(response.body().getId());

                        //save id and user data to sharedPref
                        SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                        SharedPreferences.Editor editor = userInfo.edit();
                        editor.putString("userName", user.getName());
                        editor.putString("userInfo", user.getInfo());
                        editor.putString("userPhoneNumber", user.getTelno());
                        editor.putBoolean("active", user.getActive());
                        editor.putString("userId", user.getId());
                        editor.apply();

                        //go to match view
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });
    }
}