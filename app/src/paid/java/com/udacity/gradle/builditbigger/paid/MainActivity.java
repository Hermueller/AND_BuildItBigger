package com.udacity.gradle.builditbigger.paid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /**
     * The button which answers with Physics Jokes.
     */
    @BindView(R.id.science_btn)
    Button physicsBtn;

    /**
     * The button which answers with Software Developer Jokes.
     */
    @BindView(R.id.software_btn)
    Button softwareBtn;

    /**
     * The button which answers with a funny Software Pick Up Line.
     */
    @BindView(R.id.pick_up_line_btn)
    Button pickUpLineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Set tags, so that the onClick method can fiend the category easier.
        physicsBtn.setTag("Physics");
        softwareBtn.setTag("Computer Science");
        pickUpLineBtn.setTag("Software Pick Up Line");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Requests and displays a new joke from the selected category.
     *
     * @param view  the Button which was clicked on.
     */
    public void tellJoke(View view) {
        String category = (String) view.getTag();

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute(category);
    }
}
