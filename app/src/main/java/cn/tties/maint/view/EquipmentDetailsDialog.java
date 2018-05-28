package cn.tties.maint.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tties.maint.R;
import cn.tties.maint.activity.AmmeterFragment;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.ItemInputType;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.secondLv.RecyclerAdapter;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.widget.CustomDatePicker;

/**
 * Created by Justin on 2018/1/12.
 */

public class EquipmentDetailsDialog extends BaseCustomDialog {
    private static final String TAG = "EquipmentDetailsDialog";
    public Context context;
    private  List<EquipmentLayoutBean> beanList;
    public ListView ListView;
    private LinearLayout cancel;
    private LinearLayout save;
    private TextView savetv;
    private TextView title;
    private EditText edit_value;
    private String name;
    EquipmentLayoutAdapter AdapterD;
    Integer EquipmentId;
    boolean flag;
    boolean isAdd;
    DataBean dataBean;
    //用于记录id位置便于回传。
    int position;
    public EquipmentDetailsDialog(boolean isAdd,Context context, String name, List<EquipmentLayoutBean> beanList,Integer EquipmentId,boolean flag, View.OnClickListener clickListener) {
       super((Activity) context,clickListener);
        this.isAdd=isAdd;
        this.context = context;
        this.beanList=beanList;
        this.name=name;
        this.EquipmentId=EquipmentId;
        this.flag=flag;
    }
    public EquipmentDetailsDialog(int eqmid,int position,DataBean dataBean,boolean isAdd, Context context, String name, List<EquipmentLayoutBean> beanList, View.OnClickListener clickListener) {
        super((Activity) context,clickListener);
        this.isAdd=isAdd;
        this.context = context;
        this.beanList=beanList;
        this.name=name;
        this.dataBean=dataBean;
        this.position=position;
        this.EquipmentId=eqmid;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_equipment_details);
        ListView = findViewById(R.id.dg_eq_de_list);
        cancel = findViewById(R.id.dg_eq_de_cancel);
        save = findViewById(R.id.dg_eq_de_save);
        savetv = findViewById(R.id.dg_eq_de_saveTv);
        title = findViewById(R.id.dg_eq_de_title);
        //重命名输入框
        edit_value = findViewById(R.id.edit_value);
        AdapterD= new EquipmentLayoutAdapter(context,beanList);
        ListView.setAdapter(AdapterD);
        //返回关闭
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquipmentDetailsDialog.this.dismiss();
            }
        });
        if(!isAdd){
            savetv.setText("保存");
        }else{
            savetv.setText("修改");
        }
        //保存添加到另一个recycleview 四级设备类型
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAdd){
                    //刷新添加二级列表
                    EventBusBean busBean = new EventBusBean();
                    busBean.setKind(EventKind.EVENT_COMPANY_ADD);
                    busBean.setMessage(EquipmentId+"");
                    busBean.setObj(AdapterD.getDataList());
                    busBean.setSuccess(flag);
                    busBean.setNew(false);
                    busBean.setName(edit_value.getText().toString());
                    EventBus.getDefault().post(busBean);
                }else{
                    EventBusBean busBean = new EventBusBean();
                    busBean.setKind(EventKind.EVENT_COMPANY_EDITOR);
                    busBean.setObj(AdapterD.getDataList());
                    busBean.setObjs(dataBean);
                    busBean.setSuccess(true);
                    busBean.setPosition(position);
                    busBean.setName(edit_value.getText().toString());
                    EventBus.getDefault().post(busBean);
                    AdapterD.notifyDataSetChanged();
                }

                EquipmentDetailsDialog.this.dismiss();
            }
        });
        title.setText(name);
        edit_value.setText(name);
    }
    
    public class EquipmentLayoutAdapter extends BaseAdapter {
        public static final int EDITTEXT = 0;
        public static final int LEAF = 3;
        private Context context;
        private CustomDatePicker cuys;

        private List<EquipmentLayoutBean> beanList;

        public EquipmentLayoutAdapter(Context context, List<EquipmentLayoutBean> beanList) {
            this.beanList = beanList;
            this.context = context;
            cuys =  new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {    }
            }, "1900-01-01 00:00", "2099-01-01 00:00");
        }

        public EquipmentLayoutAdapter(List<EquipmentLayoutBean> beanList) {
            this.beanList = beanList;
        }

        @Override
        public int getCount() {
            return beanList.size();
        }


        @Override
        public int getItemViewType(int position) {
            //这里是根据position返回指定的布局类型，比如男的返回0，女的返回1，会根据这个返回值加载不同布局
            return beanList.get(position).getType();
        }

        @Override
        public int getViewTypeCount() {
            //这里是adapter里有几种布局
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void setDataList(List<EquipmentLayoutBean> beanList) {
            this.beanList = beanList;
        }

        public List<EquipmentLayoutBean> getDataList() {
            return beanList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final EquipmentLayoutBean bean = beanList.get(position);
            ViewEditTextHolder vEditText = null;
            ViewLeafHolder vLeaf = null;
            if (convertView == null) {
                switch (bean.getType()) {
                    case EDITTEXT:
                        convertView = View.inflate(x.app(), R.layout.layout_edittext, null);
                        vEditText = new ViewEditTextHolder(convertView);
                        convertView.setTag(vEditText);
                        break;
                    case LEAF:
                        convertView = View.inflate(x.app(), R.layout.layout_leafnode, null);
                        vLeaf = new ViewLeafHolder(convertView);
                        convertView.setTag(vLeaf);
                        break;
                }
            } else {
                switch (bean.getType()) {
                    case EDITTEXT:
                        vEditText = (ViewEditTextHolder) convertView.getTag();
                        break;
                    case LEAF:
                        vLeaf = (ViewLeafHolder) convertView.getTag();
                        break;
                }
            }
            //根据不同布局加载不同数据
            switch (bean.getType()) {
                case EDITTEXT:
                    //清除焦点
                    vEditText.value.clearFocus();
                    //先清除之前的文本改变监听
                    if (vEditText.value.getTag() instanceof TextWatcher) {
                        vEditText.value.removeTextChangedListener((TextWatcher) (vEditText.value.getTag()));
                    }
                    vEditText.value.setOnClickListener(null);
                    vEditText.value.setOnFocusChangeListener(null);
                    //设置数据
                    vEditText.name.setText(TextUtils.isEmpty(bean.getTextName()) ? "" : bean.getTextName());
                    vEditText.value.setText(TextUtils.isEmpty(bean.getValue()) ? "" : bean.getValue());

                    //根据输入类型，增加验证和默认值
                    setInputType(bean,  vEditText.value);
                    break;
                case LEAF:
                    //设置数据
                    vLeaf.name.setText(TextUtils.isEmpty(bean.getTextName()) ? "" : bean.getTextName());
                    vLeaf.layoutInfo.removeAllViews();
                    vLeaf.layoutInfo.clearFocus();
                    for (final EquipmentLayoutBean layoutBean : bean.getChildrenList()) {
                        View layout = View.inflate(x.app(), R.layout.layout_edittext, null);
                        ViewEditTextHolder holder = new ViewEditTextHolder(layout);
                        holder.name.setText(layoutBean.getTextName());
                        holder.value.setText(layoutBean.getValue());
                        holder.value.setOnClickListener(null);
                        holder.value.setOnFocusChangeListener(null);
                        holder.value.clearFocus();
                        if (holder.value.getTag() instanceof TextWatcher) {
                            holder.value.removeTextChangedListener((TextWatcher) (holder.value.getTag()));
                        }
                        //根据输入类型，增加验证和默认值
                        setInputType(layoutBean, holder.value);
                        vLeaf.layoutInfo.clearFocus();
                        vLeaf.layoutInfo.addView(layout);
                    }
                    break;
            }
            return convertView;
        }

        private void setInputType(final EquipmentLayoutBean bean, final EditText editText) {
            switch (ItemInputType.get(bean.getInputType())) {
                case STRING:
                    //文本改变监听
                    final TextWatcher stringWatcher = new SimpeTextWather() {
                        @Override
                        public void afterTextChanged(Editable s) {
                            if (TextUtils.isEmpty(s)) {
                                bean.setValue(null);
                            } else {
                                bean.setValue(String.valueOf(s));
                            }
                        }
                    };
                    //吧监听设置到不同的EditText上
                    editText.addTextChangedListener(stringWatcher);
                    editText.setTag(stringWatcher);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case DOUBLE:
                    //文本改变监听
                    final TextWatcher doubleWatcher = new SimpeTextWather() {
                        @Override
                        public void afterTextChanged(Editable s) {
                            int selectionStart = editText.getSelectionStart();
                            int selectionEnd = editText.getSelectionEnd();
                            if (TextUtils.isEmpty(s)) {
                                bean.setValue(null);
                            }
//                        else if (!isOnlyPointNumber(s.toString()) && s.length() > 0) {
//                            //删除多余输入的字（不会显示出来）
//                            s.delete(selectionStart - 1 < 0 ? 0 : selectionStart - 1, selectionEnd);
//                            editText.setText(s);
//                            editText.setSelection(s.length());
//                            bean.setValue(String.valueOf(s));
//                        }
                            else {
                                bean.setValue(String.valueOf(s));
                            }
                        }
                    };
                    //吧监听设置到不同的EditText上
                    editText.addTextChangedListener(doubleWatcher);
                    editText.setTag(doubleWatcher);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                case INT:
                    //文本改变监听
                    final TextWatcher intWatcher = new SimpeTextWather() {
                        @Override
                        public void afterTextChanged(Editable s) {
                            if (TextUtils.isEmpty(s)) {
                                bean.setValue(null);
                            } else {
                                bean.setValue(String.valueOf(s));
                            }
                        }
                    };
                    //吧监听设置到不同的EditText上
                    editText.addTextChangedListener(intWatcher);
                    editText.setTag(intWatcher);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    break;
                case DATE:
                    editText.clearFocus();
                    editText.setInputType(InputType.TYPE_NULL);
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                showDatePickerDialog(bean, editText);
                            }
                        }
                    });
                    editText.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            showDatePickerDialog(bean, editText);
                        }
                    });
                    break;
            }
        }

        /**
         * 第一种布局的Holder
         */
        class ViewEditTextHolder {
            TextView name;
            EditText value;

            public ViewEditTextHolder(View convertView) {
                name = (TextView) convertView.findViewById(R.id.text_name);
                value = (EditText) convertView.findViewById(R.id.edit_value);
            }
        }

        public abstract class SimpeTextWather implements TextWatcher {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }

        /**
         * 第四种布局的Holder
         */
        class ViewLeafHolder {
            TextView name;
            LinearLayout layoutInfo;

            public ViewLeafHolder(View convertView) {
                name = (TextView) convertView.findViewById(R.id.text_name);
                layoutInfo = (LinearLayout) convertView.findViewById(R.id.layout_info);
            }
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        public boolean isOnlyPointNumber(String number) {
            Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,1}$");
            Matcher matcher = pattern.matcher(number);
            return matcher.matches();
        }

        /**
         * 展示日期选择对话框
         */
        private void showDatePickerDialog(final EquipmentLayoutBean bean, final EditText editText) {
//        Calendar c = Calendar.getInstance();
//        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                editText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
//                bean.setValue(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
//            }
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            if (cuys.isShow()) {
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            String now = sdf.format(new Date());
            if (StringUtil.isEmpty(editText.getText().toString())) {
                editText.setText(now.split(" ")[0]);
            }
            cuys =  new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) { // 回调接口，获得选中的时间
                    editText.setText(time.split(" ")[0]);
                    bean.setValue(time.split(" ")[0]);
                }
            }, "1900-01-01 00:00", "2099-01-01 00:00");
            cuys.showSpecificTime(false);
            cuys.setIsLoop(false); // 不允许循环滚动
            cuys.show(editText.getText().toString());
        }
    }

}