package ca.ualberta.cs.routinekeen.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.ualberta.cs.routinekeen.R;

/**
 * Created by tiakindele on 2017-11-24.
 */

public class MapFilter extends AppCompatActivity{
    Button cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        cancel = (Button)findViewById(R.id.filterCancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapFilter.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Apply filter to the map view
     * @param view
     */
    public void applyFilter(View view) {
        Intent i = new Intent(MapFilter.this, MapsActivity.class);
        Toast.makeText(this, "Filter Applied!", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}
