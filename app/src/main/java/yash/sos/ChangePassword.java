package yash.sos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Tushar on 19-06-2018.
 */

public class ChangePassword extends AppCompatActivity {
    EditText newpass,confpass,coerid1;
    Button confirm;
    final String URL="http://babywolf01.000webhostapp.com/tushar/new_password.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        coerid1=(EditText)findViewById(R.id.coerid1);
        newpass=(EditText)findViewById(R.id.newpass);
        confpass=(EditText)findViewById(R.id.confirmpass);
        confirm=(Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpass.getText().toString().equals(confpass.getText().toString()))
                {
                    RequestQueue requestQueue= Volley.newRequestQueue(ChangePassword.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String connection_status=jsonObject.get("conn").toString();
                                if (connection_status.equals("CONN_SUCCESS"))
                                {
                                    String querystatus=jsonObject.get("queryExecute").toString();
                                    if (querystatus.equals("SUCCESSFUL"))
                                    {
                                        Toast.makeText(ChangePassword.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                                        Intent i1=new Intent(ChangePassword.this,Login.class);
                                        startActivity(i1);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePassword.this, "Query Execute failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(ChangePassword.this, "Query Execute failed", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                Toast.makeText(ChangePassword.this, "Query Execute failed", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String,String> Hash=new HashMap<String, String>();
                            Hash.put("coerid",coerid1.getText().toString());
                            Hash.put("password",newpass.getText().toString());
                            return Hash;

                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });


    }
}
