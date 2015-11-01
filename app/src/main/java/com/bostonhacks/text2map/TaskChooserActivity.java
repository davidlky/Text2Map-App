package com.bostonhacks.text2map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TaskChooserActivity extends AppCompatActivity {

    Direction direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_chooser);
        direction = new Direction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_chooser, menu);
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

    public void goToRoutes(View view) {
        direction.type = Direction.TYPE.R;
        Intent i = new Intent(TaskChooserActivity.this,SearchActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Direction",direction);
        startActivity(i);
    }

    public void goToPTransit(View view) {
        direction.type = Direction.TYPE.P;
        Intent i = new Intent(TaskChooserActivity.this,SearchActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Direction", direction);
        startActivity(i);
    }

    public void goToFood(View view) {
        direction.type = Direction.TYPE.F;
        Intent i = new Intent(TaskChooserActivity.this,SearchActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Direction", direction);
        startActivity(i);
    }


    public void goToWifi(View view) {
        direction.type = Direction.TYPE.W;
        Intent i = new Intent(TaskChooserActivity.this,SearchActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Direction",direction);
        startActivity(i);
    }
}
