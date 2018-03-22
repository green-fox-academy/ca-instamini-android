package com.greenfox.aze.instamini;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Login extends AppCompatActivity {

    EditText username;
    Button login;

    Api service;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        preferences = getPreferences(Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://instamini.mobil.ninja/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(Api.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.registerUser(new UserRequest(new User(username.getText().toString()))).enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        if ("ok".equals(response.body().status)) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(MainActivity.USERNAME, username.getText().toString());
                            editor.apply();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
