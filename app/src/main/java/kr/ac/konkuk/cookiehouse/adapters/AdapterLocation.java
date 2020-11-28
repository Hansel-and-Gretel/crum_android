package kr.ac.konkuk.cookiehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kr.ac.konkuk.cookiehouse.R;

public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.MyViewHolder> {


    private Context context;
    private ArrayList place_id, place_name, place_time, place_lon, place_lat;

    public AdapterLocation(Context context, ArrayList place_id, ArrayList place_name, ArrayList place_time, ArrayList place_lon, ArrayList place_lat) {
        this.context = context;
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_time = place_time;
        this.place_lon = place_lon;
        this.place_lat = place_lat;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_location, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.pIdTv.setText(String.valueOf(place_id.get(i)));
        holder.pNameTv.setText(String.valueOf(place_name.get(i)));
        holder.pTimeTv.setText(String.valueOf(place_time.get(i)));
        holder.pLonTv.setText(String.valueOf(place_lon.get(i)));
        holder.pLatTv.setText(String.valueOf(place_lat.get(i)));



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
