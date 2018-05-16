package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.httpclient.result.ContractResult;

public class ContractCheckListViewAdapter extends RecyclerView.Adapter<ContractCheckListViewAdapter.ImageHolder> {

    private Context mContext;
    private List<ContractResult> mData;
    private LayoutInflater mInflater;

    public ContractCheckListViewAdapter(Context mContext, List<ContractResult> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData =  data;
    }

    public void setMDataList(List<ContractResult> mData) {
        this.mData = mData;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(mInflater.inflate(R.layout.listview_contract, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        holder.bind(position);

    }

    public void setCheckAll() {
        for (ContractResult result : mData) {
            result.setChecked(true);
        }
    }

    public void clearCheckAll() {
        for (ContractResult result : mData) {
            result.setChecked(false);
        }
    }

    public ContractResult getCheck() {
        for (ContractResult result : mData) {
            if (result.isChecked()) {
                return result;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private LinearLayout layoutBg;
        private CheckBox checkBox;

        public View getView() {
            return itemView;
        }

        public ImageHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            layoutBg = (LinearLayout) itemView.findViewById(R.id.layout_bg);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);

        }

        public void bind(int position) {
            final ContractResult result = mData.get(position);
            //设置条目的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result.isChecked()) {
                        result.setChecked(false);
                    } else {
                        clearCheckAll();
                        result.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });
            textName.setText(result.getContractName());
            if (result.isChecked()) {
                checkBox.setChecked(true);
                textName.setTextColor(ContextCompat.getColor(x.app(), R.color.white));
                layoutBg.setBackgroundColor(ContextCompat.getColor(x.app(), R.color.btn_confirm_normal));
            } else {
                checkBox.setChecked(false);
                textName.setTextColor(ContextCompat.getColor(x.app(), R.color.gray));
                layoutBg.setBackgroundColor(ContextCompat.getColor(x.app(), R.color.colorTransparent));
            }
        }

    }
}