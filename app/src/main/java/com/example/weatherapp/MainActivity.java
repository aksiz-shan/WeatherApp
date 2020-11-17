package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    TextView view_city;
    TextView view_temp;
    TextView view_desc;

    ImageView view_weather;
    EditText search;
    FloatingActionButton search_floating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_city = (TextView)findViewById(R.id.town) ;
        view_temp = (TextView)findViewById(R.id.temp);
        view_desc = (TextView)findViewById(R.id.desc);
        view_city.setText("");
        view_temp.setText("");
        view_desc.setText("");

        view_weather = findViewById(R.id.weatherImage);
        search = findViewById(R.id.editTown);
        search_floating = findViewById(R.id.btnSearch);

        search_floating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imn = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                imn.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);
                api_key(String.valueOf(search.getText()));
            }

            private void api_key(final String City) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?q="+City+"&appid=ecfd64c2b4273ed07af313a31053c70a&units=metric")
                        .get()
                        .build();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try{
                    Response response = client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String responseData = response.body().string();
                            try {
                                JSONObject json = new JSONObject(responseData);
                                JSONArray array = json.getJSONArray("weather");
                                JSONObject object = array.getJSONObject(0);

                                String description = object.getString("description");
                                String icons = object.getString("icon");
                                JSONObject temp1 = json.getJSONObject("main");
                                Double Temperature = temp1.getDouble("temp");

                                setText(view_city,City);
                                String temps = (Math.round(Temperature)+" °С");
                                setText(view_temp,temps);
                                setText(view_desc,description);
                                setImage(view_weather,icons);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void setText(final TextView text, final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(value);
                    }
                });
            }

            private void setImage(final ImageView imageView, final String value){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (value) {
                            case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon1));
                                break;
                            case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
                                break;
                            case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon3));
                                break;
                            case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                                break;
                            case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                                break;
                            case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon6));
                                break;
                            case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;
                            case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon7));
                                break;
                            default:
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.weather));
                        }
                    }
                });
            }
        });

    }
}