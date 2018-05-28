package cn.tties.maint.secondLv;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.tties.maint.R;

/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */

public class ParentViewHolder extends BaseViewHolder {
    private Context mContext;
    private View view;
    private RelativeLayout containerLayout;
    private TextView parentLeftView;
    private TextView parentRightView;
    public ImageView expand;
//    public View parentDashedView;
    public LinearLayout parentDashedView;
    RecyclerAdapter recyclerAdapter;
    /**
     * 标记展开的item
     */
    private int opened = -1;
    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final RecyclerAdapter recyclerAdapter, final DataBean dataBean, final int pos, final ItemClickListener listener){
        this.recyclerAdapter=recyclerAdapter;
        containerLayout = (RelativeLayout) view.findViewById(R.id.container);
        parentLeftView = (TextView) view.findViewById(R.id.parent_left_text);
        parentRightView = (TextView) view.findViewById(R.id.parent_right_text);
        expand = (ImageView) view.findViewById(R.id.expend);
        parentDashedView = view.findViewById(R.id.parent_dashed_view);
        parentDashedView = (LinearLayout)view.findViewById(R.id.parent_dashed_view);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand
                .getLayoutParams();
        expand.setLayoutParams(params);
        parentLeftView.setText(dataBean.getParentLeftTxt());
//        parentRightView.setText(dataBean.getParentRightTxt());
        parentRightView.setText("详情");

        if (pos == opened){
            parentDashedView.setVisibility(View.VISIBLE);
        } else{
            parentDashedView.setVisibility(View.GONE);
        }
        //父布局OnClick监听
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opened == getAdapterPosition()) {
                    //当点击的item已经被展开了, 就关闭.
                    opened = -1;
                    recyclerAdapter.notifyItemChanged(getAdapterPosition());
                } else {
                    int oldOpened = opened;
                    opened = getAdapterPosition();
                    recyclerAdapter.notifyItemChanged(oldOpened);
                    recyclerAdapter.notifyItemChanged(opened);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

}