package yash.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * Created by Tushar on 15-06-2018.
 */

public class Home extends AppCompatActivity {

    TextInputLayout textInputName,textInputCoerid,textInputPassword,textInputMail,textInputPhone,textInputBranch,textInputYear;
    EditText ename,ecoerid,epassword,eemail,ephone,ebranch,eyear;
    Button signup;
    ProgressDialog dialog;
    final String URL = "http://babywolf01.000webhostapp.com/tushar/signup2.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputName = (TextInputLayout)findViewById(R.id.name);
        textInputName.setHint("Name");

        textInputCoerid = (TextInputLayout)findViewById(R.id.coerid);
        textInputCoerid.setHint("COER ID");

        textInputPassword = (TextInputLayout)findViewById(R.id.password);
        textInputPassword.setHint("Password");

        textInputMail = (TextInputLayout)findViewById(R.id.email);
        textInputMail.setHint("E-Mail");

        textInputPhone = (TextInputLayout)findViewById(R.id.phone);
        textInputPhone.setHint("Phone");

        textInputBranch = (TextInputLayout)findViewById(R.id.branch);
        textInputBranch.setHint("Branch");

        textInputYear = (TextInputLayout)findViewById(R.id.year);
        textInputYear.setHint("Year");

        ename=(EditText)findViewById(R.id.nameInput);
        ecoerid=(EditText)findViewById(R.id.coeridinput);
        epassword=(EditText)findViewById(R.id.passwordinput);
        eemail=(EditText)findViewById(R.id.emailinput);
        ephone=(EditText)findViewById(R.id.phoneinput);
        ebranch=(EditText)findViewById(R.id.branchinput);
        eyear=(EditText)findViewById(R.id.yearinput);

        signup=(Button)findViewById(R.id.signup);
        dialog=new ProgressDialog(Home.this);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Logging in.....");
        dialog.setTitle("Please wait!");




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameInput = ename.getText().toString();
                String coeridInput = ecoerid.getText().toString();
                String passInput = epassword.getText().toString();
                String emailInput = eemail.getText().toString();
                String phoneInput = ephone.getText().toString();
                String branchInput = ebranch.getText().toString();
                String yearInput = eyear.getText().toString();

                if (nameInput.equals("") || coeridInput.equals("") || passInput.equals("") || emailInput.equals("") || phoneInput.equals("") || branchInput.equals("") || yearInput.equals(""))
                {
                    Snackbar.make(v,"Enter Some Data !",2000).setAction("Action",null).show();

                }
                else
                {

                    dialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(Home.this);

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                String connectionStatus = responseObject.get("conn").toString();
                                if (connectionStatus.equals("CONN_SUCCESS")) {
                                    String queryInsert = responseObject.get("queryInsert").toString();
                                    if (queryInsert.equals("IN_SUCCESS")) {
                                        Intent intent = new Intent(Home.this,Login.class);
                                        intent.putExtra("name", nameInput);
                                        dialog.dismiss();
                                        startActivity(intent);
                                    } else if (queryInsert.equals("IN_FAIL")) {
                                        Toast.makeText(Home.this, "failed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(Home.this, "failed due to some exception", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                } else {
                                    Toast.makeText(Home.this, "Connection with database failed", Toast.LENGTH_SHORT).show();
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
                            params.put("name",ename.getText().toString());
                            params.put("coerid", ecoerid.getText().toString());
                            params.put("password", epassword.getText().toString());
                            params.put("email",eemail.getText().toString());
                            params.put("phone", ephone.getText().toString());
                            params.put("branch", ebranch.getText().toString());
                            params.put("year", eyear.getText().toString());

                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });

    }
}
