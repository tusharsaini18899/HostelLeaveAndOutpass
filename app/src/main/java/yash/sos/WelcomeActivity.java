package yash.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    ViewPager mpager;
    private int[] layouts={R.layout.first_slide,R.layout.second_slide,R.layout.third_slide};
    private MpagerAdapter mpagerAdapter;
    Button skip,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        final SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
        skip=(Button)findViewById(R.id.skip);
        next=(Button)findViewById(R.id.next);
        mpager=(ViewPager)findViewById(R.id.viewpager);
        mpagerAdapter=new MpagerAdapter(layouts,this);
        mpager.setAdapter(mpagerAdapter);
        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position== layouts.length-1)
                {
                    next.setText("Start");
                    skip.setVisibility(View.INVISIBLE);
                }
                else {
                    next.setText("Next");
                    skip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("value","2");
                editor.commit();
                Intent i1=new Intent(WelcomeActivity.this,Login.class);
                startActivity(i1);
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextslide=mpager.getCurrentItem()+1;
                if (nextslide<layouts.length)
                {
                    mpager.setCurrentItem(nextslide);
                }
                else
                {
                    SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                    editor.putString("value","2");
                    editor.commit();
                    Intent i1=new Intent(WelcomeActivity.this,Login.class);
                    startActivity(i1);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        String check=preferences.getString("value",null);
        if (check!=null)
        {
            Intent i2=new Intent(WelcomeActivity.this,Login.class);
            startActivity(i2);
            finish();
        }

    }
}
