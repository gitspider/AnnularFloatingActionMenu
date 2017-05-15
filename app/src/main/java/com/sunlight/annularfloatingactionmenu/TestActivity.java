package com.sunlight.annularfloatingactionmenu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sunlight.annularfloatingmenu.FloatingActionButton;
import com.sunlight.annularfloatingmenu.FloatingActionMenu;
import com.sunlight.annularfloatingmenu.SubActionButton;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 2);

                return;
            }
        }

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton button1 = itemBuilder.build();
        SubActionButton button2 = itemBuilder.build();

        SubActionButton button3 = itemBuilder.build();

        ImageView itemIcon1 = new ImageView(this); // Create an icon
        itemIcon1.setImageResource(android.R.drawable.ic_dialog_map);
        button1.setContentView(itemIcon1);

        ImageView itemIcon2 = new ImageView(this); // Create an icon
        itemIcon2.setImageResource(android.R.drawable.ic_dialog_dialer);
        button2.setContentView(itemIcon2);

        ImageView itemIcon3 = new ImageView(this); // Create an icon
        itemIcon3.setImageResource(android.R.drawable.ic_dialog_alert);
        button3.setContentView(itemIcon3);

        SubActionButton button4 = itemBuilder.build();
        button4.setBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.holo_red_dark, null));

        SubActionButton button5 = itemBuilder.build();
        SubActionButton button6 = itemBuilder.build();

        FloatingActionButton fab = new FloatingActionButton.Builder(this, true)
                .build();
        fab.setPosition(200, 300);
        fab.setIconResource(android.R.drawable.ic_dialog_email);
        fab.setBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.holo_red_light, null));

        int w = button1.getRadius();
        int h = button1.getRadius();

        FloatingActionMenu menu = new FloatingActionMenu.Builder(this, true)
                .addSubActionView(button1, w, h)
                .addSubActionView(button2, w, h)
                .addSubActionView(button4, w, h)
                .addSubActionView(button3, w, h)
                .addSubActionView(button5, w, h)
                .addSubActionView(button6, w, h)
                .attachTo(fab)
                .setStartAngle(0)
                .setEndAngle(360)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
}
