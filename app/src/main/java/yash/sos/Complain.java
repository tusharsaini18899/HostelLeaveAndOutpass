package yash.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by My Document on 6/17/2018.
 */

public class Complain extends AppCompatActivity {
    final String URL = "http://babywolf01.000webhostapp.com/tushar/put_complaints_in_db.php";
    ProgressDialog dialog;
    RelativeLayout.LayoutParams layoutParams;
    EditText name, coer_id,complain,mobile_no;
    RelativeLayout l2;
    Button submit,back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain);
        name = (EditText) findViewById(R.id.name);
        complain= (EditText) findViewById(R.id.complain);
        coer_id = (EditText) findViewById(R.id.coer_id);
        submit=(Button)findViewById(R.id.complain_submit);
        mobile_no=(EditText)findViewById(R.id.mobile_no);
        back=(Button)findViewById(R.id.back);
        dialog = new ProgressDialog(Complain.this);
        l2 = (RelativeLayout) findViewById(R.id.complain_view);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait");
        dialog.setTitle("Complain Request");

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                if (name.getText().toString().equals("") || coer_id.getText().toString().equals("") || complain.getText().toString().equals("") || mobile_no.getText().toString().equals("") ) {
                    Toast.makeText(Complain.this, "Enter some credential", Toast.LENGTH_SHORT).show();
                }
                else if(mobile_no.getText().length()<10) {

                    Toast.makeText(Complain.this, "Please enter correct mobile no.", Toast.LENGTH_SHORT).show();

                }
                    else
                    {

                    dialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(Complain.this);
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            try {

                                JSONObject object = new JSONObject(response);
                                String connection_status = object.get("conn").toString();
                                l2.removeAllViews();
                                if (connection_status.equals("CONN_SUCCESS")) {
                                    Log.e("hlo","hlo");
                                    String insertion_status = object.get("complainInsert").toString();
                                    if (insertion_status.equals("INSERTION_SUCCESS")) {
                                        TextView view = new TextView(Complain.this);
                                        Toast.makeText(Complain.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                        String registration_num = object.get("registration_num").toString();
                                        Log.e("hlo1","hlo1");
                                        view.setTextSize(30.0f);
//                                        view.setTextColor(R.color."Blue");
                                        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                                        view.setText("      Your complain no. is " + registration_num);
                                        l2.addView(view);
                                        dialog.dismiss();

                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(Complain.this, "Request Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else {
                                    dialog.dismiss();
                                    Toast.makeText(Complain.this, "Request Failed", Toast.LENGTH_SHORT).show();

                                }


                            } catch (JSONException e) {
                                dialog.dismiss();
                                Toast.makeText(Complain.this, "Request Failed", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(Complain.this, "Request Failed", Toast.LENGTH_SHORT).show();
                            Log.e("VOLLY ERROR", error.toString());


                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("name", name.getText().toString());
                            params.put("coer_id", coer_id.getText().toString());
                            params.put("mobile_no", mobile_no.getText().toString());
                            params.put("complain", complain.getText().toString());

                            return params;

                        }


                    };
                    requestQueue.add(request);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Complain.this,Dashboard.class);        // get back to DashBoard
                startActivity(i1);
            }
        });



    }

}
