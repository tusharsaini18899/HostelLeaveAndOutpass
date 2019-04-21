package yash.sos;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by My Document on 6/10/2018.
 */

public class Pdf extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";

    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    final String URL="http://babywolf01.000webhostapp.com/tushar/leave_status.php";
    String name;
     String year;
     Button button;
     String branch;
     String hostel_name;
     String room_no;
String coer_id;
     String phone_number;
     String reas;
     String leave_datefrom;
     String leave_dateto;
    String pdfa;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pp);
button=(Button)findViewById(R.id.button);
coer_id=getIntent().getStringExtra("coer_idpdf");

        RequestQueue requestQueue = Volley.newRequestQueue(Pdf.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String connectionstatus = jsonObject.get("conn").toString();
                    if (connectionstatus.equals("CONN_FAILED")) {
                        Toast.makeText(Pdf.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        String status = jsonObject.get("status").toString();
                        if (status.equals("SUCCESS")) {

                            name = jsonObject.get("name").toString();
                            year = jsonObject.get("year").toString();
                            branch = jsonObject.get("branch").toString();
                            hostel_name = jsonObject.get("hostel_name").toString();
                            room_no = jsonObject.get("room_no").toString();
                            coer_id = jsonObject.get("coer_id").toString();
                            phone_number = jsonObject.get("phone_number").toString();
                            reas = jsonObject.get("reas").toString();
                            leave_datefrom = jsonObject.get("leave_datefrom").toString();
                            leave_dateto = jsonObject.get("leave_dateto").toString();
                            Log.e("Error", name + year + branch);
                             pdfa = "Name: " + name + "\n" + "Year: " + year + "\n" + "Branch: " + branch + "\n" + "Hostel Name: " + hostel_name + "\n" + "Room Number: " + room_no + "\n" + "COER ID"+coer_id+"\n"+"Phone Number: " + phone_number + "\n" + "Reason: " + reas + "\n" + "leave Date From: " + leave_datefrom + "\n" + "leave Date To: " + leave_dateto + "\n";

                        } else {
                            Toast.makeText(Pdf.this, "Record not found", Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(Pdf.this, e.toString(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volly Error", "Error");

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hash = new HashMap<>();
                hash.put("coer_id", coer_id);
                return hash;
            }
        };
        requestQueue.add(stringRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Pdf.this,pdf2.class);
                intent.putExtra("yash",pdfa);
                startActivity(intent);
            }
        });


    }


    }




