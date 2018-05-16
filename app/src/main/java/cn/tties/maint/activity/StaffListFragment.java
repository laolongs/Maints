package cn.tties.maint.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.TableAccountAdapter;
import cn.tties.maint.enums.RoleType;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.QuitParams;
import cn.tties.maint.httpclient.params.SelectStaffParams;
import cn.tties.maint.httpclient.params.UpdateMbRoleByIdParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.MbStaffResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.view.UserHandoverDialog;
import cn.tties.maint.view.UserInfoDialog;
import cn.tties.maint.view.UserPwdDialog;
import cn.tties.maint.view.UserRoleDialog;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 账户（员工）管理.
 */
@ContentView(R.layout.fragment_account_check)
public class StaffListFragment extends BaseFragment {

    private static final String TAG = "AccountCheckFragment";

    @ViewInject(R.id.ptrlayout_staff)
    private PtrClassicFrameLayout staffPtrlayout;

    @ViewInject(R.id.table_account)
    private ListView accountTable;

    private TableAccountAdapter accountAdapter;

    private UserHandoverDialog userHandoverDialog;

    private UserInfoDialog userInfoDialog;

    private UserRoleDialog userRoleDialog;

    private UserPwdDialog userPwdDialog;

    public static StaffListFragment staffListFragmentInstance;

    private List<MbStaffResult> accountList;

    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staffListFragmentInstance = this;
        accountAdapter = new MyAdapter(x.app(), accountList);
        this.mPtrListViewOnScrollListener = new PtrListViewOnScrollListener(this.staffPtrlayout, true, false);
        accountTable.setAdapter(accountAdapter);
        accountTable.setOnScrollListener(this.mPtrListViewOnScrollListener);
        this.queryStaffList();
        this.initPullRefresh();
    }

    /**
     * 初始化刷新.
     */
    private void initPullRefresh() {
        staffPtrlayout.setPtrHandler(new PtrHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                StaffListFragment.this.queryStaffList();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    public class MyAdapter extends TableAccountAdapter {

        public MyAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getConvertView(convertView);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (position % 2 == 0) {
                viewHolder.layout.setBackgroundColor((ContextCompat.getColor(x.app(), R.color.table_bg)));
            } else {
                viewHolder.layout.setBackgroundColor((ContextCompat.getColor(x.app(), R.color.white)));
            }
            final MbStaffResult ec = (MbStaffResult) this.getItem(position);
            viewHolder.text1.setText(ec.getStaffName());
            viewHolder.text2.setText(String.valueOf(ec.getStaffTel()));
            viewHolder.text3.setText(ec.getRoleName());
            viewHolder.text4.setText(ec.getStaffNo());

            viewHolder.btn1.setText("修改密码");
            viewHolder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userPwdDialog = new UserPwdDialog(StaffListFragment.this, ec, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userPwdDialog.validator.validate();
                        }
                    });
                    userPwdDialog.show();
                }
            });
            viewHolder.btn2.setText("修改信息");
            viewHolder.btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInfoDialog = new UserInfoDialog(StaffListFragment.this, ec, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userInfoDialog.validator.validate();
                        }
                    });
                    userInfoDialog.show();

                }
            });
            viewHolder.btn3.setText("修改角色");
            if(ec.getRoleId() == RoleType.MANAGER.getValue()){
                viewHolder.btn3.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.btn3.setVisibility(View.VISIBLE);
            }
            viewHolder.btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRoleDialog = new UserRoleDialog(StaffListFragment.this.getActivity(), ec, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UpdateMbRoleByIdParams updateMbRoleByIdParams = new UpdateMbRoleByIdParams();
                            updateMbRoleByIdParams.setStaffId(ec.getStaffId());
                            for (int i = 0; i < userRoleDialog.radioRole.getChildCount(); i ++) {
                                RadioButton view = (RadioButton) userRoleDialog.radioRole.getChildAt(i);
                                if (view.isChecked()) {
                                    Integer roleId = RoleType.getValue(view.getText().toString());
                                    updateMbRoleByIdParams.setRoleId(roleId);
                                    break;
                                }
                            }
                            HttpClientSend.getInstance().send(updateMbRoleByIdParams, new BaseStringCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        userRoleDialog.dismiss();
                                        StaffListFragment.this.queryStaffList();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                    }
                                }
                            });
                        }
                    });
                    userRoleDialog.show();

                }
            });
            viewHolder.btn4.setText("离职交接");
            if(ec.getRoleId() == RoleType.MANAGER.getValue()){
                viewHolder.btn4.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.btn4.setVisibility(View.VISIBLE);
            }
            viewHolder.btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final List<MbStaffResult> sinnerList = new ArrayList<MbStaffResult>();
                    for (MbStaffResult entity : accountList) {
                        if (ec.getRoleId() == entity.getRoleId() && ec.getStaffId() != entity.getStaffId()) {
                            sinnerList.add(entity);
                        }
                    }
                    if (sinnerList.size() == 0) {
                        Toast.makeText(x.app(), "无可交接人员", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    userHandoverDialog = new UserHandoverDialog(StaffListFragment.this.getActivity(),ec, sinnerList, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (sinnerList.size() == 0) {
                                userHandoverDialog.dismiss();
                            }
                            MbStaffResult checkEntity = sinnerList.get(userHandoverDialog.overAccount.getSelectedItemPosition());
                            QuitParams quitParams = new QuitParams();
                            quitParams.setOldStaffId(ec.getStaffId());
                            quitParams.setRoldId(ec.getRoleId());
                            quitParams.setNewStaffId(checkEntity.getStaffId());
                            HttpClientSend.getInstance().send(quitParams, new BaseStringCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {

                                        userHandoverDialog.dismiss();
                                        StaffListFragment.this.queryStaffList();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                    }
                                }
                            });
                        }

                    });
                    userHandoverDialog.show();
                }
            });
            return convertView;
        }
    }

    public void queryStaffList() {
        HttpClientSend.getInstance().send(new SelectStaffParams(), new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    staffPtrlayout.refreshComplete();
                    return;
                }

                accountList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MbStaffResult>>() {}.getType());
                accountAdapter.setList(accountList);
                accountAdapter.notifyDataSetChanged();
                staffPtrlayout.refreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                staffPtrlayout.refreshComplete();
            }
        });
    }
}
