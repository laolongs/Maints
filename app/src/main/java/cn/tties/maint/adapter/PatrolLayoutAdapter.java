package cn.tties.maint.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.holder.PatrolInputBodyItemHolder;
import cn.tties.maint.holder.PatrolInputItemHolder;
import cn.tties.maint.holder.PatrolItemHolder;
import cn.tties.maint.httpclient.result.LoginResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.Patrol_NewQuestionDialog;
import cn.tties.maint.view.Patrol_QuestionDialog;
import cn.tties.maint.widget.CustomDatePicker;

public class PatrolLayoutAdapter extends BaseAdapter {
    private static final String TAG = "PatrolLayoutAdapter";
     //没有巡视过得  数据补出来得
    public static final int NOQUESTION = 0;
     // 巡视过得  数据请求回来得
    public static final int QUESTION = 1;
    private List<PatrolItemEntity> beanList;
    OnClick listener;
    BaseFragment mFragment;
    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }
    public void setOnClick(OnClick listener){
        this.listener=listener;
    }
    @Override
    public int getItemViewType(int position) {
        //这里是根据position返回指定的布局类型，比如男的返回0，女的返回1，会根据这个返回值加载不同布局
        return beanList.get(position).getInputType();
    }
    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 2;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setDataList(List<PatrolItemEntity> beanList) {
        this.beanList = beanList;
    }
    public PatrolLayoutAdapter(List<PatrolItemEntity> beanList, BaseFragment mFragment) {
        this.beanList = beanList;
        this.mFragment = mFragment;
    }
    public List<PatrolItemEntity> getDataList() {
        return beanList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PatrolItemEntity item = beanList.get(position);
        NoQuestionViewHolder noQuestionViewHolder=null;
        QuestionViewHolder questionViewHolder=null;
        if (convertView == null) {
            switch (item.getInputType()) {
                case NOQUESTION:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_noquestion, null);
                    noQuestionViewHolder = new NoQuestionViewHolder(convertView);
                    convertView.setTag(noQuestionViewHolder);
                    break;
                case QUESTION:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_question, null);
                    questionViewHolder = new QuestionViewHolder(convertView);
                    convertView.setTag(questionViewHolder);
                    break;

            }
        } else {
            switch (item.getInputType()) {
                case NOQUESTION:
                    noQuestionViewHolder = (NoQuestionViewHolder) convertView.getTag();
                    break;
                case QUESTION:
                    questionViewHolder = (QuestionViewHolder) convertView.getTag();
                    break;
            }
        }
        final PatrolResult bean = item.getResult();
        switch (item.getInputType()){
            case NOQUESTION:
                noQuestionViewHolder.text_title.setText(item.getTitle());
                if(String.valueOf(item.getFaultTypeString()).equals("一般")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
                }
                if(String.valueOf(item.getFaultTypeString()).equals("重大")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
                }
                if(String.valueOf(item.getFaultTypeString()).equals("紧急")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
                }
                noQuestionViewHolder.text_description.setText(item.getFaultHarm());
                //无问题  提交数据  隐藏图片展示 。。。
                noQuestionViewHolder.ll_noquestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //有问题
                noQuestionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isTemperature;
                        //是否有温度
                        if(item.getTemperature()==1){
                            isTemperature=true;
                        }else{
                            isTemperature=false;
                        }
                        listener.OnClickListenter(isTemperature);
                    }
                });
                break;
            case QUESTION:
                questionViewHolder.text_title.setText(item.getTitle());
                if(String.valueOf(item.getFaultTypeString()).equals("一般")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
                }
                if(String.valueOf(item.getFaultTypeString()).equals("重大")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
                }
                if(String.valueOf(item.getFaultTypeString()).equals("紧急")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
                }
                //描述
//                questionViewHolder.text_description.setText(item.getFaultHarm());
                //图片列表  图片集合盘空
//                if(){
//                    ImageAdapter adapter = new ImageAdapter(mFragment.getContext(), result.getImageBeanList());
//                    OnRecyclerViewItemClickListener clickListener = new OnRecyclerViewItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, final int pos) {
//
//                            String path = result.getImageBeanList().get(pos).path;
//                            //打开预览
//                            Intent intentPreview = new Intent(mFragment.getContext(), ImagePreviewActivity.class);
//                            ImageItem item = new ImageItem();
//                            item.path = path;
//                            List<ImageItem> list = new ArrayList<>();
//                            list.add(item);
//                            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
//                            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//                            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//                            mFragment.startActivityForResult(intentPreview, EventKind.REQUEST_CODE_PREVIEW);
//                        }
//                    };
//                    adapter.setOnItemClickListener(clickListener);
//                    questionViewHolder.question_imglist.setLayoutManager(new GridLayoutManager(mFragment.getContext(), 3));
//                    questionViewHolder.question_imglist.setHasFixedSize(true);
//                    questionViewHolder.question_imglist.setAdapter(adapter);
//                }

                //以上报无问题
                questionViewHolder.ll_noquestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //重新上报问题
                questionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isTemperature;
                        //是否有温度
                        if(item.getTemperature()==1){
                            isTemperature=true;
                        }else{
                            isTemperature=false;
                        }
                        listener.OnClickListenter(isTemperature);
                    }
                });
                break;
        }


        return convertView;
    }
    public class NoQuestionViewHolder{
            ImageView img_type;
            TextView text_title;
            TextView text_description;
            LinearLayout ll_question;
            LinearLayout ll_noquestion;
        public NoQuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
            text_description= view.findViewById(R.id.text_description);
            ll_question= view.findViewById(R.id.ll_question);
            ll_noquestion= view.findViewById(R.id.ll_noquestion);
        }
    }
    public class QuestionViewHolder{
        ImageView img_type;
        TextView text_title;
        TextView text_description;
        LinearLayout ll_question;
        TextView ll_noquestion;
        RecyclerView question_imglist;
        TextView text_more;
        @SuppressLint("WrongViewCast")
        public QuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
            text_description= view.findViewById(R.id.text_description);
            ll_question= view.findViewById(R.id.ll_question);
            ll_noquestion= view.findViewById(R.id.ll_noquestion);
            question_imglist= view.findViewById(R.id.question_imglist);
            text_more= view.findViewById(R.id.text_more);
        }
    }

    public interface OnClick{
        public void OnClickListenter(boolean flag);
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SelectedPicViewHolder> {
        private Context mContext;
        private List<ImageItem> mData;
        private LayoutInflater mInflater;
        private OnRecyclerViewItemClickListener listener;

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.listener = listener;
        }

        public void setImages(List<ImageItem> data) {
            mData = new ArrayList<>(data);
            notifyDataSetChanged();
        }

        public List<ImageItem> getImages() {
            return mData;
        }

        public ImageAdapter(Context mContext, List<ImageItem> data) {
            this.mContext = mContext;
            this.mInflater = LayoutInflater.from(mContext);
            setImages(data);
        }

        @Override
        public ImageAdapter.SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageAdapter.SelectedPicViewHolder(mInflater.inflate(R.layout.listview_image, parent, false));
        }

        @Override
        public void onBindViewHolder(ImageAdapter.SelectedPicViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView iv_img;
            private int clickPosition;

            public SelectedPicViewHolder(View itemView) {
                super(itemView);
                iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            }

            public void bind(int position) {
                //设置条目的点击事件
                itemView.setOnClickListener(this);
                //根据条目位置设置图片
                ImageItem item = mData.get(position);
                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                clickPosition = position;
            }

            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(v, clickPosition);
            }
        }
    }
}