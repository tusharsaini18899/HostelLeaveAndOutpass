package yash.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by My Document on 6/16/2018.
 */

public class Status extends AppCompatActivity {
    ProgressDialog dialog;
    TextView name1,coer_id1,registration_number,status1;
    String registration_num,name,coer_id,status;
    Button home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        registration_num = getIntent().getStringExtra("registration_num");
        name= getIntent().getStringExtra("name");
        status=getIntent().getStringExtra("status");
        coer_id=getIntent().getStringExtra("coer_id");
        name1 = (TextView) findViewById(R.id.name);
        coer_id1 = (TextView) findViewById(R.id.coer_id);
        registration_number = (TextView) findViewById(R.id.registration_num);
        status1 = (TextView) findViewById(R.id.status);
        registration_number.setText(registration_num);
        status1.setText(status);
        name1.setText(name);
        coer_id1.setText(coer_id);
        home=(Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(Status.this,Dashboard.class);//Get back to DASHBOARD
                startActivity(i3);
                finish();
            }
        });

    }
}
