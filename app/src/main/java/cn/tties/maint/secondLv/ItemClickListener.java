package cn.tties.maint.secondLv;

/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */
/**
 * Created by hbh on 2017/4/20.
 * 父布局Item点击监听接口
 */
public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(DataBean bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(DataBean bean);
}
