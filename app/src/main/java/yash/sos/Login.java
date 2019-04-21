package yash.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tushar on 15-06-2018.
 */

public class Login extends AppCompatActivity {
    TextInputLayout textInputPassword,textInputCoerid;
    TextView sign,fgpass;
    EditText id,epassword;
    Button login;
    ProgressDialog dialog;
    CheckBox remember;
    final String URL = "http://babywolf01.000webhostapp.com/tushar/login.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputCoerid = (TextInputLayout)findViewById(R.id.coerid);
        textInputCoerid.setHint("COER ID");

        textInputPassword = (TextInputLayout)findViewById(R.id.password);
        textInputPassword.setHint("Password");



        fgpass=(TextView)findViewById(R.id.fgpass);
        remember=(CheckBox)findViewById(R.id.check);
        id=(EditText)findViewById(R.id.coeridinput);
        epassword=(EditText)findViewById(R.id.passwordinput);
        sign=(TextView)findViewById(R.id.sign);
        login=(Button)findViewById(R.id.login);

        dialog=new ProgressDialog(Login.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Logging in.....");
        dialog.setTitle("Please wait!");

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Login.this,Home.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String passInput = epassword.getText().toString();
                final String idInput = id.getText().toString();
                if(idInput.equals("") || passInput.equals(""))
                {
                    Snackbar.make(v,"Enter Credentials First !",3000).setAction("Action",null).show();

                }
                else
                {
                    if(remember.isChecked())
                    {
                        SharedPreferences.Editor sp=getSharedPreferences("hosteller",MODE_PRIVATE).edit();
                        sp.putString("remember","2");
                        sp.commit();

                    }
                    dialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                String connectionStatus = responseObject.get("conn").toString();
                                if (connectionStatus.equals("CONN_SUCCESS")) {
                                    String loginStatus = responseObject.get("loginStatus").toString();
                                    if (loginStatus.equals("LOGIN_SUCCESS")) {
                                        Intent intent = new Intent(Login.this, Dashboard.class);
                                        dialog.dismiss();
                                        intent.putExtra("name",idInput);
                                        startActivity(intent);
                                    } else if (loginStatus.equals("WRONG_PASS")) {
                                        Toast.makeText(Login.this, "wrong password", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(Login.this, "Login failed due to some exception", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Connection with database failed", Toast.LENGTH_SHORT).show();
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
                            Log.e("Volley Error", error.toString());
                            dialog.dismiss();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("coerid", id.getText().toString());
                            params.put("password", epassword.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
        fgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Login.this,PhoneAuthActivity.class);
                startActivity(i1);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp1=getSharedPreferences("hosteller",MODE_PRIVATE);
        String check=sp1.getString("remember",null);
        if(check !=null)
        {
            Intent i1=new Intent(Login.this,Dashboard.class);
            startActivity(i1);
            finish();
        }
    }
}
