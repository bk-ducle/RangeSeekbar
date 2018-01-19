package ducle.rangeseekbar;

import android.graphics.Canvas;
import android.graphics.Paint;

class Thumb {
    private float mTargetRadius = 50;
    private boolean mIsPress = false;
    private float mX;
    private float mY;
    private float mThumbRadius;
    private Paint mNormalPaint;
    private Paint mPressedPaint;

    public Thumb() {
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPressedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setY(float y) {
        this.mY = y;
    }

    public float getY() {
        return mY;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        this.mX = x;
    }

    public void setNormalColor(int color) {
        mNormalPaint.setColor(color);
    }

    public void setPressColor(int color) {
        mPressedPaint.setColor(color);
    }

    public void setRadius(float radius) {
        this.mThumbRadius = radius;
    }

    public boolean isPress() {
        return mIsPress;
    }

    public void press() {
        this.mIsPress = true;
    }

    public void release() {
        this.mIsPress = false;
    }

    public boolean isInTargetZone(float x, float y) {
        return Math.abs(x - mX) <= mTargetRadius && Math.abs(y - mY) <= mTargetRadius;
    }

    public void draw(Canvas canvas) {
        if (mIsPress) {
            canvas.drawCircle(mX, mY, mThumbRadius, mPressedPaint);
        } else {
            canvas.drawCircle(mX, mY, mThumbRadius, mNormalPaint);
        }
    }
}
