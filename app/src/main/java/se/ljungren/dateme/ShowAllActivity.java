package se.ljungren.dateme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        final ListView listview = (ListView) findViewById(R.id.listView);

        ApiServiceInterface api = retrofit.create(ApiServiceInterface.class);

        //getUserlist
        Call<List<User>> call2 = api.getUserList();
        call2.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call2, Response<List<User>> response) {

                //ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, response.body());
                //listview.setAdapter(adapter);

                for(int i=0 ; i<response.body().size() ; i++){
                    System.out.println(response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call2, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
