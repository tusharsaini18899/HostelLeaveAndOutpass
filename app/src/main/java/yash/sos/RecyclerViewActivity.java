package yash.sos;

/**
 * Created by My Document on 6/18/2018.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<Person> persons;
    private RecyclerView rv;
    final String URL="http://babywolf01.000webhostapp.com/tushar/faculty_cardview.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        persons = new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(RecyclerViewActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String connectionstaus=jsonObject.get("conn").toString();
                    if(connectionstaus.equals("CONN_SUCCESS"))
                    {

                        int number_of_rows= Integer.parseInt(jsonObject.get("numrows").toString());
                        for (int i=1;i<number_of_rows;i=i+4 )
                        {
                            persons.add(new Person(jsonObject.get(i+"").toString(),jsonObject.get(i+1+"").toString(),jsonObject.get(i+2+"").toString(),jsonObject.get(i+3+"").toString(),R.drawable.teacher1));
                            Log.e(jsonObject.get(i+"").toString(),jsonObject.get(i+1+"").toString());

                        }

                    }
                    else
                    {
                        Toast.makeText(RecyclerViewActivity.this, "Connection Failed due to some reason", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("232",persons.size()+"");
                    RVAdapter adapter = new RVAdapter(persons);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    Log.e("Error","Exception");
                    Toast.makeText(RecyclerViewActivity.this, "Connection Failed due to some reason", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecyclerViewActivity.this, "Connection Failed due to volly Error", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

   }
