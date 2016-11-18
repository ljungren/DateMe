package se.ljungren.dateme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.ljungren.dateme.models.User;

import static se.ljungren.dateme.MainActivity.PREFS;

public class ChangeProfileActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://dmweb.herokuapp.com/api/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

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
        nameTxt.setText(userInfo.getString("userName", null));
        infoTxt.setText(userInfo.getString("userInfo", null));
        telTxt.setText(userInfo.getString("userPhoneNumber", null));
        activeBox.setChecked(userInfo.getBoolean("active", false));

        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new user object
                user = new User(nameTxt.getText().toString(), infoTxt.getText().toString(), telTxt.getText().toString(), activeBox.isChecked());

                //update pref
                SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("userName", user.getName());
                editor.putString("userInfo", user.getInfo());
                editor.putString("userPhoneNumber", user.getTelno());
                editor.apply();

                String userId = userInfo.getString("userId", null);

                //do PUT
                ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);
                Call<User> call = api.setUser(userId, user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Updated user", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get user id
                SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                String userId = userInfo.getString("userId", null);

                //do DELETE
                ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);
                Call<ResponseBody> call = api.deleteUser(userId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent = new Intent(ChangeProfileActivity.this, MainActivity.class);
                        //Clear prefs
                        nameTxt.setText("");
                        infoTxt.setText("");
                        telTxt.setText("");
                        activeBox.setChecked(false);

                        //clear pref
                        SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                        SharedPreferences.Editor editor = userInfo.edit();
                        editor.putString("userName", null);
                        editor.putString("userInfo", null);
                        editor.putString("userPhoneNumber", null);
                        editor.putString("userId", null);
                        editor.apply();

                        //go to register Activity
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeProfileActivity.this, MatchActivity.class);
                startActivity(intent);
            }
        });
    }
}
