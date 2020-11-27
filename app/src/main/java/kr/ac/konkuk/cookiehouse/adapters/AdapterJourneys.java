package kr.ac.konkuk.cookiehouse.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import retrofit2.Callback;


public class AdapterJourneys extends RecyclerView.Adapter<AdapterJourneys.MyHolder> {

    Context context;
    List<ModelJourney> journeyList;

    public AdapterJourneys(Context context, List<ModelJourney> journeyList) {
        this.context = context;
        this.journeyList = journeyList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        // item_journey
        ImageView jImageIv;
        TextView uNameTv, jTypeTv,jTitleTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            uNameTv = itemView.findViewById(R.id.post_writer);
            jTitleTv = itemView.findViewById(R.id.post_name);
            jTypeTv = itemView.findViewById(R.id.post_field);
            jImageIv = itemView.findViewById(R.id.post_image);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_journey, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        // get data
        String uName = journeyList.get(i).getUserName();
        String jName = journeyList.get(i).getName();
        String jType = journeyList.get(i).getType();
        String jImage = journeyList.get(i).getImage();

        myHolder.uNameTv.setText(uName);
        myHolder.jTitleTv.setText(jName);
        myHolder.jTypeTv.setText(jType);

        //포스트 이미지 설정
        try{
            Picasso.get().load(jImage).placeholder(R.drawable.test_image).into(myHolder.jImageIv);
        }catch (Exception e){

        }



    }

    @Override
    public int getItemCount() {
        Log.d("tAG", "getItemCount: 아이템 개수" + journeyList.size());
        return journeyList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ModelJourney> getJourneyList() {
        return journeyList;
    }

    public void setJourneyList(List<ModelJourney> journeyList) {
        this.journeyList = journeyList;
    }


}
