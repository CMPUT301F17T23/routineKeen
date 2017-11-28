package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-24.
 */

public class MapFilter extends AppCompatActivity{
    Button noFilterBtn;
    Boolean radiusBool;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        noFilterBtn = (Button)findViewById(R.id.filterNoFilterBtn);
        noFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapFilter.this, MapsActivity.class);
                i.putExtra("radBool", false);
                startActivity(i);
                finish();
            }
        });
        try {
            radiusBool = getIntent().getExtras().getBoolean("radBool");
            if (radiusBool) {
                CheckBox cB = (CheckBox) findViewById(R.id.radiusCheckBox);
                cB.setChecked(true);
            }
        } catch (Exception e) {
            System.out.print("Pass");
        }

    }

    public void onFilterCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.personalCheckBox:
                if (checked) // include personal events
                    Toast.makeText(this, "Personal Option Checked", Toast.LENGTH_SHORT).show();
                else // do not include personal events
                    Toast.makeText(this, "Personal Option Unchecked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.followingCheckBox:
                if (checked) // include following events
                    Toast.makeText(this, "Following Option Checked", Toast.LENGTH_SHORT).show();
                else //do not include following events
                    Toast.makeText(this, "Following Option Unchecked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.recentCheckBox:
                if (checked) // include only recent events
                    Toast.makeText(this, "Recent Events Option Checked", Toast.LENGTH_SHORT).show();
                else //include all events
                    Toast.makeText(this, "Recent Events Option Unchecked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radiusCheckBox:
                if (checked) { // include only recent radius
                    radiusBool = true;
                    Toast.makeText(this, "Radius Filter Checked", Toast.LENGTH_SHORT).show();
                    Log.d("TOLU0", "HERE " + String.valueOf(radiusBool));
                }
                else{
                    radiusBool = false;
                    Toast.makeText(this, "Radius Filter Unchecked", Toast.LENGTH_SHORT).show();
                    Log.d("TOLU0", "HERE " + String.valueOf(radiusBool));
                }
                break;
        }
    }

//    /**
//     * If a participant wants to highlight events within 5 km of his/her current location,
//     * set eventRadius to 5.
//     * If nothing was specified, return negative value.
//     */
//    public void setRadius() {
//        range = (EditText)findViewById(R.id.filterRange);
//        String rangeStr = range.getText().toString();
//        if (rangeStr.matches("")) {
//            eventRadius = -1;
//        }
//        else {
//            eventRadius = Integer.parseInt(rangeStr);
//        }
//        // to pass eventRadius to string, do: (eventRadius+"")
//        // toString() only works with Object types
//        // eventRadius is an int, which is a primitive type.... you need to use + ""
//    }

    /**
     * Apply filter to the map view
     * @param view
     */
    public void applyFilter(View view) {
        Intent i = new Intent(MapFilter.this, MapsActivity.class);
        Toast.makeText(this, "Filter Applied!", Toast.LENGTH_SHORT).show();
        i.putExtra("radBool", radiusBool);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // save changes
    }
}
