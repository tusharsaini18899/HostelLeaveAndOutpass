package yash.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Tushar on 18-06-2018.
 */

public class ActionBarExample extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ggg);
        setSupportActionBar((Toolbar)findViewById(R.id.actionBar));
        getSupportActionBar().setTitle("");
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
                break;
            case R.id.exit:
                System.exit(0);
                break;
            case R.id.logout:
                SharedPreferences.Editor sp=getPreferences(MODE_PRIVATE).edit();
                sp.clear();
                sp.commit();
                Intent i1=new Intent(ActionBarExample.this,Login.class);
                startActivity(i1);
                finish();
                break;
        }
        return true;
    }
}
