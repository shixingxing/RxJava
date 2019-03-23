package com.test.rxjava;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.test.rxjava.fragment.Sample1Fragment;
import com.test.rxjava.fragment.Sample2Fragment;
import com.test.rxjava.fragment.Sample3Fragment;
import com.test.rxjava.fragment.Sample4Fragment;
import com.test.rxjava.fragment.Sample5Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if (id == R.id.action_settings_sample1) {
            jump(new Sample1Fragment());
            return true;
        }

        if (id == R.id.action_settings_sample2) {
            jump(new Sample2Fragment());
            return true;
        }
        if (id == R.id.action_settings_sample3) {
            jump(new Sample3Fragment());
            return true;
        }
        if (id == R.id.action_settings_sample4) {
            jump(new Sample4Fragment());
            return true;
        }
        if (id == R.id.action_settings_sample5) {
            jump(new Sample5Fragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void jump(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.addToBackStack(null).commit();
    }
}
