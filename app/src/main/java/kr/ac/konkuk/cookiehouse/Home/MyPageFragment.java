package kr.ac.konkuk.cookiehouse.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.konkuk.cookiehouse.R;

/*
* Created by Chanhee on 10/17/2020
* */


public class MyPageFragment extends Fragment {
    private static final String TAG = "MyPageFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_mypage, container, false);

        return view;

    }
}
