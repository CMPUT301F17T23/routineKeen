package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.R;

/**
 * User has the option of filtering the markers he/she would like to see on the map.
 * Created by tiakindele on 2017-11-24.
 */

public class MapFilter extends AppCompatActivity{
    Button noFilterBtn;     // button user clicks to cancel result filter
    Boolean radiusBool;     // true if the user only wants nearby events shown

    /**
     * On create, initialize page.
     * Also, check if checkboxes are supposed to be checked or not.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        noFilterBtn = (Button)findViewById(R.id.filterNoFilterBtn);
        noFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapFilter.this, MapsActivity.class);
                i.putExtra("radBool", false);       // make sure the nearby filter is OFF
                startActivity(i);
                finish();
            }
        });

        // check if the nearby events filter was previously selected
        // if it was, select it now
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

    /**
     * When a checkbox is clicked on the map filter page, handle it here
     * @param view
     */
    public void onFilterCheckboxClicked(View view) {

        // True if the box has been checked
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
                }
                else{
                    radiusBool = false;
                    Toast.makeText(this, "Radius Filter Unchecked", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * Apply filter to the map view.
     * Send the user's choice to only see nearby events to MapsActivity.class through putExtra
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
