package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;

/**
 * 用电户号列表.
 * Created by Justin on 2018/1/8.
 */
public class EleAccountAdapter extends BaseAdapter {

    private Context context;
    private Integer curEleId;
    private CompanyResult curCompany;
    private List<EleAccountResult> eleAccountList;

    public EleAccountAdapter(Context context, List<EleAccountResult> eleAccountList) {
        this.context = context;
        this.eleAccountList = eleAccountList;
    }

    @Override
    public int getCount() {
        if (curCompany == null) {
            return 0;
        }
        return eleAccountList.size() + 1;
    }

    @Override
    public EleAccountResult getItem(int position) {
        return eleAccountList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_company, parent, false);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (position == 0) {
            holder.textHostId.setVisibility(View.INVISIBLE);
            holder.selView.setVisibility(View.INVISIBLE);
            holder.textComapny.setVisibility(View.VISIBLE);
            holder.textComapny.setText(curCompany.getCompanyShortName());
        } else {
            EleAccountResult item = getItem(position);
            if (curEleId == null) {
                curEleId = item.getEleAccountId();
            }
            holder.textComapny.setVisibility(View.INVISIBLE);
            holder.textHostId.setVisibility(View.VISIBLE);
            if (curEleId == item.getEleAccountId()) {
                holder.selView.setVisibility(View.VISIBLE);
                holder.textHostId.setTextColor(ContextCompat.getColor(this.context, R.color.main_title));
            } else {
                holder.selView.setVisibility(View.INVISIBLE);
                holder.textHostId.setTextColor(ContextCompat.getColor(this.context, R.color.black));
            }
            holder.textHostId.setText(item.getEleNo());
        }
        return convertView;
    }

    class ViewHolder {
        View selView;

        TextView textHostId;

        TextView textComapny;

        public ViewHolder(View view) {
            textHostId = (TextView) view.findViewById(R.id.text_hostId);
            selView = (View) view.findViewById(R.id.selView);
            textComapny = (TextView) view.findViewById(R.id.text_company);
            view.setTag(this);
        }
    }

    public void setCurEleId(Integer curEleId) {
        this.curEleId = curEleId;
    }

    public void setCurCompany(CompanyResult curCompany) {
        this.curCompany = curCompany;
    }

    public void setEleAccountList(List<EleAccountResult> eleAccountList) {
        this.eleAccountList = eleAccountList;
    }
}
