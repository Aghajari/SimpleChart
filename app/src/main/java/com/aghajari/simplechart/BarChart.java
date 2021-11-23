package com.aghajari.simplechart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BarChart extends View {

    final List<ChartData> list = new ArrayList<>();
    private final Rect textBounds = new Rect();

    Drawable drawable, drawablePlace;
    Bitmap placeHolder = null;

    private boolean forceInvalidate = false;
    final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final Paint textPaint = new Paint();
    int barWidth = 100;
    int barHeight = 100;
    float radius = 20;
    float max = -1;
    float textPadding = 8;

    public BarChart(Context context) {
        this(context, null);
    }

    public BarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textPadding *= context.getResources().getDisplayMetrics().density;

        GradientDrawable GD = new GradientDrawable();
        GD.setColors(new int[]{0xFF84B043, 0xFFEEC54D, 0xFFD85A47});
        drawable = GD;
        drawablePlace = new ColorDrawable(Color.LTGRAY);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(14 * context.getResources().getDisplayMetrics().scaledDensity);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        barHeight = bottom - top - getPaddingBottom() - getPaddingTop();
        forceInvalidate = true;
        invalidate();
    }

    public void addData(float data, String title) {
        this.list.add(new ChartData(data, title));
    }

    public void setRadius(float radius) {
        this.radius = radius;
        forceInvalidate = true;
        invalidate();
    }

    public void setTextPadding(float padding) {
        textPadding = padding;
        forceInvalidate = true;
        invalidate();
    }

    public void setBarWidth(int width) {
        this.barWidth = width;
        forceInvalidate = true;
        invalidate();
    }

    public void setBarDrawable(Drawable drawable) {
        this.drawable = drawable;
        forceInvalidate = true;
        invalidate();
    }

    public void setMaxValue(float max) {
        this.max = max;
        forceInvalidate = true;
        invalidate();
    }

    public void setBarDrawablePlace(Drawable drawable) {
        this.drawablePlace = drawable;
        forceInvalidate = true;
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        forceInvalidate = true;
        invalidate();
    }

    public void setTextSize(float size) {
        textPaint.setTextSize(size * getContext().getResources().getDisplayMetrics().scaledDensity);
        forceInvalidate = true;
        invalidate();
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() == GONE || getParent() == null || list.isEmpty())
            return;

        textPaint.getTextBounds("AMIR", 0, 1, textBounds);
        int bh = (int) (this.barHeight - textBounds.height() - (textPadding * 2));

        if (forceInvalidate || placeHolder == null) {
            placeHolder = Bitmap.createBitmap(barWidth, bh, Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(placeHolder);
            drawablePlace.setBounds(0, 0, barWidth, bh);
            clipPath(c, placeHolder);
            drawablePlace.draw(c);
        }

        float maxData = max;
        if (maxData == -1) {
            maxData = 0;
            for (ChartData d : list)
                maxData += d.data;
        }
        maxData = Math.max(1, maxData);

        float left = getPaddingLeft();
        float top = getPaddingTop();
        float bottom = bh + getPaddingTop();

        int padding = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        padding -= list.size() * barWidth;
        if (list.size() == 0)
            padding /= 2;
        else
            padding = Math.max(padding / (list.size() - 1), 0);

        for (ChartData chartData : list) {
            canvas.drawBitmap(placeHolder, left, top, null);

            Bitmap bitmap = chartData.tmpBitmap;

            int innerHeight = (int) (chartData.data * bh / maxData);
            int innerTop = (int) (bottom - innerHeight);

            if (innerHeight > 0) {
                if (forceInvalidate || chartData.tmpBitmap == null || chartData.tmpBitmap.getHeight() != innerHeight) {
                    bitmap = Bitmap.createBitmap(barWidth, innerHeight, Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(bitmap);
                    drawable.setBounds(0, -innerTop, barWidth, -innerTop + bh);
                    clipPath(c, bitmap);
                    drawable.draw(c);

                    chartData.tmpBitmap = bitmap;
                }
            }

            canvas.drawBitmap(bitmap, left, innerTop, null);

            textPaint.getTextBounds(chartData.title, 0, chartData.title.length(), textBounds);
            float x = (left + barWidth / 2f) - textBounds.width() / 2f;
            float y = bottom + textPadding + textBounds.height();

            canvas.drawText(chartData.title, x, y, textPaint);
            left += barWidth + padding;
        }

        forceInvalidate = false;
    }

    private void clipPath(Canvas canvas, Bitmap bitmap) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
    }

    private static class ChartData {
        private Bitmap tmpBitmap = null;

        float data;
        String title;

        public ChartData(float data, String title) {
            this.data = data;
            this.title = title;
        }
    }
}
