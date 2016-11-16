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

public class MatchActivity extends Activity {

    public static final String PREFS = "userInfo";
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
        String userString = userInfo.getString("userName", "nothing found");
        //System.out.println(userString);
        nameTxt2.setText(userString);

        Button matchButton = (Button) findViewById(R.id.matchButton);

        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data

            }
        });
    }
}
