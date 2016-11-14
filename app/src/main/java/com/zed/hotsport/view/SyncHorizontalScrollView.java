package com.zed.hotsport.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
/**
 * Created by Administrator on 2016/10/10.
 */

public class SyncHorizontalScrollView extends HorizontalScrollView{

    private View mView;

    public SyncHorizontalScrollView(Context context) {
        super(context);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 通过重写HorizontalScrollView的onScrollChanged方法使用scrollTo实现联动
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //设置控件滚动监听，得到滚动的距离，然后让传进来的view也设置相同的滚动具体
        if(mView!=null) {
            mView.scrollTo(l, t);
        }
    }

    /**
     * 设置跟它联动的view
     * @param view
     */
    public void setScrollView(View view){
        this.mView = view;
    }

}
