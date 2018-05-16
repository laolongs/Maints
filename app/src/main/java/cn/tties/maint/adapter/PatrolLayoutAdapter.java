package cn.tties.maint.adapter;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.holder.PatrolInputBodyItemHolder;
import cn.tties.maint.holder.PatrolInputItemHolder;
import cn.tties.maint.holder.PatrolItemHolder;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.widget.CustomDatePicker;

public class PatrolLayoutAdapter extends BaseAdapter {

    /**
     * 电压电流类输入框
     */
    public static final int INPUT = 0;
    /**
     * 标准是否单选按钮
     */
    public static final int RADIO = 1;
    /**
     * 计量柜柜体
     */
    public static final int GAGUE_BODY = 2;

    private List<PatrolItemEntity> beanList;

    private CustomDatePicker cuys;

    public PatrolLayoutAdapter(List<PatrolItemEntity> beanList) {
        this.beanList = beanList;
        cuys = new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
            }
        }, "1900-01-01 00:00", "2099-01-01 00:00");
    }

    @Override
    public int getCount() {
        return beanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        //这里是根据position返回指定的布局类型，比如男的返回0，女的返回1，会根据这个返回值加载不同布局
        return beanList.get(position).getInputType();
    }

    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 3;
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

    public List<PatrolItemEntity> getDataList() {
        return beanList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PatrolItemEntity item = beanList.get(position);
        PatrolItemHolder itemHolder = null;
        PatrolInputItemHolder inputItemHolder = null;
        PatrolInputBodyItemHolder inputBodyHolder = null;
        if (convertView == null) {
            switch (item.getInputType()) {
                case INPUT:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_input, null);
                    inputItemHolder = new PatrolInputItemHolder(convertView);
                    convertView.setTag(inputItemHolder);
                    break;
                case RADIO:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_radio, null);
                    itemHolder = new PatrolItemHolder(convertView);
                    convertView.setTag(itemHolder);
                    break;
                case GAGUE_BODY:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_gague_body, null);
                    inputBodyHolder = new PatrolInputBodyItemHolder(convertView);
                    convertView.setTag(inputBodyHolder);
                    break;
            }
        } else {
            switch (item.getInputType()) {
                case INPUT:
                    inputItemHolder = (PatrolInputItemHolder) convertView.getTag();
                    break;
                case RADIO:
                    itemHolder = (PatrolItemHolder) convertView.getTag();
                    break;
                case GAGUE_BODY:
                    inputBodyHolder = (PatrolInputBodyItemHolder) convertView.getTag();
                    break;
            }
        }
        final PatrolResult bean = item.getResult();
        //根据不同布局加载不同数据
        switch (item.getInputType()) {
            case INPUT:
                inputItemHolder.editValue1.clearFocus();
                inputItemHolder.editValue2.clearFocus();
                inputItemHolder.editValue3.clearFocus();
                inputItemHolder.editValue4.clearFocus();
                inputItemHolder.editValue5.clearFocus();
                inputItemHolder.editValue6.clearFocus();
                removeWatch(inputItemHolder);
                //标题
                inputItemHolder.textTitle.setText(item.getTitle());
                if (item.getHasTemperature() != null) {
                    inputItemHolder.setViewCount(1);
                    inputItemHolder.textName1.setVisibility(View.GONE);
                    inputItemHolder.textUnit1.setText("C°");
                    inputItemHolder.editValue1.setText(bean.getValue1() == null ? "" : bean.getValue1());

                }
                if (item.getHasLineTemperature() != null) {
                    inputItemHolder.setViewCount(3);
                    inputItemHolder.textName1.setText("A:");
                    inputItemHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                    inputItemHolder.textUnit1.setText("C°");
                    inputItemHolder.textName2.setText("B:");
                    inputItemHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                    inputItemHolder.textUnit2.setText("C°");
                    inputItemHolder.textName3.setText("C:");
                    inputItemHolder.editValue3.setText(bean == null ? "" : bean.getValue3());
                    inputItemHolder.textUnit3.setText("C°");
                }
                if (item.getHasVoltage() != null && item.getHasCurrent() != null) {
                    inputItemHolder.setViewCount(6);
                    inputItemHolder.textName1.setText("A:");
                    inputItemHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                    inputItemHolder.textUnit1.setText("V");
                    inputItemHolder.textName2.setText("B:");
                    inputItemHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                    inputItemHolder.textUnit2.setText("V");
                    inputItemHolder.textName3.setText("C:");
                    inputItemHolder.editValue3.setText(bean == null ? "" : bean.getValue3());
                    inputItemHolder.textUnit3.setText("V");
                    inputItemHolder.textName4.setText("A:");
                    inputItemHolder.editValue4.setText(bean == null ? "" : bean.getValue4());
                    inputItemHolder.textUnit4.setText("A");
                    inputItemHolder.textName5.setText("B:");
                    inputItemHolder.editValue5.setText(bean == null ? "" : bean.getValue5());
                    inputItemHolder.textUnit5.setText("A");
                    inputItemHolder.textName6.setText("C:");
                    inputItemHolder.editValue6.setText(bean == null ? "" : bean.getValue6());
                    inputItemHolder.textUnit6.setText("A");
                } else if (item.getHasVoltage() != null) {
                    inputItemHolder.setViewCount(3);
                    inputItemHolder.textName1.setText("A:");
                    inputItemHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                    inputItemHolder.textUnit1.setText("V");
                    inputItemHolder.textName2.setText("B:");
                    inputItemHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                    inputItemHolder.textUnit2.setText("V");
                    inputItemHolder.textName3.setText("C:");
                    inputItemHolder.editValue3.setText(bean == null ? "" : bean.getValue3());
                    inputItemHolder.textUnit3.setText("V");
                } else if (item.getHasCurrent() != null) {
                    inputItemHolder.setViewCount(3);
                    inputItemHolder.textName1.setText("A:");
                    inputItemHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                    inputItemHolder.textUnit1.setText("A");
                    inputItemHolder.textName2.setText("B:");
                    inputItemHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                    inputItemHolder.textUnit2.setText("A");
                    inputItemHolder.textName3.setText("C:");
                    inputItemHolder.editValue3.setText(bean == null ? "" : bean.getValue3());
                    inputItemHolder.textUnit3.setText("A");
                }
                if (item.getActivePower() != null) {
                    inputItemHolder.setViewCount(2);
                    inputItemHolder.textName1.setText("上月有功功率:");
                    inputItemHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                    inputItemHolder.textUnit1.setVisibility(View.GONE);
                    inputItemHolder.textName2.setText("本期有功功率:");
                    inputItemHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                    inputItemHolder.textUnit2.setVisibility(View.GONE);
                }
                setWatch(bean, inputItemHolder);
                break;
            case RADIO:
                itemHolder.radioGroup.clearFocus();
                itemHolder.radioGroup.setOnCheckedChangeListener(null);
                //标题
                itemHolder.textTitle.setText(item.getTitle());
                if (item.getEquipType() != null && item.getEquipType().equals(OtherPatrolItemType.ENVIRONMENT.getInfo())) {
                    itemHolder.radio1.setText("是");
                    itemHolder.radio2.setText("否");
                }
                if (item.getEquipType() != null && item.getEquipType().equals(OtherPatrolItemType.HEALTH.getInfo())) {
                    itemHolder.radio1.setText("是");
                    itemHolder.radio2.setText("否");
                }
                //没有类型 不显示信息
                if (item.getFaultType() == null) {
                    itemHolder.layoutInfo.setVisibility(View.GONE);
                } else {
                    itemHolder.textType.setText(String.valueOf(item.getFaultTypeString()));
                    itemHolder.textFaultHarm.setText(item.getFaultHarm());
                    itemHolder.textDealDesc.setText(item.getDealDesc());
                }
                if (bean.getHasquestion() != null) {
                    if (bean.getHasquestion()) {
                        itemHolder.radio1.setChecked(true);
                    } else {
                        itemHolder.radio2.setChecked(true);
                    }
                } else {
                    itemHolder.radioGroup.clearCheck();
                }

                itemHolder.radioGroup.setTag(bean);
                //吧监听设置到不同的EditText上
                itemHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton btn = (RadioButton) radioGroup.findViewById(i);
                        PatrolResult bean = (PatrolResult) radioGroup.getTag();
                        bean.setHasquestion(false);
                        if (btn.getText().equals("是") || btn.getText().equals("没有隐患") || btn.getText().equals("当前洁净状态")) {
                            bean.setHasquestion(true);
                        }
                    }
                });
                break;
            case GAGUE_BODY:
                final EditText editText = inputBodyHolder.editValue1;
                inputBodyHolder.editValue2.clearFocus();
                inputBodyHolder.editValue3.clearFocus();
                removeWatch(inputBodyHolder);
                //标题
                inputBodyHolder.textTitle.setText(item.getTitle());
                inputBodyHolder.textName1.setText("监视日期");
                inputBodyHolder.textName2.setText("有功总");
                inputBodyHolder.textName3.setText("无功总");
                inputBodyHolder.textUnit2.setText("W");
                inputBodyHolder.textUnit3.setText("W");
                inputBodyHolder.editValue1.setText(bean == null ? "" : bean.getValue1());
                inputBodyHolder.editValue2.setText(bean == null ? "" : bean.getValue2());
                inputBodyHolder.editValue3.setText(bean == null ? "" : bean.getValue3());
                editText.clearFocus();
                editText.setHint("请选择时间");
                editText.setInputType(InputType.TYPE_NULL);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            showDatePickerDialog(bean, editText);
                        }
                    }
                });
                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePickerDialog(bean, editText);
                    }
                });

                setWatch(bean, inputBodyHolder);
                break;
        }
        return convertView;
    }

    private void removeWatch(PatrolInputItemHolder holder) {
        if (holder.editValue1.getTag() instanceof TextWatcher) {
            holder.editValue1.removeTextChangedListener((TextWatcher) (holder.editValue1.getTag()));
        }
        if (holder.editValue2.getTag() instanceof TextWatcher) {
            holder.editValue2.removeTextChangedListener((TextWatcher) (holder.editValue2.getTag()));
        }
        if (holder.editValue3.getTag() instanceof TextWatcher) {
            holder.editValue3.removeTextChangedListener((TextWatcher) (holder.editValue3.getTag()));
        }
        if (holder.editValue4.getTag() instanceof TextWatcher) {
            holder.editValue4.removeTextChangedListener((TextWatcher) (holder.editValue4.getTag()));
        }
        if (holder.editValue5.getTag() instanceof TextWatcher) {
            holder.editValue5.removeTextChangedListener((TextWatcher) (holder.editValue5.getTag()));
        }
        if (holder.editValue6.getTag() instanceof TextWatcher) {
            holder.editValue6.removeTextChangedListener((TextWatcher) (holder.editValue6.getTag()));
        }
    }

    private void removeWatch(PatrolInputBodyItemHolder holder) {
        if (holder.editValue2.getTag() instanceof TextWatcher) {
            holder.editValue2.removeTextChangedListener((TextWatcher) (holder.editValue2.getTag()));
        }
        if (holder.editValue3.getTag() instanceof TextWatcher) {
            holder.editValue3.removeTextChangedListener((TextWatcher) (holder.editValue3.getTag()));
        }
    }

    private void setWatch(final PatrolResult bean, PatrolInputItemHolder holder) {
        //文本改变监听
        final TextWatcher oneNameWatcher1 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue1(null);
                } else {
                    bean.setValue1(String.valueOf(s));
                }
            }
        };
        holder.editValue1.addTextChangedListener(oneNameWatcher1);
        holder.editValue1.setTag(oneNameWatcher1);

        //文本改变监听
        final TextWatcher oneNameWatcher2 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue2(null);
                } else {
                    bean.setValue2(String.valueOf(s));
                }
            }
        };
        holder.editValue2.addTextChangedListener(oneNameWatcher2);
        holder.editValue2.setTag(oneNameWatcher2);


        //文本改变监听
        final TextWatcher oneNameWatcher3 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue3(null);
                } else {
                    bean.setValue3(String.valueOf(s));
                }
            }
        };
        holder.editValue3.addTextChangedListener(oneNameWatcher3);
        holder.editValue3.setTag(oneNameWatcher3);


        //文本改变监听
        final TextWatcher oneNameWatcher4 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue1(null);
                } else {
                    bean.setValue1(String.valueOf(s));
                }
            }
        };
        holder.editValue4.addTextChangedListener(oneNameWatcher4);
        holder.editValue4.setTag(oneNameWatcher4);


        //文本改变监听
        final TextWatcher oneNameWatcher5 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue5(null);
                } else {
                    bean.setValue5(String.valueOf(s));
                }
            }
        };
        holder.editValue5.addTextChangedListener(oneNameWatcher5);
        holder.editValue5.setTag(oneNameWatcher5);

        //文本改变监听
        final TextWatcher oneNameWatcher6 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue6(null);
                } else {
                    bean.setValue6(String.valueOf(s));
                }
            }
        };
        holder.editValue6.addTextChangedListener(oneNameWatcher6);
        holder.editValue6.setTag(oneNameWatcher6);

    }

    private void setWatch(final PatrolResult bean, PatrolInputBodyItemHolder holder) {

        //文本改变监听
        final TextWatcher oneNameWatcher = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue1(null);
                } else {
                    bean.setValue1(String.valueOf(s));
                }
            }
        };
        holder.editValue1.addTextChangedListener(oneNameWatcher);
        holder.editValue1.setTag(oneNameWatcher);

        //文本改变监听
        final TextWatcher oneNameWatcher2 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue2(null);
                } else {
                    bean.setValue2(String.valueOf(s));
                }
            }
        };
        holder.editValue2.addTextChangedListener(oneNameWatcher2);
        holder.editValue2.setTag(oneNameWatcher2);


        //文本改变监听
        final TextWatcher oneNameWatcher3 = new SimpeTextWather() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setValue3(null);
                } else {
                    bean.setValue3(String.valueOf(s));
                }
            }
        };
        holder.editValue3.addTextChangedListener(oneNameWatcher3);
        holder.editValue3.setTag(oneNameWatcher3);
    }

    public abstract class SimpeTextWather implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
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

    /**
     * 展示日期选择对话框
     */
    private void showDatePickerDialog(final PatrolResult bean, final EditText editText) {
        if (cuys.isShow()) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        if (StringUtil.isEmpty(editText.getText().toString())) {
            editText.setText(now);
        }
        cuys = new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                editText.setText(time);
                bean.setValue1(time);
            }
        }, "1900-01-01 00:00", "2099-01-01 00:00");
        cuys.showSpecificTime(true);
        cuys.setIsLoop(false); // 不允许循环滚动
        cuys.show(editText.getText().toString());
    }
}