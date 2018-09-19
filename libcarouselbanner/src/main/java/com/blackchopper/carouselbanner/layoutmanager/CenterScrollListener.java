package com.blackchopper.carouselbanner.layoutmanager;

import android.support.v7.widget.RecyclerView;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : CarouselBanner
 */
public class CenterScrollListener extends RecyclerView.OnScrollListener {
    private boolean mAutoSet = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof CoolBannerLayoutManager)) {
            mAutoSet = true;
            return;
        }

        final CoolBannerLayoutManager.OnPageChangeListener onPageChangeListener = ((CoolBannerLayoutManager) layoutManager).onPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(newState);
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (mAutoSet) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(((CoolBannerLayoutManager) layoutManager).getCurrentPosition());
                }
                mAutoSet = false;
            } else {
                final int delta;
                delta = ((CoolBannerLayoutManager) layoutManager).getOffsetToCenter();
                if (delta != 0) {
                    if (((CoolBannerLayoutManager) layoutManager).getOrientation() == CoolBannerLayoutManager.VERTICAL)
                        recyclerView.smoothScrollBy(0, delta);
                    else
                        recyclerView.smoothScrollBy(delta, 0);
                    mAutoSet = true;
                } else {
                    if (onPageChangeListener != null) {
                        onPageChangeListener.onPageSelected(((CoolBannerLayoutManager) layoutManager).getCurrentPosition());
                    }
                    mAutoSet = false;
                }
            }
        } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            mAutoSet = false;
        }
    }
}