package yash.sos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by My Document on 6/17/2018.
 */

public class No_information extends AppCompatActivity {
    Button home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_information);


        home=(Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(No_information.this,Dashboard.class);//Get back to DASHBOARD
                startActivity(i3);
                finish();
            }
        });

    }
}
