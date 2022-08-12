package com.thecodework.handgesture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

public class DashBoardActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initView();
        initializer();
    }

    private void initializer() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoardActivity.this,
                        MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void initView() {
        startButton = findViewById(R.id.startButton);
    }
}