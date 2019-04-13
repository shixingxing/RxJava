package com.test.rxjava;

import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.test.rxjava.fragment.Sample1Fragment;
import com.test.rxjava.fragment.Sample2Fragment;
import com.test.rxjava.fragment.Sample3Fragment;
import com.test.rxjava.fragment.Sample4Fragment;
import com.test.rxjava.fragment.Sample5Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

    private static final int PERMISSION_RQ = 2333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int p = PermissionChecker.checkSelfPermission(this, Manifest.permission.INTERNET);
            if (p != PermissionChecker.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSION_RQ);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
