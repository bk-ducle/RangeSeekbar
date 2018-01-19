package ducle.rangeseekbar;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line {
    private Paint mPaint;
    private float mLeftX;
    private float mY;
    private float mRightX;
    private int mNumSegments;
    private float mTickDistance;
    private float mTickHeight;
    private float mTickStart;
    private float mTickEnd;

    public Line() {
        super();
    }

    public void setWHP(float width, float height, float padding) {
        mLeftX = padding;
        mRightX = width - padding;
        mY = height / 2;
    }

    public void setTick(int tickStart, int tickEnd) {
        mTickStart = tickStart;
        mTickEnd = tickEnd;
        mNumSegments = tickEnd - tickStart;
        mTickDistance = (mRightX - mLeftX) / mNumSegments;
    }

    public void setPaint(float width, int color) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }

    public void setY(float y) {
        this.mY = y;
    }

    public float getLeftX() {
        return mLeftX;
    }

    public float getRightX() {
        return mRightX;
    }

    public void setLeftX(float LeftX) {
        this.mLeftX = LeftX;
    }

    public void setRightX(float RightX) {
        this.mRightX = RightX;
    }

    public float getTickDistance() {
        return mTickDistance;
    }

    public void setTickCount(int tickCount) {
        float length = mRightX - mLeftX;
        mNumSegments = tickCount - 1;
        mTickDistance = length / mNumSegments;
    }

    public int getNearestTickIndex(Thumb thumb) {
        return (int) ((thumb.getX() - mLeftX + mTickDistance / 2) / mTickDistance);
    }

    public float getNearestTickCoordinate(Thumb thumb) {
        int index = getNearestTickIndex(thumb);
        float nearestCoordinate = mLeftX + index * mTickDistance;
        return nearestCoordinate;
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(mLeftX, mY, mRightX, mY, mPaint);
    }
}
