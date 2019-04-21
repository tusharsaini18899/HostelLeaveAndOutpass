package yash.sos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Tushar on 16-06-2018.
 */

public class Final extends AppCompatActivity {
    TextView nametext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_final);

        nametext=(TextView)findViewById(R.id.textView);
        String name=getIntent().getStringExtra("name");
        nametext.setText("Hello "+name);

    }
}
