package cn.tties.maint.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.PatrolResult;

/**
 * Created by Justin on 2018/1/8.
 */

public class PatrolDescriptionListViewAdapter extends BaseAdapter {
    private List<PatrolResult.QuertionBean.QuestionDescriptionListBean> mDataList;
    private BaseFragment mFragment;
    public PatrolDescriptionListViewAdapter(BaseFragment fragment, List<PatrolResult.QuertionBean.QuestionDescriptionListBean> mList) {
        mDataList = mList;
        this.mFragment = fragment;

    }

    public PatrolDescriptionListViewAdapter(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public int getCount() {
        if (null == this.mDataList) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public PatrolResult.QuertionBean.QuestionDescriptionListBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PatrolResult.QuertionBean.QuestionDescriptionListBean result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_description, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.textTitle.setText(result.getContent());
        if (result.getImageBeanList() != null) {
            ImageAdapter adapter = new ImageAdapter(mFragment.getContext(), result.getImageBeanList());
            PatrolDescriptionListViewAdapter.OnRecyclerViewItemClickListener clickListener = new PatrolDescriptionListViewAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, final int pos) {

                    String path = result.getImageBeanList().get(pos).path;
                    //打开预览
                    Intent intentPreview = new Intent(mFragment.getContext(), ImagePreviewActivity.class);
                    ImageItem item = new ImageItem();
                    item.path = path;
                    List<ImageItem> list = new ArrayList<>();
                    list.add(item);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    mFragment.startActivityForResult(intentPreview, EventKind.REQUEST_CODE_PREVIEW);
                }
            };
            adapter.setOnItemClickListener(clickListener);

            holder.recyclerViewDescription.setLayoutManager(new GridLayoutManager(mFragment.getContext(), 3));
            holder.recyclerViewDescription.setHasFixedSize(true);
            holder.recyclerViewDescription.setAdapter(adapter);
        }
        return convertView;
    }

    public void setmDataList(List<PatrolResult.QuertionBean.QuestionDescriptionListBean> mDataList) {
        this.mDataList = mDataList;
    }

    class ViewHolder {
        ViewHolder(View view) {
            textTitle = (TextView) view.findViewById(R.id.text_title);
            recyclerViewDescription = (RecyclerView) view.findViewById(R.id.recyclerView);
        }

        public RecyclerView recyclerViewDescription;
        public TextView textTitle;
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
        public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SelectedPicViewHolder(mInflater.inflate(R.layout.listview_image, parent, false));
        }

        @Override
        public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
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