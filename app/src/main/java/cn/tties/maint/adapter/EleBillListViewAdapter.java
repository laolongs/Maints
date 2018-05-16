package cn.tties.maint.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.bean.EleBillBean;
import cn.tties.maint.bean.ImageBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.DeleteEleBillParams;
import cn.tties.maint.httpclient.params.UploadEleBillParams;
import cn.tties.maint.util.DateUtils;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.HorizontalListView;

/**
 * Created by Justin on 2018/1/8.
 */

public class EleBillListViewAdapter extends BaseAdapter {

    private List<EleBillBean> mDataList;

    private BaseFragment mFragment;

    private EleBillBean curEleBillBean;

    private int mPostion;

    private int totalWidth;

    private HorizontalListView listViewHorizon;

    public EleBillListViewAdapter(BaseFragment fragment, int width,  HorizontalListView listViewHorizon) {
        this.mFragment = fragment;
        this.totalWidth = width;
        this.listViewHorizon = listViewHorizon;
    }

    @Override
    public int getCount() {
        if (null == this.mDataList) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public EleBillBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final EleBillBean item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_ele_bill, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final int margin = totalWidth / 5;
        final int mWidth = totalWidth * 3 / 5;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.linearLayout.getLayoutParams();
        params.width = mWidth;
        if (position == 0) {
            params.setMargins(margin, 0, margin/2, 0);
        } else if (position == mDataList.size() - 1) {
            params.setMargins(0, 0, margin, 0);
        } else {
            params.setMargins(0, 0, margin/2, 0);
        }
        holder.linearLayout.setLayoutParams(params);

        holder.textMonth.setText(DateUtils.getStrMonth(item.getMonth()));
        holder.textPrvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewHorizon.scrollTo((position -1) * (margin/2 + mWidth));
            }
        });
        holder.textNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewHorizon.scrollTo((position+1) * (margin/2 + mWidth));
            }
        });
        setImageItem(0, item, holder.image1);
        setImageItem(1, item, holder.image2);
        setImageItem(2, item, holder.image3);
        setImageItem(3, item, holder.image4);
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getImageList() == null || item.getImageList().size() == 0) {
                    Toast.makeText(x.app(), "当月未上传图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                ConfirmDialog dialog = new ConfirmDialog(MyApplication.curActivity);
                dialog.loadDialog("是否上传图片？", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UploadEleBillParams params = new UploadEleBillParams();
                        params.setBillMonth(item.getMonth());
                        params.setBillYear(item.getYear());
                        params.setEleAccountId(item.getEleAccountId());
                        List<File> fileList = new ArrayList<>();
                        for (ImageBean billBean : item.getImageList()) {
                            if (billBean.isLocal()) {
                                File file = new File(billBean.getImageUrl());
                                fileList.add(file);
                            }
                        }
                        HttpClientSend.getInstance().sendFile(params, fileList, new BaseAlertCallback("上传电费单成功", "上传电费单失败") {
                            @Override
                            public void onSuccessed(String result) {
                            }
                        });
                    }
                });
            }
        });
        return convertView;
    }

    private void setImageItem(final int pos, final EleBillBean item, final ImageView imageView) {
        final ImageBean bean = getImageBean(item, pos);
        //如果图片地址显示上传图片
        if (bean.getImageUrl() == null && !bean.isLocal()) {
            ImagePicker.getInstance().getImageLoader().displayImage(mFragment.getActivity(), "", imageView, 0, 0);
            imageView.setImageResource(R.drawable.selector_image_add);
            imageView.setPadding(20, 20, 20, 20);
        } else {
            imageView.setPadding(0, 0, 0, 0);
            String path = "";
            if (bean.isLocal()) { //本地图片
                path = Uri.fromFile(new File(bean.getImageUrl())).toString();
            } else {//网络图片
                path = bean.getImageUrl();
            }
            ImagePicker.getInstance().getImageLoader().displayImage(mFragment.getActivity(), path, imageView, 0, 0);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                curEleBillBean = item;
                mPostion = pos;
                if (bean.getImageUrl() != null) {//已上传图片显示预览
                    String path = "";
                    if (bean.isLocal()) { //本地图片
                        path = Uri.fromFile(new File(bean.getImageUrl())).toString();
                    } else {//网络图片
                        path = bean.getImageUrl();
                    }
                    //打开预览
                    Intent intentPreview = new Intent(mFragment.getContext(), ImagePreviewDelActivity.class);
                    ImageItem item = new ImageItem();
                    item.path = path;
                    List<ImageItem> list = new ArrayList<>();
                    list.add(item);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    mFragment.startActivityForResult(intentPreview, EventKind.REQUEST_CODE_PREVIEW);
                } else {//未上传图片显示上传
                    int count = 4 - curEleBillBean.getImageList().size();
                    ImagePicker.getInstance().setSelectLimit(count);
                    Intent intent1 = new Intent(mFragment.getActivity(), ImageGridActivity.class);
                    mFragment.startActivityForResult(intent1, EventKind.REQUEST_CODE_SELECT);
                }
            }
        });
    }

    public void setImageItem(List<ImageItem> imageItems) {
        List<ImageBean> oldList = curEleBillBean.getImageList();
        for (ImageBean oldBean : oldList) {
            if (oldBean.isLocal()) {
                boolean isExisit = false;
                int index = 0;
                for (ImageItem item : imageItems) {
                   if (item.path.equals(oldBean.getImageUrl())) {
                       isExisit =true;
                       break;
                   }
                    index++;
                }
                if (isExisit) {
                    imageItems.remove(index);
                }
            }
        }
        if (imageItems.size() == 0) {
            Toast.makeText(x.app(),"图片已选择",Toast.LENGTH_SHORT).show();
            return;
        }
        for (ImageItem item : imageItems) {
            ImageBean bean = new ImageBean();
            bean.setImageUrl(item.path);
            bean.setLocal(true);
            curEleBillBean.getImageList().add(bean);
        }
        this.notifyDataSetChanged();
    }

    public ImageBean getImageBean(EleBillBean item, int postion) {
        if (item.getImageList().size() - 1 < postion) {
            return new ImageBean();
        }
        return item.getImageList().get(postion);
    }

    public void setmDataList(List<EleBillBean> mDataList) {
        this.mDataList = mDataList;
    }

    public void deleteImage() {
        final ImageBean bean = getImageBean(curEleBillBean, mPostion);
        if (bean.isLocal()) {
            curEleBillBean.getImageList().remove(mPostion);
            notifyDataSetChanged();
            return;
        }
        DeleteEleBillParams params = new DeleteEleBillParams();
        params.setBillId(bean.getImageId());
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("删除电费单成功", "删除电费单失败") {
            @Override
            public void onSuccessed(String result) {
                curEleBillBean.getImageList().set(mPostion, new ImageBean());
                notifyDataSetChanged();
            }
        });
    }


    public class ViewHolder {

        ViewHolder(View view) {
            btnSave = (Button) view.findViewById(R.id.btn_save);
            textMonth = (TextView) view.findViewById(R.id.text_month);
            image1 = (ImageView) view.findViewById(R.id.image_1);
            image2 = (ImageView) view.findViewById(R.id.image_2);
            image3 = (ImageView) view.findViewById(R.id.image_3);
            image4 = (ImageView) view.findViewById(R.id.image_4);
            linearLayout = (LinearLayout) view.findViewById(R.id.sasf);
            textPrvMonth = (TextView) view.findViewById(R.id.text_prv_month);
            textNextMonth = (TextView) view.findViewById(R.id.text_next_month);
        }

        public TextView textMonth;
        public TextView textPrvMonth;
        public TextView textNextMonth;
        public ImageView image1;
        public ImageView image2;
        public ImageView image3;
        public ImageView image4;

        public Button btnSave;

        public LinearLayout linearLayout;
    }
}