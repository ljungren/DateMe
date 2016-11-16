package se.ljungren.dateme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "userInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameTxt;
        final EditText infoTxt;
        final EditText telTxt;
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        infoTxt = (EditText) findViewById(R.id.infoTxt);
        telTxt = (EditText) findViewById(R.id.telTxt);

        Button regButton = (Button) findViewById(R.id.regButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MatchActivity.class);

                //save data
                String name = nameTxt.getText().toString();
                String info = infoTxt.getText().toString();
                String tel = telTxt.getText().toString();
                SharedPreferences userInfo = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("userName", name);
                editor.putString("userInfo", info);
                editor.putString("userPhoneNumber", tel);
                editor.commit();

                //register send POST to web service


                //save id to sharedPref

                //go to next view
                startActivity(intent);
            }
        });
    }
}
