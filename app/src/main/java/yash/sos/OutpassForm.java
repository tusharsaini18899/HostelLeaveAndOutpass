package yash.sos;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OutpassForm extends AppCompatActivity {

    EditText name1,year1,branch1,hostelname1,roomno1,coerid1,phoneno1,reason1,timefrom1,timeto1;
    TextView date1;
    Button submitoutpass1,backbutton;
    private DatePickerDialog.OnDateSetListener mdate;

    ProgressDialog dialog;

    final String URL="http://babywolf01.000webhostapp.com/tushar/outpassform.php";   //change it later

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outpass_form);

        name1=(EditText)findViewById(R.id.namee);
        year1=(EditText)findViewById(R.id.yeare);
        branch1=(EditText)findViewById(R.id.branche);
        hostelname1=(EditText)findViewById(R.id.hostelnamee);
        roomno1=(EditText)findViewById(R.id.roomnoe);
        coerid1=(EditText)findViewById(R.id.coeride);
        phoneno1=(EditText)findViewById(R.id.phonenoe);
        reason1=(EditText)findViewById(R.id.reasone);
        date1=(TextView)findViewById(R.id.datee);
        timefrom1=(EditText)findViewById(R.id.timefrome);
        timeto1=(EditText)findViewById(R.id.timetoe);

        submitoutpass1=(Button)findViewById(R.id.submitoutpass);
        backbutton=(Button)findViewById(R.id.back1);

        dialog=new ProgressDialog(OutpassForm.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Outpass Submission");
        dialog.setTitle("Outpass Submission");

        submitoutpass1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        submitoutpass1.setBackgroundDrawable(getResources().getDrawable(R.drawable.leave_button_pressed));
                        break;
                        case MotionEvent.ACTION_UP:
                            submitoutpass1.setBackgroundDrawable(getResources().getDrawable(R.drawable.leave_button));
                            break;
                }
                return false;
            }
        });



        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(OutpassForm.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mdate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mdate=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date1.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };




        submitoutpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String name2=name1.getText().toString();
                String year2=year1.getText().toString();
                String branch2=branch1.getText().toString();
                String hostelname2=hostelname1.getText().toString();
                String roomno2=roomno1.getText().toString();
                String coerid2=coerid1.getText().toString();
                String phoneno2=phoneno1.getText().toString();
                String reason2=reason1.getText().toString();
                String date2=date1.getText().toString();
                String timefrom2=timefrom1.getText().toString();
                String timeto2=timeto1.getText().toString();

                if (name2.equals("") || year2.equals("") || branch2.equals("") || hostelname2.equals("") || roomno2.equals("")  || coerid2.equals("") || phoneno2.equals("") || reason2.equals("") || date2.equals("") || timefrom2.equals("") || timeto2.equals("") )
                {
                    Toast.makeText(OutpassForm.this,"Please Enter All the Values", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
                else
                {
                    Log.d("SQL", "Inserting data");
                    dialog.show();

                    RequestQueue requestQueue= Volley.newRequestQueue(OutpassForm.this);
                    StringRequest request=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String connectionStatus=jsonObject.get("conn").toString();
                                if(connectionStatus.equals("CONN_SUCCESS"))
                                {

                                    String signupstatus=jsonObject.get("queryInsert").toString();
                                    if(signupstatus.equals("IN_FAIL"))
                                    {
                                        Toast.makeText(OutpassForm.this, "Dear "+name2+"! Your Outpass Submission Failed !", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(OutpassForm.this, "Dear "+name2+"! Outpass Submitted Successfully !", Toast.LENGTH_SHORT).show();


                                        Intent i3=new Intent(OutpassForm.this,Pdf_Outpass.class);  //Change it later

                                        dialog.dismiss();
                                        startActivity(i3);
                                    }

                                }
                                else
                                {
                                    Toast.makeText(OutpassForm.this, "Connection Error", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volly error","error");
                            dialog.dismiss();

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {

                            HashMap<String,String> hash =new HashMap<>();
                            hash.put("name",name1.getText().toString());
                            hash.put("year",year1.getText().toString());
                            hash.put("branch",branch1.getText().toString());
                            hash.put("hostel_name",hostelname1.getText().toString());
                            hash.put("room_no",roomno1.getText().toString());
                            hash.put("coer_id",coerid1.getText().toString());
                            hash.put("phone_number",phoneno1.getText().toString());
                            hash.put("reas",reason1.getText().toString());
                            hash.put("dt",date1.getText().toString());
                            hash.put("time_from",timefrom1.getText().toString());
                            hash.put("time_to",timeto1.getText().toString());
                            return hash;
                        }
                    };
                    requestQueue.add(request);
                }

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a1=new Intent(OutpassForm.this,Dashboard.class);
                startActivity(a1);
            }
        });
    }
}
