package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Freewind
 * Date: 12-9-1
 * Time: 下午6:40
 */
public class MyImageView extends ImageView implements View.OnTouchListener {

    private Path path;
    private List<Path> paths = new ArrayList<Path>();

    public MyImageView(Context context) {
        super(context);
        this.setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        Log.i("xxx", "" + paths.size());
        for (Path path : paths) {
            canvas.drawPath(path, paint);
        }
    }

    private String info(PointF p) {
        if (p == null) return "(-1,-1)";
        return "(" + p.x + "," + p.y + ")";
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                PointF p = getPoint(event, 0);
                path.moveTo(p.x, p.y);
                paths.add(path);
                break;
            case MotionEvent.ACTION_MOVE:
//                if (event.getPointerCount() == 1) {
                p = getPoint(event, 0);
                path.lineTo(p.x, p.y);
//                }
                break;
        }
        invalidate();
        return true;
    }

    private PointF getPoint(MotionEvent event, int index) {
        return new PointF(event.getX(index), event.getY(index));
    }
}

