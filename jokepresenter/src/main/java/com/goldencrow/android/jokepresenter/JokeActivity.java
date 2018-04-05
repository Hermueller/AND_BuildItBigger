package com.goldencrow.android.jokepresenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    /**
     * Key for saving the joke in an intent.
     */
    public static final String JOKE_KEY = "joke";

    /**
     * Key for saving the category of the joke in an intent.
     */
    public static final String JOKE_CATEGORY_KEY = "joke_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(JOKE_KEY)) {
            // Extract the information from the intent.
            String joke = intent.getStringExtra(JOKE_KEY);
            String category = intent.getStringExtra(JOKE_CATEGORY_KEY);

            // Find TextViews and display the info from the intent with it.
            TextView jokeTv = findViewById(R.id.joke_tv);
            jokeTv.setText(joke);

            TextView jokeLabelTv = findViewById(R.id.joke_label_tv);
            jokeLabelTv.setText(getString(R.string.joke_header, category));
        }
    }
}
