package com.ntecprograms.simplexmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void novo(View view){
        intent = new Intent(this, LinearActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrir(View view){
        intent = new Intent(this, OpenActivity.class);
        startActivity(intent);
        finish();
    }

    public void inform(View view){
        Alerts alerts = new Alerts(this);
        alerts.showAlertInfo();
    }

}
