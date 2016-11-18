package se.ljungren.dateme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.ljungren.dateme.models.User;

public class ShowAllActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://dmweb.herokuapp.com/api/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    List<String> users;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);

        users = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);

        //getUserlist
        Call<List<User>> call = api.getUserList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                //display usernames in listView
                for(int i=0 ; i<response.body().size() ; i++){
                    //System.out.println(response.body().get(i).getName());
                    users.add(response.body().get(i).getName());
                }

                ListView listview = (ListView) findViewById(R.id.listView);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
