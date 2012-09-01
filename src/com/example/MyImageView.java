package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Freewind
 * Date: 12-9-1
 * Time: 18:40
 */
public class MyImageView extends ImageView implements View.OnTouchListener {

    private int id = -1;
    private Path path;
    private List<Path> paths = new ArrayList<Path>();
    private List<PointF> points = new ArrayList<PointF>();

    boolean multiTouch = false;

    public MyImageView(Context context) {
        super(context);
        this.setOnTouchListener(this);
    }

    public void reset() {
        paths.clear();
        points.clear();
        path = null;
        id = -1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = createPen(Color.RED, 5);
        paint.setStyle(Paint.Style.STROKE);
        for (Path path : paths) {
            canvas.drawPath(path, paint);
        }

        for (int i = 0; i < points.size(); i++) {
            PointF p = points.get(i);
            canvas.drawCircle(p.x, p.y, 20, createPen(Color.GREEN, 1));
            canvas.drawText("" + (i + 1), p.x, p.y, createPen(Color.WHITE, 1));
        }
    }

    private PointF copy(PointF p) {
        PointF copy = new PointF();
        copy.set(p);
        return copy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                multiTouch = false;

                id = event.getPointerId(0);
                PointF p = getPoint(event, 0);
                path = new Path();
                path.moveTo(p.x, p.y);
                paths.add(path);

                points.add(copy(p));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                multiTouch = true;
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int tId = event.getPointerId(i);
                    if (tId != id) {
                        points.add(getPoint(event, i));
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!multiTouch) {
                    p = getPoint(event, 0);
                    path.lineTo(p.x, p.y);
                }
                break;
        }

        invalidate();

        return true;
    }

    private PointF getPoint(MotionEvent event, int index) {
        return new PointF(event.getX(index), event.getY(index));
    }

    private Paint createPen(int color, int width) {
        Paint pen = new Paint();
        pen.setColor(color);
        pen.setStrokeWidth(width);
        return pen;
    }

}

