package yash.sos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by My Document on 6/18/2018.
 */

public class CardView extends Activity {
    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardview);
        personName = (TextView)findViewById(R.id.person_name);
//        personAge = (TextView)findViewById(R.id.person_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

        //  personName.setText("Emma Wilson");
        //personAge.setText("23 years old");
        // personPhoto.setImageResource(R.drawable.amage);

    }
}
