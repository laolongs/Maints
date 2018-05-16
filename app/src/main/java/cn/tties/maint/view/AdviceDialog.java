package cn.tties.maint.view;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddAdviceParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.util.JsonUtils;


/**
 * 处理建议弹出框
 * author chensi
 */
public class AdviceDialog extends BaseCustomDialog {
    @NotEmpty(message = "请输入处理建议")
    EditText editAdvice;
    Integer staffId;
    Integer questionId;

    public AdviceDialog(Integer staffId, Integer questionId) {
        super(MyApplication.curActivity, null);
        this.staffId = staffId;
        this.questionId = questionId;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_advice);
        editAdvice = findViewById(R.id.edit_advice);
        super.setContentView();
    }

    public void loading() {
        super.mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdviceDialog.this.validator.validate();
            }
        };
        this.show();
    }

    @Override
    public void onValidationSucceeded() {
        AddAdviceParams addAdviceParams = new AddAdviceParams();
        addAdviceParams.setContent(editAdvice.getText().toString());
        addAdviceParams.setStaffId(staffId);
        addAdviceParams.setQuestionId(questionId);
        HttpClientSend.getInstance().send(addAdviceParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(x.app(), "处理完成！", Toast.LENGTH_SHORT).show();
                QuestionFragment.mQuestionFragmentInstance.getQuestionList();
                AdviceDialog.this.dismiss();
            }
        });
    }
}