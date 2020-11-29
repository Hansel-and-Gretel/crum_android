package kr.ac.konkuk.cookiehouse.Path;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kr.ac.konkuk.cookiehouse.DataStorage.ImageFile;
import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Social.JourneyDetailActivity;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJourneyActivity extends AppCompatActivity {

    private static final String TAG = "PostJourneyActivity";
    RetrofitInterface retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);

    ArrayAdapter<CharSequence> typesAdapter;
    ArrayAdapter<CharSequence> partyAdapter;

    TextView journeyName;
    Spinner journeyType;
    Spinner journeyWith;
    EditText journeySummary;
    ImageView journeyImageBtn;

    Button finishJourneyBtn;


    String name = "";
    String type = "";
    String party = "";
    String summary = "";
    String imagePath = "";


    //11.07 갤러리 사진 가져오는 것을 위해서 / 카메라 사진 촬영 가져오는 것을 위해서
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    //11.15 https://developer.android.com/reference/androidx/core/content/FileProvider#SpecifyFiles
    private File tempFile;      // 사진 파일


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_journey);

        journeyName = findViewById(R.id.input_journey_name);
        journeyType = findViewById(R.id.input_journey_type);
        journeyWith = findViewById(R.id.input_journey_with);
        journeySummary = findViewById(R.id.summary);
        journeyImageBtn = findViewById(R.id.journey_image);

        finishJourneyBtn = findViewById(R.id.btn_finish_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        journeyName.setOnClickListener(onClickListener);
        journeyImageBtn.setOnClickListener(onClickListener);

        journeyName.setText(ModelJourney.getCurrentJourney().name);
        // 초기값 유지를 위해 추가로 선언
        List<String> typesList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.journey_type)));
        List<String> partyList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.journey_with)));


        typesAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, typesList);
        partyAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, partyList);
        journeyType.setAdapter(typesAdapter);
        journeyType.setSelection(typesList.indexOf(ModelJourney.getCurrentJourney().type));
        journeyWith.setAdapter(partyAdapter);
        journeyWith.setSelection(partyList.indexOf(ModelJourney.getCurrentJourney().party));

        journeyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = journeyType.getSelectedItem().toString();
                Toast.makeText(PostJourneyActivity.this, type, Toast.LENGTH_SHORT).show();
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
            switch (v.getId()) {
                case R.id.input_journey_name:
                    final Dialog changeNameDialog = new Dialog(PostJourneyActivity.this);
                    changeNameDialog.setContentView(R.layout.layout_change_journey_name_dialog);
                    EditText inputName = changeNameDialog.findViewById(R.id.new_category_name);
                    Button createBtn = changeNameDialog.findViewById(R.id.create_category_btn);
                    Button cancelBtn = changeNameDialog.findViewById(R.id.cancel_btn);
                    createBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String newJourneyName = inputName.getText().toString();
                            if(!newJourneyName.isEmpty()){
                                journeyName.setText(newJourneyName);
                                changeNameDialog.dismiss();
                            }
                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeNameDialog.dismiss();
                        }
                    });
                    changeNameDialog.show();
                    break;

                case R.id.journey_image:
                    final Dialog photoDialog = new Dialog(PostJourneyActivity.this);
                    photoDialog.setContentView(R.layout.layout_photo_dialog);

                    Button galleryPhotoBtn = photoDialog.findViewById(R.id.gallery_photo_btn);
                    Button cameraPhotoBtn = photoDialog.findViewById(R.id.camera_photo_btn);
                    Button deletePhotoBtn = photoDialog.findViewById(R.id.delete_photo_btn);

                    galleryPhotoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPhoto();
                            photoDialog.dismiss();
                        }
                    });
                    cameraPhotoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                takePhoto();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            photoDialog.dismiss();
                        }
                    });
                    deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            journeyImageBtn.setImageBitmap(null);
                            photoDialog.dismiss();
                        }
                    });
                    photoDialog.show();
                    break;

            }
        }
    }

    //새로운 Journey 저장 (xml 상에서 onClick 구현되어있음)
    public void uploadJourney(View view) {        // 지금은 카테고리만 저장하는 역할(설정값 저장할게 이것 밖에없음 아직), 나중에 알림기능이나, 통계 on/off 블라블라
        name = journeyName.getText().toString();
        if (name.isEmpty() || type.isEmpty() || party.isEmpty()) {
            // 하나라도 입력X
            Log.i("이거", name + " " + type + " " + party);
            Toast.makeText(PostJourneyActivity.this, "Fill out all the forms", Toast.LENGTH_SHORT).show();
            return;
        } else if(summary.length() >= 200) {
            Toast.makeText(PostJourneyActivity.this, "Summary is too long (max:200)", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // 각 Field에 값 입력
            // 중요한 변경사항::: status의 의미> true==입력가능,아직완료안된상태의 journey / false==summary까지 입력되고 난 완료된 journey
            // todo 지우ㅓㅓㅓㅓㅓ알아ㅓㄴㅇ리ㅏㄴ어리ㅏ
            if(!imagePath.isEmpty()){
                Log.i("사진 경로", imagePath);
            }
            Call<ModelJourney> call = retrofitInterface.uploadFinishedJourney(name, type, party, ModelJourney.getCurrentJourney().frequency, summary, imagePath, false, false, ModelUser.USER.getId(), ModelUser.USER.getUserName());


            call.enqueue(new Callback<ModelJourney>() {
                @Override
                public void onResponse(Call<ModelJourney> call, Response<ModelJourney> response) {
                    if (response.code() == 200) {
                        Toast.makeText(PostJourneyActivity.this, "Journey Uploaded!", Toast.LENGTH_SHORT).show();
                        //uploadPath();   // TODO 위치도 여기 확정ㅇ ㅏ님
                        // 1. path 업로드
                        ModelJourney journeyResult = response.body();
                        Intent intent = new Intent(PostJourneyActivity.this, JourneyDetailActivity.class);
                        intent.putExtra("journeyId", journeyResult.getId());
                        startActivity(intent);
                    } else if (response.code() == 400) {
                        Toast.makeText(PostJourneyActivity.this, "Journey creation failed", Toast.LENGTH_LONG).show();
                        Log.i("아아아ㅏㅇ", response.message());

                    }
                }

                @Override
                public void onFailure(Call<ModelJourney> call, Throwable t) {
                    Toast.makeText(PostJourneyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }

    }

    // 갤러리 접속 또는 카메라 키기
    public void getPhoto(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(galleryIntent, PICK_FROM_ALBUM);
    }


    public void takePhoto() throws IOException {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            tempFile = ImageFile.createImageFile();          // 사진 저장할 extra 파일
        } catch (IOException e){
            e.printStackTrace();
        }

        if(tempFile != null){
            Uri photoUri = FileProvider.getUriForFile(this, "kr.ac.konkuk.cookiehouse.fileprovider",tempFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            imagePath = tempFile.getName();

            startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
        }
    }

//    private File createImageFile() throws IOException {     // 카메라 추가인 경우
//        // 이미지 파일 이름 (CODE: 포토) 즉 DB의 photo에 들어갈 것: "trailJourney_(name)_(time).jpg")
//        String timeStamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
//        String ImageFileName = "trailJourney_"+journeyName.getText()+"_"+timeStamp;
//
//        // 이미지 저장될 폴더 이름, xml/filepaths참고 (Android/data/com.example.zekak/files/)
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        if(!storageDir.exists()){   // 없으면 만듦
//            storageDir.mkdirs();
//        }
//
//        // 빈 파일 생성
//        File image = File.createTempFile(ImageFileName, ".jpg", storageDir);
//        return image;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Photo from gallery
        if(requestCode == PICK_FROM_ALBUM) {
            if(resultCode != RESULT_OK) return;
            Uri photoUri = data.getData();      // 갤러리에서 선택한 이미지의 Uri 받아옴
            // (CODE: 포토) photoUri 형태: content://(도메인 com.example.zekak).fileprovider/photos/zekak_(시간).jpg.
            Cursor cursor = null;               // cursor를 통해 스키마를 content://에서 file://로 변경할 것임 (사진이 저장된 절대경로 받아오는 과정)

            try {
                String[] proj = {MediaStore.Images.Media.DATA };
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));    // 사진을 임시파일에 저장, 뷰 thumbnail위해서
            } finally {
                if(cursor != null) {
                    cursor.close();
                }
            }

            try{
                setImage(true);         // 갤러리 사진 뷰에 적용, 복사본 생성
            } catch (IOException e){}
        }

        // Photo from camera
        if(requestCode == PICK_FROM_CAMERA) {
            //Uri photoUri = data.getData();
            if (resultCode == RESULT_OK) {      // 이부분 https://developer.android.com/training/camera/photobasics?hl=ko#java 참고
                assert data != null;
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                journeyImageBtn.setImageBitmap(imageBitmap);
                galleryAddPic();
            }
        }

        // ERROR 처리
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
        }
    }



    private void setImage(boolean makeCopy) throws IOException {
        // 사진의 절대경로 불러와 bitmap 파일로 변형 --> ImageView에 이미지 넣음
        Bitmap originalBm = null;          // 변환된 bitmap
        if(Build.VERSION.SDK_INT >= 29){
            try {
                originalBm = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), Uri.fromFile(tempFile))); // 이 파일 내용을 bitmap으로 decode
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                originalBm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(tempFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if(originalBm != null){
            journeyImageBtn.setImageBitmap(originalBm);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
