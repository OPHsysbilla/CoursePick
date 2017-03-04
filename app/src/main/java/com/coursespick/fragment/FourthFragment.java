package com.coursespick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursespick.R;

/**
 * Created by eva on 2017/2/21.
 */

public class FourthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static FourthFragment newInstance() {
        FourthFragment fourthFragment = new FourthFragment();
        Bundle bundle = new Bundle();
        fourthFragment.setArguments(bundle);
        return fourthFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.first_fragment_main, container, false);
        Bundle bundle = getArguments();
        return rootview;
    }

    @Override
    public void onRefresh() {

    }
}