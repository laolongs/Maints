package cn.tties.maint.holder;

import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import cn.tties.maint.activity.BaseFragment;

public class BaseLayoutHolder implements Validator.ValidationListener {

    public View contentView;

    public Integer id;

    public Integer dbId;

    public Integer layoutType;

    public BaseFragment fragment;

    public void validator(BaseFragment fragment) {
        this.fragment = fragment;
        Validator validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();
    }

    public void onValidationSucceeded() {
        fragment.onValidationSucceeded();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        fragment.onValidationFailed(errors);
    }

    protected void setClear(EditText editText) {
        editText.setText("");
        editText.setError(null,null);
    }
}