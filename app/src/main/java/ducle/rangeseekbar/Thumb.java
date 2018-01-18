package ducle.rangeseekbar;

import android.graphics.Canvas;
import android.graphics.Paint;

class Thumb {
    private float mTargetRadius;
    private boolean mIsPress = false;
    private float mX;
    private float mY;
    private float mThumbRadius;
    private int mColorNormal;
    private int mColorPressed;
    private Paint mPaintNormal;
    private Paint mPaintPressed;

    public Thumb() {
    }

    public Thumb(float mX, float mThumbRadius, int mColorNormal, int mColorPressed) {
        this.mTargetRadius = 50;
        this.mX = mX;
        this.mThumbRadius = mThumbRadius;
        this.mColorNormal = mColorNormal;
        this.mColorPressed = mColorPressed;

        mPaintNormal = new Paint();
        mPaintNormal.setColor(mColorNormal);
        mPaintNormal.setAntiAlias(true);

        mPaintPressed = new Paint();
        mPaintPressed.setColor(mColorPressed);
        mPaintPressed.setAntiAlias(true);
    }

    public Thumb(float mThumbRadius, int mColorNormal, int mColorPressed) {
        this.mTargetRadius = 50;
        this.mThumbRadius = mThumbRadius;
        this.mColorNormal = mColorNormal;
        this.mColorPressed = mColorPressed;

        mPaintNormal = new Paint();
        mPaintNormal.setColor(mColorNormal);
        mPaintNormal.setAntiAlias(true);

        mPaintPressed = new Paint();
        mPaintPressed.setColor(mColorPressed);
        mPaintPressed.setAntiAlias(true);
    }

    public void setY(float y) {
        this.mY = y;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        this.mX = x;
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
            canvas.drawCircle(mX, mY, mThumbRadius, mPaintPressed);
        } else {
            canvas.drawCircle(mX, mY, mThumbRadius, mPaintNormal);
        }
    }
}
