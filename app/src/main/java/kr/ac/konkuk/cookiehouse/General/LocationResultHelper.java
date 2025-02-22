package kr.ac.konkuk.cookiehouse.General;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import kr.ac.konkuk.cookiehouse.models.Places;
import kr.ac.konkuk.cookiehouse.DataStorage.PlacesDBControl;
import kr.ac.konkuk.cookiehouse.Path.RecordJourneyActivity;
import kr.ac.konkuk.cookiehouse.R;

public class LocationResultHelper {

    /*이쪽에서 디비작업 진행하면 될듯
    *
    * mLocationList : location 리스트 (사실 현재는 한번에 하나 받아옴)
    * mLocationList.size() : 로케이션 리스트안의 리스트개수 : 현재 1개
    *
    *
    * 필요한정보
    * location.getTime()
    * location.getLatitude()
    * location.getLongitude()
    *
    * */


    /* HERE YOU ARE */
    // To. 다른 곳에서 db 쓸 일 있을 찬희에게
    // 1. 이렇게 먼저 이 클래스 객체를 생성하고
    PlacesDBControl placesDB;

    public void insertPlacesInfo() {
        // 2. 요기서 액티비티 context 꼭 넘겨주기!
        placesDB = new PlacesDBControl(mContext);
        Location location = mLocationList.get(0);
        // 3. Places model(이건 나중에 내가 따로 옮겨놓을겝, 일단 냅둬주랍)
        Places place = new Places(0, null, location.getTime(), (float)location.getLongitude(), (float)location.getLatitude(), null, null, null, false);
        // 4. DBControl 클래스에서 함수 맘대로 가져다 쓰면댕
        boolean checkDB = placesDB.insert(place);
        if(checkDB){
            Log.i("Saved to DB", String.valueOf(location.getTime()));
        } else {
            Log.i("DB 저장 실패", String.valueOf(location.getTime()));
        }
    }


    public static final String KEY_LOCATION_RESULTS = "key-location-results";
    private Context mContext;
    private List<Location> mLocationList;

    public LocationResultHelper(Context mContext, List<Location> mLocationList) {
        this.mContext = mContext;
        this.mLocationList = mLocationList;
    }

    public String getLocationResultText() {

        if (mLocationList.isEmpty()) {
            return "Location not received";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Location location : mLocationList) {
                sb.append("TIME:");
                sb.append(location.getTime());
                sb.append("Latitude:");
                sb.append(location.getLatitude());
                sb.append("Longitude: ");
                sb.append(location.getLongitude());
                sb.append("\n");
            }
            return sb.toString();
        }

    }

    private CharSequence getLocationResultTitle() {

        //개수
        String result = mContext.getResources().getQuantityString
                (R.plurals.num_locations_reported, mLocationList.size(), mLocationList.size());

        return result + " : " + DateFormat.getDateTimeInstance().format(new Date());
    }

    public void showNotification() {

        Intent notificationIntent = new Intent(mContext, RecordJourneyActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);

        // Add the main Activity to the task stack as the parent.
//        stackBuilder.addParentStack(RecordJourneyActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(mContext,
                App.CHANNEL_ID)
                .setContentTitle(getLocationResultTitle())
                .setContentText(getLocationResultText())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(notificationPendingIntent);

        getNotificationManager().notify(0, notificationBuilder.build());

    }

    private NotificationManager getNotificationManager() {

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;

    }

    // 저장하기
    public void saveLocationResults() {
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit()
                .putString(KEY_LOCATION_RESULTS, getLocationResultTitle() + "\n" +
                        getLocationResultText())
                .apply();
        insertPlacesInfo();
    }


    public static String getSavedLocationResults(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_RESULTS, "default value");

    }




}
