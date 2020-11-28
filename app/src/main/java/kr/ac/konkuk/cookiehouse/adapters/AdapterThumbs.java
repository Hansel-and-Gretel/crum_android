package kr.ac.konkuk.cookiehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Social.JourneyDetailActivity;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;

public class AdapterThumbs extends RecyclerView.Adapter<AdapterThumbs.MyHolder> {

    private Context context;
    private List<ModelJourney> mJourneys;

    public AdapterThumbs(Context context, List<ModelJourney> mJourneys) {
        this.context = context;
        this.mJourneys = mJourneys;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thumb, viewGroup, false);

        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

//        ModelJourney journey = mJourneys.get(i);

        String jImage =mJourneys.get(i).getImage();
        int jId = mJourneys.get(i).getId();

        //포스트 이미지 설정
        try{
            Picasso.get().load(jImage).placeholder(R.drawable.test_image).into(myHolder.post_image);
        }catch (Exception e){

        }


        // button click handlers
        myHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 선택하면 포스트 상세 페이지 postdetail activity
                Intent intent = new Intent(context, JourneyDetailActivity.class);
                intent.putExtra("journeyId",jId); // 해당 포스트가 클릭될때 해당 id로 포스트 디테일을 가져옴
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "나는 저니 몇개?"+mJourneys.size());
        return mJourneys.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView post_image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ModelJourney> getmJourneys() {
        return mJourneys;
    }

    public void setmJourneys(List<ModelJourney> mJourneys) {
        this.mJourneys = mJourneys;
    }
}
