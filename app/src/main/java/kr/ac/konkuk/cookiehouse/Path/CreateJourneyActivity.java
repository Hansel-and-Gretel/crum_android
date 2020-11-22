package kr.ac.konkuk.cookiehouse.Path;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

public class CreateJourneyActivity extends AppCompatActivity {

    private static final String TAG = "CreateJourneyActivity";
//    private static final int ACTIVITY_NUM = 1;


    // TODO: freqency settings
    public static final String[] locateFrequencyOptions = {"5", "10", "15", "20"};
    public int frequencySetting = -1;

    // TODO: category list and accompany types
    // for spinner.. (임시)
//    private static final ArrayList<String> TYPES = new ArrayList<>();
//    private static final ArrayList<String> PARTY = new ArrayList<>();
    ArrayAdapter<CharSequence> typesAdapter;
    ArrayAdapter<CharSequence> partyAdapter;

    EditText journeyName;
    Spinner journeyType;
    Spinner journeyWith;
    Button frequencyBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journey_new);

        journeyName = findViewById(R.id.input_journey_name);
        journeyType = findViewById(R.id.input_journey_type);
        journeyWith = findViewById(R.id.input_journey_with);
        frequencyBtn = findViewById(R.id.input_locate_frequency);

        SpinnerOnSelectedListener onSelectedListener = new SpinnerOnSelectedListener();
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        journeyType.setOnItemSelectedListener(onSelectedListener);
        journeyWith.setOnItemSelectedListener(onSelectedListener);
        frequencyBtn.setOnClickListener(onClickListener);

        typesAdapter = ArrayAdapter.createFromResource(this, R.array.journey_type, R.layout.support_simple_spinner_dropdown_item);
        partyAdapter = ArrayAdapter.createFromResource(this, R.array.journey_with, R.layout.support_simple_spinner_dropdown_item);
        journeyType.setAdapter(typesAdapter);
        journeyWith.setAdapter(partyAdapter);
    }

    class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (view.getId()) {
                case R.id.input_journey_type:
                    //category = categoryList.get(position).toString();
                    break;
                case R.id.input_journey_with:
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }


    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.input_locate_frequency:
                    final Dialog frequencyDialog = new Dialog(CreateJourneyActivity.this);
                    frequencyDialog.setContentView(R.layout.layout_pin_frequency_dialog);

                    Button okBtn = (Button) frequencyDialog.findViewById(R.id.set_btn);
                    Button cancelBtn = (Button) frequencyDialog.findViewById(R.id.cancel_btn);

                    final NumberPicker frequencyPicker = (NumberPicker) frequencyDialog.findViewById(R.id.frequency_picker);
                    frequencyPicker.setMinValue(0);
                    frequencyPicker.setMaxValue(locateFrequencyOptions.length-1);
                    frequencyPicker.setDisplayedValues(locateFrequencyOptions);
                    frequencyPicker.setDescendantFocusability(NumberPicker.FOCUS_BEFORE_DESCENDANTS);
                    frequencyPicker.setWrapSelectorWheel(false);

                    // TODO
                    frequencyPicker.setValue(1);        // default: 10 min

                    frequencyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            frequencySetting = Integer.parseInt(locateFrequencyOptions[frequencyPicker.getValue()]);
                            frequencyBtn.setText(frequencySetting + " min");
                            frequencyBtn.setTextSize(18);
                            frequencyDialog.dismiss();
                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            frequencyDialog.dismiss();
                        }
                    });
                    frequencyDialog.show();
                    break;
            }
        }
    }

    /*
     * Bottom Navigation view setup
     * */
//    private void setupBottomNavigationView(){
//        Log.d(TAG,"SetupBottomNavigationView : setting up BottomNavigationView");
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//
//    }


}
