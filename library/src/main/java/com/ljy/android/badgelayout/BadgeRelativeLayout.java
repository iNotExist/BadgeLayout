package com.ljy.android.badgelayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.ljy.android.library.R;


/**
 * <p>
 * 子控件可以选择其他子控件一个角作为中心位置，用于显示未读消息数量的效果
 * </p>
 * <p>
 * 在xml中，子控件可以设置两个属性targetView和layoutOn，targetView的值是目标控件id，layoutOn默认值为rt，一共可选的：
 * lt 左上角 lb 左下角 rt 右上角 rb 右下角
 * </p>
 * <p>
 * 当你子控件使用了targetView属性时，margin的作用发生了改变，用来控制偏移：<br/>
 * layout_marginRight，向左偏移指定距离<br/>
 * layout_marginLeft，向又偏移指定距离<br/>
 * layout_marginTop，向下偏移指定距离<br/>
 * layout_marginBottom，向上偏移指定距离<br/>
 * </p>
 */
public class BadgeRelativeLayout extends RelativeLayout {


    public BadgeRelativeLayout(Context context) {
        super(context);
    }

    public BadgeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs,0);
    }

    private void init(Context context, AttributeSet attrs,int defStyleAttr) {
    }

    public BadgeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                BadgeLayoutParams layoutParams =
                        (BadgeLayoutParams) child.getLayoutParams();
                if (layoutParams.mTargetViewId != -1) {
                    View targetView = findViewById(layoutParams.mTargetViewId);

                    int cl = 0;
                    int ct = 0;
                    int cr = 0;
                    int cb = 0;
                    int childMeasuredWidth = child.getMeasuredWidth();
                    int childMeasuredHeight = child.getMeasuredHeight();
                    if (layoutParams.mLayoutOn == BadgeLayoutParams.RIGHT_TOP) {
                        cl = (int)(targetView.getX() + targetView.getMeasuredWidth() - childMeasuredWidth/2);
                        ct = (int) (targetView.getY() - childMeasuredHeight/2);
                        cr = (int)(targetView.getX() + targetView.getMeasuredWidth() + childMeasuredWidth/2 );
                        cb = (int)(targetView.getY() + childMeasuredHeight/2 );
                    } else if(layoutParams.mLayoutOn == BadgeLayoutParams.RIGHT_BOTTOM){
                        cl = (int)(targetView.getX() + targetView.getMeasuredWidth() - childMeasuredWidth/2 );
                        ct = (int) (targetView.getY() + targetView.getMeasuredHeight() - childMeasuredHeight/2);
                        cr = (int)(targetView.getX() + targetView.getMeasuredWidth() + childMeasuredWidth/2 );
                        cb = (int)(targetView.getY() + targetView.getMeasuredHeight() + childMeasuredHeight/2);
                    } else if(layoutParams.mLayoutOn == BadgeLayoutParams.LEFT_TOP){
                        cl = (int)(targetView.getX()  - childMeasuredWidth/2);
                        ct = (int) (targetView.getY() - childMeasuredHeight/2);
                        cr = (int)(targetView.getX()  + childMeasuredWidth/2);
                        cb = (int)(targetView.getY() + childMeasuredHeight/2);
                    } else if(layoutParams.mLayoutOn == BadgeLayoutParams.LEFT_BOTTOM){
                        cl = (int)(targetView.getX()  - childMeasuredWidth/2 );
                        ct = (int) (targetView.getY() + targetView.getMeasuredHeight() - childMeasuredHeight/2);
                        cr = (int)(targetView.getX()  + childMeasuredWidth/2 );
                        cb = (int)(targetView.getY() + targetView.getMeasuredHeight() + childMeasuredHeight/2);
                    }
                    child.layout(cl + layoutParams.leftMargin - layoutParams.rightMargin ,
                            ct + layoutParams.topMargin - layoutParams.bottomMargin,
                            cr + layoutParams.leftMargin - layoutParams.rightMargin,
                            cb + layoutParams.topMargin - layoutParams.bottomMargin);
                }
            }
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BadgeLayoutParams(getContext(),attrs);
    }

    public static class BadgeLayoutParams extends LayoutParams {

        public static final int LEFT_TOP = 0;
        public static final int LEFT_BOTTOM = 1;
        public static final int RIGHT_TOP = 2;
        public static final int RIGHT_BOTTOM = 3;

        public int mTargetViewId;
        public int mLayoutOn;

        public BadgeLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.BadgeLayout_LayoutParams);
            mTargetViewId = typedArray.getResourceId(R.styleable.BadgeLayout_LayoutParams_targetView, -1);
            mLayoutOn = typedArray.getInt(R.styleable.BadgeLayout_LayoutParams_layoutOn, RIGHT_TOP);
            typedArray.recycle();
        }

    }
}



