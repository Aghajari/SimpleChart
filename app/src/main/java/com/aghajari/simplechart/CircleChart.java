package com.aghajari.simplechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CircleChart extends View {

    final List<ChartData> data = new ArrayList<>();
    final Paint paint = new Paint();
    final Paint textPaint = new Paint();
    final RectF rectF = new RectF();
    final RectF rectF2 = new RectF();
    final Rect textBounds = new Rect();

    public CircleChart(Context context) {
        this(context, null);
    }

    public CircleChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(14 * context.getResources().getDisplayMetrics().scaledDensity);
        apply();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rectF.set(getPaddingLeft(), getPaddingTop(), right - left - getPaddingRight(), bottom - top - getPaddingBottom());
        apply();
        invalidate();
    }

    public void addData(int color, int textColor, float width, float data) {
        this.data.add(new ChartData(color, textColor, width, data));
        apply();
    }

    public void setTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        invalidate();
    }

    public void setTextSize(float size) {
        textPaint.setTextSize(size * getContext().getResources().getDisplayMetrics().scaledDensity);
        invalidate();
    }

    private void apply() {
        rectF2.set(rectF);
        float w = 0;
        for (ChartData d : data)
            w = Math.max(w, d.width);

        rectF2.left += w / 2;
        rectF2.right -= w / 2;
        rectF2.top += w / 2;
        rectF2.bottom -= w / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() == GONE || getParent() == null || data.isEmpty())
            return;

        float maxData = 0;
        for (ChartData d : data)
            maxData += d.data;
        maxData = Math.max(1, maxData);

        float start = -90;
        final int count = data.size();
        final float r = rectF2.width() / 2f;
        int d = 0;

        for (int i = 0; i < count; i++) {
            ChartData mData = data.get(i);
            paint.setStrokeWidth(mData.width);
            paint.setColor(mData.color);
            float sweep = mData.data * 360 / maxData;
            canvas.drawArc(rectF2, start, sweep, false, paint);
            start += sweep;

            float center = (start - sweep / 2);
            float x = r * (float) Math.cos(Math.toRadians(center));
            float y = r * (float) Math.sin(Math.toRadians(center));
            x += rectF.width() / 2;
            y += rectF.height() / 2;

            int p = (int) Math.floor(mData.data * 100 / maxData);
            d += p;
            if (i == (count - 1) && d != 100)
                p += 100 - d;

            final String text = p + "%";
            textPaint.setColor(mData.textColor);
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, x - textBounds.width() / 2f, y + textBounds.height() / 2f, textPaint);
        }
    }

    private static class ChartData {
        public int color;
        public int textColor;
        public float width;
        public float data;

        public ChartData(int color, int textColor, float width, float data) {
            this.color = color;
            this.textColor = textColor;
            this.width = width;
            this.data = data;
        }
    }
}
