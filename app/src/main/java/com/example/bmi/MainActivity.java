package com.example.bmi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText vWeight;
    Button submitButton, aboutButton;
    ImageView imageView;
    String[] feetArray, inchesArray;
    int feet, inches;

    Spinner spinnerFeet, spinnerInches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//-- get views
        vWeight = (EditText) findViewById(R.id.weightET);

        feetArray = getResources().getStringArray(R.array.feet);
        inchesArray = getResources().getStringArray(R.array.inches);
        spinnerFeet = (Spinner) findViewById(R.id.spinner_feet);
        ArrayAdapter<String> adapterFeet = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, feetArray);
        spinnerFeet.setAdapter(adapterFeet);

        spinnerInches = (Spinner) findViewById(R.id.spinner_inches);
        ArrayAdapter<String> adapterInches = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, inchesArray);
        spinnerInches.setAdapter(adapterInches);

        spinnerFeet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                feet = arg0.getSelectedItemPosition() + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerInches.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                inches = arg0.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
        aboutButton = (Button) findViewById(R.id.aboutBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        registerForContextMenu(imageView);
    }

    public void calcBMI(View view) {
        String weight = vWeight.getText().toString();
        Intent intent = new Intent(this, ReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("feet", feet);
        bundle.putInt("inches", inches);
        bundle.putString("weight", weight);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void showAbout(View view) {
        openOptionsDialog();
    }


    // --- Option Menu ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                openOptionsDialog();
                return true;
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;
    }

    public void openOptionsDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.menu_about)
                .setMessage(R.string.about_msg)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int which) {
                            }
                        }).show();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                openOptionsDialog();
                return true;
            case R.id.menuItemWiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
        }
        return false;
    }
}
