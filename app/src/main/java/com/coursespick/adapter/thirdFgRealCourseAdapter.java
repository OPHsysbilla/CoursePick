package com.coursespick.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.coursespick.MainActivity;
import com.coursespick.R;
import com.coursespick.fragment.realCourse.realCourseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eva on 2017/3/2.
 */

public class thirdFgRealCourseAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public ThirdToFirstCallBack callBack;
    static public List<realCourseData> datashow = new ArrayList<>() ;
    //type
    public static final int TYPE_EMPTY = 0xff01;
    public static final int TYPE_COURSEINFO = 0xff02;
    public thirdFgRealCourseAdapter(Context context,List<realCourseData> d) {
        this.context = context;
        try {
            callBack = (MainActivity)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must imlement listener.");
        }
        if(d == null)
            d= new ArrayList<>();
//        realCourseData[] temp = activity.;
        datashow = d;
        Log.d("thirdAdapter:","initialize - datasize:"+String.valueOf(datashow.size())+"comes in.");
    }

    public thirdFgRealCourseAdapter(Context context) {
        this.context = context;
        try {
            callBack = (MainActivity)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must imlement listener.");
        }
    }
    public void addOne(realCourseData real){
        Boolean exists = false;
        if(real!=null) {
            // (上课时间，教师号，课程号)   的组合不存在的时候加入
            // 避免加入多个相同课程
            for(realCourseData data:datashow){
                String cid = data.getcId();
                String tid = data.gettId();
                String cTime = data.getcTime();
                if(real.getcId()==cid && real.gettId()==tid && cTime == real.getcTime()){
                    exists = true;
                    break;
                }
            }
            if(exists == false){
                datashow.add(0, real);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COURSEINFO:
                return new HolderTypeCourseInfo(LayoutInflater.from(parent.getContext()).inflate(R.layout.third_fragment_list_item, parent, false));
            case TYPE_EMPTY:
                return new HolderTypeEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.second_fragment_empty, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HolderTypeCourseInfo){
            bindTypeCourseInfo((HolderTypeCourseInfo)holder,position);
        }else if (holder instanceof HolderTypeEmpty) {
            bindTypeEmpty((HolderTypeEmpty) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return datashow.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if(datashow.size() == 0){
//            return TYPE_EMPTY;
//        }else{
            return TYPE_COURSEINFO;
//        }

   }
    public static List<realCourseData> getDatashow() {
        List<realCourseData> temp = datashow;
        return temp;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_EMPTY:
                        case TYPE_COURSEINFO:
                        default:
                            return gridManager.getSpanCount();
                    }
                }
            });
        }
    }

    /////////////////////////////
    private void bindTypeCourseInfo(HolderTypeCourseInfo holder, final int position){
        //填充布局每一行的数据

        holder.thicid.setText(datashow.get(position).getcId());
        holder.thitname.setText(datashow.get(position).gettName());
        holder.thiclasstime.setText(datashow.get(position).getcTime());
        holder.thibtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 加入一项到first fragment课表中显示
                realCourseData real = new realCourseData();
                real.settName(datashow.get(position).gettName());
                real.settId(datashow.get(position).gettId());

                //。。。。其他属性

                callBack.FirstAddOneClass(real);


            }
        });
        holder.thibtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在待选池中删除此课
                datashow.remove(position);
//                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    private void bindTypeEmpty(HolderTypeEmpty holder, int position) {
//
    }

//    private void bindType2(final HolderType2 holder, final int position){
//        String title = datashow.get(position).getCloth_title();
//        holder.item_txt_type2.setText(title);
//        String imageUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//        Glide.with(context)
//        .load(imageUrl)
//        .centerCrop()
//        .into(holder.item_img_type2);
//        holder.item_img_type2.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//            String imageUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("imageUrl",imageUrl);
//            bundle.putString("textTitle",datashow.get(position).getCloth_title());
//            intent.putExtras(bundle);
//            intent.setClass(context, ClothItemDetailsActivity.class);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
//            Pair.create((View)holder.item_img_type2, "cloth_details_image"));
//        //                ,Pair.create((View)holder.item_txt_type2, "cloth_details_title")
//            context.startActivity(intent, options.toBundle());
//            }
//            });
//        }


/////////////////////////////

    public class HolderTypeEmpty extends RecyclerView.ViewHolder {
        public ImageView item_img;

        public HolderTypeEmpty(View itemView) {
            super(itemView);
//          item_img =
        }
    }

    public class HolderTypeCourseInfo extends RecyclerView.ViewHolder {

//        private String id; //课程ID
//        private String courseName;//课名
//        private String tname;//教师名
//        private String tid;//教师号
//        private int section; //从第几节课开始
//        private int sectionSpan; //跨几节课
//        private int week; //周几
//        private String classRoom; //教室
//        private int courseFlag; //课程背景颜色
//        private int necessary;//是否是必修课
//
//
//        private String necessaryteacher; //必选老师
//        private String negativeteacher;//不选老师ok
//        private int negativetime;//不选课的时间———— 1：早上1-2节  2：中午5-6节   3: 下午9——10节  4：晚上11——13节
//        private String location;//校区ok
//        private String full;//选课人数是否限制ok
//        private int uppercredit;//学分上限ok
//        private String academy;//学院ok
//        private int typeofgeneral;//通识课类别———— 1：理工  2：人文  3：经管 ok

        private TextView thicid;
        private TextView thitname;
        private TextView thiclasstime;
        private ToggleButton thitogglebutton;
        private Button thibtnok;
        private Button thibtndelete;

        //list item里一列的控件绑定
        public HolderTypeCourseInfo(View itemView) {
            super(itemView);
            thicid=(TextView)itemView.findViewById(R.id.thi_fg_cid);
            thitname=(TextView)itemView.findViewById(R.id.thi_fg_tname);
            thiclasstime=(TextView)itemView.findViewById(R.id.thi_fg_ctime);
            thitogglebutton=(ToggleButton)itemView.findViewById(R.id.thi_fg_tb);
            thibtnok=(Button)itemView.findViewById(R.id.thi_fg_btn_ok);
            thibtndelete=(Button)itemView.findViewById(R.id.thi_fg_btn_delete);

        }
    }
    public interface ThirdToFirstCallBack{
        void FirstAutoArrange(List<realCourseData> realList);
        void FirstAddOneClass(realCourseData real);
    }

}


