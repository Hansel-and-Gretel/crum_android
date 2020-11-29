package kr.ac.konkuk.cookiehouse.DataStorage;

import android.content.Context;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.ac.konkuk.cookiehouse.General.App;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ImageFile extends AppCompatActivity {
    public static File storageDir;
    public static File createImageFile() throws IOException {     // 카메라 추가인 경우
        // 이미지 파일 이름 (CODE: 포토) 즉 DB의 photo에 들어갈 것: "trailJourney_(name)_(time).jpg")
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
        String ImageFileName = "trailJourney_"+ ModelJourney.getCurrentJourney().name+"_"+timeStamp;

        // 이미지 저장될 폴더 이름, xml/filepaths참고 (Android/data/com.example.zekak/files/)
        storageDir =  getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if(!storageDir.exists()){   // 없으면 만듦
            storageDir.mkdirs();
        }

        // 빈 파일 생성
        File image = File.createTempFile(ImageFileName, ".jpg", storageDir);
        return image;
    }
}
