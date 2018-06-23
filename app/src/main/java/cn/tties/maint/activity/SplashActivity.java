package cn.tties.maint.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.AreaDao;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.dao.IndustryDao;
import cn.tties.maint.dao.OverhaulItemDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.dao.PatrolItemTypeDao;
import cn.tties.maint.dao.RateDao;
import cn.tties.maint.entity.AreaEntity;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.entity.IndustryEntity;
import cn.tties.maint.entity.OverhaulItemEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.entity.PatrolItemTypeEntity;
import cn.tties.maint.entity.RateEntity;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.LoginParams;
import cn.tties.maint.httpclient.params.QueryAllMbFunctionParams;
import cn.tties.maint.httpclient.params.SelectAllDataParams;
import cn.tties.maint.httpclient.result.AllDataResult;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.LoginResult;
import cn.tties.maint.httpclient.result.MbFunctionResult;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.widget.TabClass;

/**
 * loading欢迎页面.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final int SHOW_TIME_MIN = 1500; //最短显示时间
    public static DbManager db = x.getDb(MyApplication.getDaoConfig());
    private ConfirmDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ConfirmDialog(SplashActivity.this);
        if (!MyApplication.mNetWorkState) {
            reLine();
            return;
        }
        initData();
    }


    private void reLine() {
        dialog.loadDialog("网络不可用状态", "请检查网络设置，点击重新连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initData();
            }
        }, true);
    }

    private void initData() {
        HttpClientSend.getInstance().send(new SelectAllDataParams(), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                try {
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(x.app(), "获取数据失败", Toast.LENGTH_SHORT).show();
                        reLine();
                    } else {
                        AllDataResult selectAllDataResultList = JsonUtils.deserialize(ret.getResult(), AllDataResult.class);
                        DbManager db = x.getDb(MyApplication.getDaoConfig());

                        //设备表初始化
                        List<EquipmentEntity> equipmentEntityList = selectAllDataResultList.getEquipmentList();
                        db.dropTable(EquipmentEntity.class);
                        EquipmentDao.getInstance().save(equipmentEntityList);

                        //设备项表初始化
                        List<EquipmentItemEntity> equipmentItemEntityList = selectAllDataResultList.getEquipmentItemList();
                        db.dropTable(EquipmentItemEntity.class);
                        EquipmentItemDao.getInstance().save(equipmentItemEntityList);

                        //检修项表初始化
                        List<OverhaulItemEntity> overhaulItemEntityList = selectAllDataResultList.getOverhaulItemList();
                        db.dropTable(OverhaulItemEntity.class);
                        OverhaulItemDao.getInstance().save(overhaulItemEntityList);

                        //巡视项表初始化
                        List<PatrolItemEntity> patrolItemEntityList = selectAllDataResultList.getPatrolItemList();
                        db.dropTable(PatrolItemEntity.class);
                        PatrolItemDao.getInstance().save(patrolItemEntityList);

                        //巡视项类型表初始化
                        List<PatrolItemTypeEntity> patrolItemTypeEntityList = selectAllDataResultList.getPatrolItemTypeList();
                        db.dropTable(PatrolItemTypeEntity.class);
                        PatrolItemTypeDao.getInstance().save(patrolItemTypeEntityList);

                        //省份表初始化
                        List<AreaEntity> areaEntityList = selectAllDataResultList.getAreaList();
                        db.dropTable(AreaEntity.class);
                        AreaDao.getInstance().save(areaEntityList);

                        //行业表初始化
                        List<IndustryEntity> industryEntityList = selectAllDataResultList.getIndustryList();
                        db.dropTable(IndustryEntity.class);
                        IndustryDao.getInstance().save(industryEntityList);

                        //费率表初始化
                        List<RateEntity> rateEntityList = selectAllDataResultList.getRateList();
                        db.dropTable(RateEntity.class);
                        RateDao.getInstance().save(rateEntityList);

                        loadingCache();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    reLine();
                } finally {

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                reLine();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void loadingCache() {
        Boolean loginStatus = ACache.getInstance().getAsObject(Constants.CACHE_LOGIN_STATUS);
        final UserInfoBean userInfoBean = MyApplication.getUserInfo();
        if (loginStatus == null || !loginStatus || userInfoBean == null) {
            Log.i(TAG, "用户非登录状态");
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        } else {
            Log.i(TAG, "自动登录 username:" + userInfoBean.getStaffTel() + " password:" + userInfoBean.getLoginPwd());
            LoginParams params = new LoginParams();
            params.setUserName(userInfoBean.getStaffTel());
            params.setPassWord(userInfoBean.getLoginPwd());
            HttpClientSend.getInstance().send(params, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {//错误
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                            return;
                        }
                        LoginResult loginResult = JsonUtils.deserialize(ret.getResult(), LoginResult.class);
                        loginResult.getUserInfo().setLoginPwd(userInfoBean.getLoginPwd());
                        ACache.getInstance().put(Constants.CACHE_USERINFO, loginResult.getUserInfo());
                        //查询用户菜单
                        getAllMbFunction(loginResult.getUserInfo().getStaffId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    } finally {
                    }
                }
            });
        }
    }

    private void getAllMbFunction(int staffId) {
        QueryAllMbFunctionParams paramsAuth = new QueryAllMbFunctionParams();
        paramsAuth.setStaffId(staffId);
        HttpClientSend.getInstance().send(paramsAuth, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<String> userAuth = new ArrayList<>();
                    List<MbFunctionResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MbFunctionResult>>() {
                    }.getType());
                    for (MbFunctionResult queryAllMbFunctionResult : list) {
                        for (TabClass tabClass : Constants.menuList) {
                            if (tabClass.getAlias().equals(queryAllMbFunctionResult.getAlias())) {
                                userAuth.add(queryAllMbFunctionResult.getAlias());
                            }
                        }
                    }
                    UserInfoBean userInfoBean = MyApplication.getUserInfo();
                    userInfoBean.setMenuList(userAuth);
                    ACache.getInstance().put(Constants.CACHE_USERINFO, userInfoBean);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(x.app(), "连接失败", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } finally {
                }
            }
        });
    }

}
