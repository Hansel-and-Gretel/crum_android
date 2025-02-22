package kr.ac.konkuk.cookiehouse.Path;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.General.StartActivity;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

import static kr.ac.konkuk.cookiehouse.models.ModelJourney.currentJourney;


public class CreateJourneyActivity extends AppCompatActivity {

    private static final String TAG = "CreateJourneyActivity";
    SharedPreferences appData;

    RetrofitConnection retrofitConnection = new RetrofitConnection();
    RetrofitInterface retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);


    // TODO: freqency settings
    public static final String[] locateFrequencyOptions = {"5", "10", "15", "20"};
    public int frequencySetting = -1;

    // TODO: category list and accompany types
    ArrayAdapter<CharSequence> typesAdapter;
    ArrayAdapter<CharSequence> partyAdapter;

    EditText journeyName;
    Spinner journeyType;
    Spinner journeyWith;
    Button frequencyBtn;
    Button createJourneyBtn;

    // TODO 잠깐
    //ModelJourney modelJourney;
    String name = "";
    String type = null;
    String party = "";
    int frequency = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journey_new);

        journeyName = findViewById(R.id.input_journey_name);
        journeyType = findViewById(R.id.input_journey_type);
        journeyWith = findViewById(R.id.input_journey_with);
        frequencyBtn = findViewById(R.id.input_locate_frequency);
        createJourneyBtn = findViewById(R.id.btn_start_new_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        frequencyBtn.setOnClickListener(onClickListener);

        typesAdapter = ArrayAdapter.createFromResource(this, R.array.journey_type, R.layout.support_simple_spinner_dropdown_item);
        partyAdapter = ArrayAdapter.createFromResource(this, R.array.journey_with, R.layout.support_simple_spinner_dropdown_item);
        journeyType.setAdapter(typesAdapter);
        journeyWith.setAdapter(partyAdapter);
        journeyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = journeyType.getSelectedItem().toString();
                Toast.makeText(CreateJourneyActivity.this, type, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        journeyWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                party = journeyWith.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

                            // model에 저장
                            frequency = frequencySetting;
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

     //새로운 Journey 저장 (xml 상에서 onClick 구현되어있음)
    public void uploadJourney(View view) {        // 지금은 카테고리만 저장하는 역할(설정값 저장할게 이것 밖에없음 아직), 나중에 알림기능이나, 통계 on/off 블라블라
        name = journeyName.getText().toString();
        if(name.isEmpty() || type.isEmpty() || party.isEmpty() || frequency <= 0){
            // 하나라도 입력X
            Log.i("이거", name+" "+type+" "+frequency);
            Toast.makeText(CreateJourneyActivity.this, "Fill out all the forms", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // 각 Field에 값 입력
            // TODO: 중요한 변경사항::: status의 의미> true==입력가능,아직완료안된상태의 journey / false==summary까지 입력되고 난 완료된 journey
            Call<ModelJourney> call = retrofitInterface.createJourney(name, type, party, frequency, true, false, ModelUser.USER.getId(), ModelUser.USER.getUserName());


            call.enqueue(new Callback<ModelJourney>() {
                @Override
                public void onResponse(Call<ModelJourney> call, Response<ModelJourney> response) {
                    if(response.code() == 200){
                        Toast.makeText(CreateJourneyActivity.this, "Journey Created", Toast.LENGTH_SHORT).show();
                        saveNewJourney();
                        Intent intent = new Intent(CreateJourneyActivity.this, RecordJourneyActivity.class);
                        startActivity(intent);
                    } else if(response.code() == 400) {
                        Toast.makeText(CreateJourneyActivity.this, "Journey creation failed", Toast.LENGTH_LONG).show();
                        Log.i("아아아ㅏㅇ", response.message());

                    }
                }

                @Override
                public void onFailure(Call<ModelJourney> call, Throwable t) {
                    Toast.makeText(CreateJourneyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }

    }

    // Save new journey to 'currentJourney'
    private void saveNewJourney() {
        ModelJourney currentJourney = new ModelJourney();
        currentJourney.name = name;
        currentJourney.type = type;
        currentJourney.party = party;
        currentJourney.frequency = frequency;
        currentJourney.status = true;
        currentJourney.shared = false;

        ModelJourney.setCurrentJourney(currentJourney);
    }



}
