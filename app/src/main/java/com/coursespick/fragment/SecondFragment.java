package com.coursespick.fragment;

import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.coursespick.MainActivity;
import com.coursespick.R;
import com.coursespick.Util.ScreenUtils;
import com.coursespick.adapter.secFgClassFilterAdapter;
import com.coursespick.fragment.realCourse.PostCourseData;
import com.coursespick.fragment.realCourse.realCourseData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by eva on 2017/2/21.
 */

public class SecondFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle = new Bundle();

        secondFragment.setArguments(bundle);
        return secondFragment;
    }
    FirstFragment firstFragment;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.second_fragment_main, container, false);
        Bundle bundle = getArguments();
        firstFragment = (FirstFragment)getFragmentManager().findFragmentByTag("firstFragment");
        mainActivity = (MainActivity)getActivity();

        InitBase();
        InitWidgetsFunction();
        InitPopUpWindow();
        return rootview;
    }



    private PopupWindow popupWindow;
    private View darkView;
    private View rootview;
    private Animation animIn, animOut;
    private RecyclerView mainList;
    private secFgClassFilterAdapter adpater ;
    private SwipeRefreshLayout refreshLayout;
    private TextView tabFilter;

    private Button btnReset,btnSubmit;
    private EditText secpopupcno;
    private EditText secpopupclassname;
    private EditText secpopupteachername;
    private EditText secpopupclasstime;
    private ToggleButton sectogg;
    private Spinner seccampus;

    private void InitBase(){
        darkView = rootview.findViewById(R.id.main_darkview);
        animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_anim);
        refreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.second_fragment_main_refresh);
        mainList = (RecyclerView) rootview.findViewById(R.id.second_fragment_main_list);
        tabFilter = (TextView)rootview.findViewById(R.id.second_fragment_tab_filter);
        tabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabFilterClick();
            }
        });
    }
    private void InitPopUpWindow(){
        popupWindow = new PopupWindow(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.second_fragment_popup, null);
        /*  init 弹出窗口 */
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new PaintDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setHeight(ScreenUtils.getScreenH(getActivity()) * 3  / 5);
        popupWindow.setWidth(ScreenUtils.getScreenW(getActivity()));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);
            }
        });

        /* init 弹出窗口（选课界面）的控件  */
        secpopupcno=(EditText)view.findViewById(R.id.sec_popup_cno);
        secpopupclassname=(EditText)view.findViewById(R.id.sec_popup_class_name);
        secpopupteachername=(EditText)view.findViewById(R.id.sec_popup_teacher_name);
        secpopupclasstime=(EditText)view.findViewById(R.id.sec_popup_class_time);
        sectogg=(ToggleButton)view.findViewById(R.id.sec_popup_toggle);
        seccampus=(Spinner)view.findViewById(R.id.sec_popup_campus);

        btnSubmit = (Button)view.findViewById(R.id.sec_popup_btn_ok);
        btnReset = (Button)view.findViewById(R.id.sec_popup_btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空
                secpopupclassname.setText("");
                secpopupcno.setText("");
                secpopupclasstime.setText("");
                secpopupteachername.setText("");
                sectogg.setChecked(false);
//                secpopupclasstime.setHint("格式：一7-8");
//                seccampus 没有重置
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //送出结果
                String classname = ""+secpopupclassname.getText().toString();
                String cno = ""+secpopupcno.getText().toString();
                String classtime =  ""+secpopupclasstime.getText().toString();
                String teachername = ""+secpopupteachername.getText().toString();
                String notFull = "";
                if(sectogg.isChecked()){
                    notFull = "true";
                }
                else{
                    notFull = "false";
                }
//                Log.d("1111111111", cno + "  "  + classname);
//                Log.d("1111111111", cno + "  "  + teachername);
//                Log.d("1111111111", cno + "  "  + classtime);
//                (String cId, String cName, String cCredit, String tId, String tName,
//                        String cTime, String cLocation, String cSize, String cNumber, String cCampus, String cLimit,
//                        String qaTime, String qaLocation){
                if(classtime.contains("格式"))
                    classname = "";
                realCourseData[] tempCourseList = PostCourseData.course_list(cno,classname,"","",teachername,"","","","","","","","");
                adpater.replaceAllData(tempCourseList);
                popupWindow.dismiss();
            }
        });
    }
    private void InitWidgetsFunction(){

//        一行的布局
        mainList.setLayoutManager(new LinearLayoutManager(mainList.getContext(), LinearLayoutManager.VERTICAL, false));
//        其他布局
//        mainList.setLayoutManager(new GridLayoutManager(mainList.getContext(), 6, GridLayoutManager.VERTICAL, false));
//        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setAutoMeasureEnabled(true);
        mainList.setItemAnimator(new DefaultItemAnimator());
        realCourseData[] tempCourseList = PostCourseData.course_list("","","","","","一1-2","","","","","","","");
        List<realCourseData> dataTemp = new ArrayList<>();
        for(realCourseData real:tempCourseList){
            dataTemp.add(real);
        }
        adpater = new secFgClassFilterAdapter(getActivity(),dataTemp);
        mainList.setAdapter(adpater);
    }
    private void tabFilterClick()
    {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(rootview.findViewById(R.id.main_div_line));
            popupWindow.setAnimationStyle(-1);
            //背景变暗
            darkView.startAnimation(animIn);
            darkView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
//                List<Cloth>a = adpater.queryAllCloth();
//                if(a.size()!=0){
//                    adapter.replaceAll(a);
//                }
                adpater.notifyDataSetChanged();
            }
        }, 1000);
    }


}