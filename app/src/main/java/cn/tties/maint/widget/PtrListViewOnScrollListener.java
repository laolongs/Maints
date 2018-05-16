package cn.tties.maint.widget;

import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Justin on 2018/1/15.
 */

public class PtrListViewOnScrollListener implements AbsListView.OnScrollListener {

    public PtrClassicFrameLayout mPtrLayout;

    private boolean pauseOnScroll;

    private boolean pauseOnFling;

    private AbsListView.OnScrollListener externalListener;

    public PtrListViewOnScrollListener(PtrClassicFrameLayout mPtrLayout, boolean pauseOnScroll, boolean pauseOnFling) {
        this(mPtrLayout,pauseOnScroll,pauseOnFling, null);
    }

    public PtrListViewOnScrollListener(PtrClassicFrameLayout mPtrLayout, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener customListener) {
        this.mPtrLayout= mPtrLayout;
        this.pauseOnScroll= pauseOnScroll;
        this.pauseOnFling= pauseOnFling;
        externalListener= customListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (externalListener != null) {
            externalListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View firstView = view.getChildAt(firstVisibleItem);

        if (firstVisibleItem == 0 && (firstView == null|| firstView.getTop() == 0)) {
            mPtrLayout.setEnabled(true);
        } else {
            mPtrLayout.setEnabled(false);
        }

        if (externalListener != null) {
            externalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}
