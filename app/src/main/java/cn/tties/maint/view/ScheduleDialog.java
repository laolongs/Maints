package cn.tties.maint.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddScheduleParams;
import cn.tties.maint.httpclient.params.UpdateQuertionActionParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.util.JsonUtils;


/**
 * 处理进度弹出框
 * author chensi
 */
public class ScheduleDialog extends BaseCustomDialog {
    EditText editsShedule;
    Integer staffId;
    Integer questionId;

    public ScheduleDialog(Integer staffId, Integer questionId) {
        super(MyApplication.curActivity, null);
        this.staffId = staffId;
        this.questionId = questionId;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_schedule);
        editsShedule = findViewById(R.id.edit_schedule);

        Button btnEnd = findViewById(R.id.btn_end);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateQuertionActionParams add0verHaulRecordParams = new UpdateQuertionActionParams();
                add0verHaulRecordParams.setStatus(QuestionStatusType.END.getType());
                add0verHaulRecordParams.setQuestionId(questionId);
                HttpClientSend.getInstance().send(add0verHaulRecordParams, new BaseStringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(x.app(), "处理完成！", Toast.LENGTH_SHORT).show();
                        QuestionFragment.mQuestionFragmentInstance.getQuestionList();
                        ScheduleDialog.this.dismiss();
                    }
                });
            }
        });

        super.mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddScheduleParams addScheduleParams = new AddScheduleParams();
                addScheduleParams.setStaffId(MyApplication.getUserInfo().getStaffId());
                addScheduleParams.setContent(editsShedule.getText().toString());
                addScheduleParams.setQuestionId(questionId);
                HttpClientSend.getInstance().send(addScheduleParams, new BaseStringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(x.app(), "添加进度完成！", Toast.LENGTH_SHORT).show();
                        QuestionFragment.mQuestionFragmentInstance.getQuestionList();
                        ScheduleDialog.this.dismiss();
                    }
                });
            }
        };
        super.setContentView();
    }

    public void loading() {
        this.show();
    }
}