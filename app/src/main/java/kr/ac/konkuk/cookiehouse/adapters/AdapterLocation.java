package kr.ac.konkuk.cookiehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import kr.ac.konkuk.cookiehouse.Path.PlaceDetailActivity;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Social.JourneyDetailActivity;

public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.MyViewHolder> {


    private Context context;
    private ArrayList  place_id, place_name, place_time, place_lon, place_lat, place_photo, place_note, place_category, place_status;

    public AdapterLocation(Context context, ArrayList place_id, ArrayList place_name, ArrayList place_time, ArrayList place_lon, ArrayList place_lat, ArrayList place_photo, ArrayList place_note, ArrayList place_category, ArrayList place_status) {
        this.context = context;
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_time = place_time;
        this.place_lon = place_lon;
        this.place_lat = place_lat;
        this.place_photo = place_photo;
        this.place_note = place_note;
        this.place_category = place_category;
        this.place_status = place_status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_location, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myHolder, int i) {



        int placeId = Integer.parseInt(String.valueOf(place_id.get(i)));
        String name = String.valueOf(place_name.get(i));

        String time = String.valueOf(place_time.get(i));
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        String timestamp = DateFormat.format("yyyy/MM/dd hh:mm aa", calendar).toString();
        String lon = String.valueOf(place_lon.get(i));
        String lat = String.valueOf(place_lat.get(i));
        String photo = String.valueOf(place_photo.get(i));
        String note = String.valueOf(place_note.get(i));
        String category = String.valueOf(place_category.get(i));
        String status = String.valueOf(place_status.get(i));




        myHolder.pIdTv.setText(String.valueOf(place_id.get(i)));
        myHolder.pNameTv.setText(name);
        myHolder.pTimeTv.setText(timestamp);
        myHolder.pLonTv.setText(lon);
        myHolder.pLatTv.setText(lat);



        // button click handlers
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 선택하면 포스트 상세 페이지 postdetail activity
//                Toast.makeText(context, ""+placeId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PlaceDetailActivity.class);
                intent.putExtra("id",placeId); // 해당 포스트가 클릭될때 해당 id로 포스트 디테일을 가져옴
                Log.d("log", "onClick:보내요 "+placeId);
                intent.putExtra("name",name);
                intent.putExtra("timestamp",timestamp);
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                intent.putExtra("photo",photo);
                intent.putExtra("note",note);
                intent.putExtra("category",category);
                intent.putExtra("status",status);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return place_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pIdTv, pNameTv, pTimeTv, pLonTv, pLatTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pIdTv = itemView.findViewById(R.id.placeId);
            pNameTv = itemView.findViewById(R.id.pName);
            pTimeTv = itemView.findViewById(R.id.timeTv);
            pLonTv = itemView.findViewById(R.id.lonTv);
            pLatTv= itemView.findViewById(R.id.latTv);


        }
    }
}
