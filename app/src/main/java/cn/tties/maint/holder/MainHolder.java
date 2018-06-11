package cn.tties.maint.holder;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.tties.maint.R;

public class MainHolder extends BaseLayoutHolder {

    public LinearLayout equipment;
    public ImageView equipment_img;
    public TextView equipment_tv;
    public LinearLayout wordorder;
    public ImageView wordorder_img;
    public TextView wordorder_tv;
    public LinearLayout ele_configuration;
    public ImageView ele_configuration_img;
    public TextView ele_configuration_tv;
    public LinearLayout ele_tour;
    public ImageView ele_tour_img;
    public TextView ele_tour_tv;
    public LinearLayout eliminate;
    public ImageView eliminate_img;
    public TextView eliminate_tv;
    public LinearLayout beautify;
    public ImageView beautify_img;
    public TextView beautify_tv;
    public LinearLayout dedusting;
    public ImageView dedusting_img;
    public TextView dedusting_tv;
    public LinearLayout question;
    public ImageView question_img;
    public TextView question_tv;
    public LinearLayout setting;
    public ImageView setting_img;
    public TextView setting_tv;
    public MainHolder(View contentView) {
        equipment = (LinearLayout) contentView.findViewById(R.id.equipment);
        equipment_img = (ImageView) contentView.findViewById(R.id.equipment_img);
        equipment_tv = (TextView) contentView.findViewById(R.id.equipment_tv);
        wordorder = (LinearLayout) contentView.findViewById(R.id.wordorder);
        wordorder_img = (ImageView) contentView.findViewById(R.id.wordorder_img);
        wordorder_tv = (TextView) contentView.findViewById(R.id.wordorder_tv);
        ele_configuration = (LinearLayout) contentView.findViewById(R.id.ele_configuration);
        ele_configuration_img = (ImageView) contentView.findViewById(R.id.ele_configuration_img);
        ele_configuration_tv = (TextView) contentView.findViewById(R.id.ele_configuration_tv);
        ele_tour = (LinearLayout) contentView.findViewById(R.id.ele_tour);
        ele_tour_img = (ImageView) contentView.findViewById(R.id.ele_tour_img);
        ele_tour_tv = (TextView) contentView.findViewById(R.id.ele_tour_tv);
        eliminate = (LinearLayout) contentView.findViewById(R.id.eliminate);
        eliminate_img = (ImageView) contentView.findViewById(R.id.eliminate_img);
        eliminate_tv = (TextView) contentView.findViewById(R.id.eliminate_tv);
        beautify = (LinearLayout) contentView.findViewById(R.id.beautify);
        beautify_img = (ImageView) contentView.findViewById(R.id.beautify_img);
        beautify_tv = (TextView) contentView.findViewById(R.id.beautify_tv);
        dedusting = (LinearLayout) contentView.findViewById(R.id.dedusting);
        dedusting_img = (ImageView) contentView.findViewById(R.id.dedusting_img);
        dedusting_tv = (TextView) contentView.findViewById(R.id.dedusting_tv);
        question = (LinearLayout) contentView.findViewById(R.id.question);
        question_img = (ImageView) contentView.findViewById(R.id.question_img);
        question_tv = (TextView) contentView.findViewById(R.id.question_tv);
        setting = (LinearLayout) contentView.findViewById(R.id.setting);
        setting_img = (ImageView) contentView.findViewById(R.id.setting_img);
        setting_tv = (TextView) contentView.findViewById(R.id.setting_tv);
    }

}