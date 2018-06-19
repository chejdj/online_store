package com.chejdj.online_store.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class rectange_radius_imageview extends android.support.v7.widget.AppCompatImageView {
    private float height;
    private float width;
    public rectange_radius_imageview(Context context) {
        super(context);
    }

    public rectange_radius_imageview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public rectange_radius_imageview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
         height=getHeight();
         width=getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //直接对画布进行裁剪
        float x=getX();
        float y=getY();
        int cut=24;
        if (width > cut && height > cut) {
            Path path = new Path();
            path.moveTo(x+cut, y);
            path.lineTo(x+width - cut, y);
            path.quadTo(x+width, y, x+width, y+cut);
            path.lineTo(x+width, y+height - cut);
            path.quadTo(x+width, y+height, x+width - cut, y+height);
            path.lineTo(x+cut, y+height);
            path.quadTo(x, y+height, x, y+height - cut);
            path.lineTo(x, y+cut);
            path.quadTo(x, y, x+cut, y);
            canvas.clipPath(path);
        }
       super.onDraw(canvas);
    }
}
