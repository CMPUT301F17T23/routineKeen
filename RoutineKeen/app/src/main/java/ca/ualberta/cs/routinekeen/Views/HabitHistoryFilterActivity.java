package ca.ualberta.cs.routinekeen.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import ca.ualberta.cs.routinekeen.Controllers.HabitHistoryController;
import ca.ualberta.cs.routinekeen.Controllers.HabitListController;
import ca.ualberta.cs.routinekeen.Controllers.IOManager;
import ca.ualberta.cs.routinekeen.R;

/**
 * Created by Saddog on 11/29/2017.
 */

public class HabitHistoryFilterActivity extends AppCompatActivity {
    private Spinner spinner;
    private String filterType;
    private EditText filterComment;
    private static final int FILTER_BY_TYPE = 1;
    private static final int FILTER_BY_COMMENT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IOManager.initManager(getApplicationContext());

        spinner = (Spinner) findViewById(R.id.typeFilterSpinner);
        filterComment = (EditText) findViewById(R.id.commentFilterText);
        Button applyFilterByTypeButton = (Button) findViewById(R.id.applyFilterByTypeButton);
        Button applyFilterByCommentButton = (Button) findViewById(R.id.applyFilterByCommentButton);
        HabitListController.getHabitList();
        ArrayList<String> typeList = new ArrayList<String>(HabitListController.getTypeList());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                typeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                filterType =  (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        applyFilterByTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent output = new Intent();
                output.putExtra("FILTER TYPE", filterType);
                setResult(FILTER_BY_TYPE, output);
                finish();
            }
        });

        applyFilterByCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent output = new Intent();
                output.putExtra("FILTER TYPE", filterComment.getText().toString());
                setResult(FILTER_BY_COMMENT, output);
                finish();
            }
        });

    }
}
