package yash.sos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Tushar on 17-06-2018.
 */

public class Dashboard extends AppCompatActivity {
    ImageView i1,i2,i3,i4,i5,i6;
    Button t1,t2,t3,t4,t5,t6;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ggg);
        i1=(ImageView)findViewById(R.id.i1);
        i2=(ImageView)findViewById(R.id.i2);
        i3=(ImageView)findViewById(R.id.i3);
        i4=(ImageView)findViewById(R.id.i4);
        i5=(ImageView)findViewById(R.id.i5);
        i6=(ImageView)findViewById(R.id.i6);
        t1=(Button) findViewById(R.id.t1);
        t2=(Button)findViewById(R.id.t2);
        t3=(Button)findViewById(R.id.t3);
        t4=(Button)findViewById(R.id.t4);
        t5=(Button)findViewById(R.id.t5);
        t6=(Button)findViewById(R.id.t6);


        setSupportActionBar((Toolbar)findViewById(R.id.actionBar));
        getSupportActionBar().setTitle("");

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii1=new Intent(Dashboard.this,form.class);
                startActivity(ii1);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii2=new Intent(Dashboard.this,OutpassForm.class);
                startActivity(ii2);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii3=new Intent(Dashboard.this,mng.class);
                startActivity(ii3);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii4=new Intent(Dashboard.this,CardView.class);
                startActivity(ii4);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii5=new Intent(Dashboard.this,complll.class);
                startActivity(ii5);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii6=new Intent(Dashboard.this,comq.class);
                startActivity(ii6);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.about:
                Intent i=new Intent(Dashboard.this,abot.class);
                startActivity(i);
                break;
            case R.id.exit:
                this.finishAffinity();
                break;
            case R.id.logout:
                SharedPreferences.Editor sp=getSharedPreferences("hosteller",MODE_PRIVATE).edit();
                sp.clear();
                sp.commit();
                Intent i1=new Intent(Dashboard.this,Login.class);
                startActivity(i1);
                finish();
                break;
        }
        return true;
    }



}
