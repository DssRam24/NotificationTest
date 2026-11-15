package com.example.notificationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnnouncementDetailActivity extends AppCompatActivity {

    TextView tvDetailCategory, tvDetailTitle, tvDetailBody;
    Button btnDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);

        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailBody = findViewById(R.id.tvDetailBody);
        btnDismiss = findViewById(R.id.btnDismiss);

        // get the data sent through the intent
        tvDetailCategory.setText("Category: " + getIntent().getStringExtra("EXTRA_CATEGORY"));
        tvDetailTitle.setText(getIntent().getStringExtra("EXTRA_TITLE"));
        tvDetailBody.setText(getIntent().getStringExtra("EXTRA_MESSAGE"));

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
