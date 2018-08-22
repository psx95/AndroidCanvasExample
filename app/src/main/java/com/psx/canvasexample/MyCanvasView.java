package com.psx.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvasView extends View {

    private Paint paint;
    private Path path;
    private int drawColor, backgroundColor;
    private Bitmap bitmap;
    private Canvas canvas;
    private float startX, startY;
    private static final int TOUCH_TOLERANCE = 4;

    public MyCanvasView(Context context) {
        super(context);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.opaque_orange, null);
        drawColor = ResourcesCompat.getColor(getResources(), R.color.opaque_yellow, null);
        path = new Path();
        paint = new Paint();
        paint.setColor(drawColor);
        // smooths out the edges without affecting the shape of what's being drawn.
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }
        return true;
    }

    private void touchUp() {
        path.reset();
    }

    private void touchMove(float x, float y) {
        float dX = Math.abs(x - startX);
        float dY = Math.abs(y - startY);
        if (dX >= TOUCH_TOLERANCE || dY >= TOUCH_TOLERANCE) {
            path.quadTo(startX, startY, (x + startX) / 2, (y + startY) / 2);
            startX = x;
            startY = y;
            canvas.drawPath(path, paint);
        }
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        startX = x;
        startY = y;
    }
}
