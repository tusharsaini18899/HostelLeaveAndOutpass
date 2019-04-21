package yash.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

public class comq extends AppCompatActivity {
    EditText registration_num;
    RadioButton complain,query;
    RadioGroup radioGroup;
    Button submit,refresh;
    ProgressDialog dialog;




    String status1,category;
    final String URL="http://babywolf01.000webhostapp.com/tushar/check_status.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comquery);
        dialog = new ProgressDialog(comq.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Status");
        dialog.setMessage("Please wait");





        registration_num=(EditText)findViewById(R.id.registration_num);
        complain=(RadioButton)findViewById(R.id.complain_radio);
        query=(RadioButton)findViewById(R.id.query_radio);
        radioGroup=(RadioGroup)findViewById(R.id.radio);
        submit=(Button)findViewById(R.id.submit_status);
        refresh=(Button)findViewById(R.id.refresh_status);



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration_num.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registration_num.getText().toString().equals("") || (!complain.isChecked()&& !query.isChecked()))
                {
                    if(registration_num.getText().toString().equals(""))
                    {
                        Toast.makeText(comq.this, "Please Insert Registration Number ", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(comq.this, "Please select any one of the category", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (complain.isChecked())
                    {
                        category="complaints";
                    }
                    else
                    {
                        category="queries";

                    }


                    RequestQueue requestQueue= Volley.newRequestQueue(comq.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                dialog.show();
                                JSONObject jsonObject=new JSONObject(response);
                                String connction_status=jsonObject.get("conn").toString();
                                if(connction_status.equals("CONN_SUCCESS"))
                                {
                                    String queryExecute=jsonObject.get("queryExecute").toString();
                                    if(queryExecute.equals("SUCCESSFUL"))
                                    {
                                        String getname=jsonObject.get("name").toString();
                                        String getcoer_id=jsonObject.get("coer_id").toString();
                                        String status=jsonObject.get("status").toString();
                                        Intent i1=new Intent(comq.this,Status.class);
                                        i1.putExtra("registration_num",registration_num.getText().toString());
                                        if(status.equals("1"))
                                        {
                                            status1="Request Initiated...";

                                        }
                                        else if (status.equals("2"))
                                        {

                                            status1="Request in progress...";

                                        }
                                        else {
                                            status1="Request Completed...";


                                        }
                                        i1.putExtra("status",status1);
                                        i1.putExtra("name",getname);
                                        i1.putExtra("coer_id",getcoer_id);
                                        dialog.dismiss();
                                        startActivity(i1);
                                        finish();

                                    }
                                    else {
                                        Intent i1=new Intent(comq.this,No_information.class);
                                        dialog.dismiss();
                                        startActivity(i1);
                                        finish();
                                    }

                                }
                                else {
                                    dialog.dismiss();
                                    Toast.makeText(comq.this, "Connection failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                dialog.dismiss();
                                Toast.makeText(comq.this, "Connection failed due to some reason", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Log.e("Volly error","error");
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("registration_num",registration_num.getText().toString());
                            hashMap.put("category",category);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }


            }
        });


    }
}
