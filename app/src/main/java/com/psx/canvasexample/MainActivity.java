package com.psx.canvasexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas canvas;
    private Paint paint = new Paint();
    private Paint paintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap bitmap;
    private ImageView imageView;
    private Rect rect = new Rect();
    private Rect bounds = new Rect();
    private static final int OFFSET = 120;
    private int offset = OFFSET;
    private static final int MULTIPLIER = 100;
    private int colorBackground, colorAccent, colorRectangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        colorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        colorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        paint.setColor(colorBackground);
        paintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        paintText.setTextSize(70);
        imageView = findViewById(R.id.myimageview);
    }

    public void drawSomething(View view) {
        int fullWidth = view.getWidth();
        int fullHeight = view.getHeight();
        int halfWidth = fullWidth / 2;
        int halfHeight = fullHeight / 2;
        if (offset == OFFSET) {
            bitmap = Bitmap.createBitmap(fullWidth, fullHeight, Bitmap.Config.ARGB_8888);
            // Associate bitmap with the imageview
            imageView.setImageBitmap(bitmap);
            // associating a bitmap with the canvas so that drawing on the canvas draws on the bitmap
            canvas = new Canvas(bitmap);
            canvas.drawColor(colorBackground);
            canvas.drawColor(colorBackground);
            canvas.drawText(getString(R.string.keep_tapping), 100, 100, paintText);
            offset += OFFSET;
        } else if (offset < halfWidth && offset < halfHeight) {
            paint.setColor(colorBackground - (offset * MULTIPLIER));
            rect.set(offset, offset, fullWidth - offset, fullHeight - offset);
            canvas.drawRect(rect, paint);
            offset += OFFSET;
        } else {
            paint.setColor(colorAccent);
            canvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, paint);
            String text = getString(R.string.done);
            paintText.getTextBounds(text, 0, text.length(), bounds);
            int x = halfWidth - bounds.centerX();
            int y = halfHeight - bounds.centerY();
            canvas.drawText(text, x, y, paintText);
        }
        view.invalidate();
    }
}
