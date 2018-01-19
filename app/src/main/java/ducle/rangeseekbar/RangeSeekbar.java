package ducle.rangeseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RangeSeekbar extends View {

    private int mTickCount;
    private int mLeftIndex;
    private int mRightIndex;

    private Thumb mLeftThumb;
    private Thumb mRightThumb;
    private Line mBackgroundLine;
    private Line mFrontLine;

    private int mPadding = 30;
    private Integer mTickStart;
    private Integer mTickEnd;
    private boolean isFirst = true;
    private int mNormalThumbColor;
    private int mPressThumbColor;
    private int mFrontLineColor;
    private int mBackgroundLineColor;
    private float mBackgroundLineWidth;
    private float mFrontLineWidth;

    private OnRangeBarChangeListener mListener;


    public RangeSeekbar(Context context) {
        super(context);
    }

    public RangeSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekbar, 0, 0);
        try {
            mTickStart = ta.getInteger(R.styleable.RangeSeekbar_tick_start, 0);
            mTickEnd = ta.getInteger(R.styleable.RangeSeekbar_tick_end, 100);
            mNormalThumbColor = ta.getColor(R.styleable.RangeSeekbar_normal_thumb_color
                    , getResources().getColor(R.color.colorPrimary));
            mPressThumbColor = ta.getColor(R.styleable.RangeSeekbar_press_thumb_color
                    , getResources().getColor(R.color.colorAccent));
            mFrontLineColor = ta.getColor(R.styleable.RangeSeekbar_front_line_color
                    , getResources().getColor(R.color.colorPrimary));
            mBackgroundLineColor = ta.getColor(R.styleable.RangeSeekbar_background_line_color
                    , getResources().getColor(android.R.color.darker_gray));
            mBackgroundLineWidth = ta.getDimension(R.styleable.RangeSeekbar_background_line_width, 10f);
            mFrontLineWidth = ta.getDimension(R.styleable.RangeSeekbar_front_line_width, 10f);
            Integer tickCount = mTickEnd - mTickStart;
            if (tickCount > 1) {
                mTickCount = tickCount;
                mLeftIndex = 0;
                mRightIndex = tickCount;
                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex + mTickStart, mRightIndex + mTickStart);
                }
            }
        } finally {
            ta.recycle();
        }
        createBackgroundLine();
        createThumb();
        createFrontLine();
    }

    private void createThumb() {
        mLeftThumb = new Thumb();
        mRightThumb = new Thumb();
    }

    private void createBackgroundLine() {
        mBackgroundLine = new Line();
    }

    private void createFrontLine() {
        mFrontLine = new Line();
    }

    public RangeSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOnRangeBarChangeListener(RangeSeekbar.OnRangeBarChangeListener listener) {
        mListener = listener;
    }

    public int getLeftIndex() {
        return mLeftIndex;
    }

    public float getCoordinateIndex(int index) {
        return 30 + index * mBackgroundLine.getTickDistance();
    }

    public void setLeftIndex(int leftIndex) {
        changeToFirst();
        if (leftIndex < mTickStart || leftIndex > mTickEnd) {
            this.mLeftIndex = 0;
        } else {
            this.mLeftIndex = leftIndex - mTickStart;
        }
    }

    public int getRightIndex() {
        return mRightIndex;
    }

    public void setRightIndex(int rightIndex) {
        changeToFirst();
        if (rightIndex < mTickStart || rightIndex > mTickEnd) {
            this.mRightIndex = mTickEnd - mTickStart;
        } else {
            this.mRightIndex = rightIndex - mTickStart;
        }
    }

    /**
     * set color for front line
     */
    public void setFrontLineColor(int color) {
        changeToFirst();
        this.mFrontLineColor = color;
    }

    /**
     * set color for background line
     */
    public void setBackgroundLineColor(int color) {
        changeToFirst();
        this.mBackgroundLineColor = color;
    }

    /**
     * set color for thumb when it's in normal state
     */
    public void setNormalThumbColor(int color) {
        changeToFirst();
        this.mNormalThumbColor = color;
    }

    /**
     * set color for thumb when is's in press state
     */
    public void setPressThumbColor(int color) {
        changeToFirst();
        this.mPressThumbColor = color;
    }

    public void setBackgroundLineWidth(float width) {
        changeToFirst();
        this.mBackgroundLineWidth = width;
    }

    public void setFrontLineWidth(float width) {
        changeToFirst();
        this.mFrontLineWidth = width;
    }

    private void changeToFirst() {
        if (!isFirst) {
            this.isFirst = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            mBackgroundLine.setWHP(canvas.getWidth(), canvas.getHeight(), mPadding);
            mBackgroundLine.setTick(mTickStart, mTickEnd);
            mBackgroundLine.setPaint(mBackgroundLineWidth, mBackgroundLineColor);

            mLeftThumb.setX(getCoordinateIndex(mLeftIndex));
            mLeftThumb.setY(canvas.getHeight() / 2);
            mLeftThumb.setRadius(25f);
            mLeftThumb.setNormalColor(mNormalThumbColor);
            mLeftThumb.setPressColor(mPressThumbColor);

            mRightThumb.setX(getCoordinateIndex(mRightIndex));
            mRightThumb.setY(canvas.getHeight() / 2);
            mRightThumb.setRadius(25f);
            mRightThumb.setNormalColor(mNormalThumbColor);
            mRightThumb.setPressColor(mPressThumbColor);

            mFrontLine.setPaint(mFrontLineWidth, mFrontLineColor);
            mFrontLine.setLeftX(mLeftThumb.getX());
            mFrontLine.setRightX(mRightThumb.getX());
            mFrontLine.setY(canvas.getHeight() / 2);

            isFirst = false;
        }
        mBackgroundLine.draw(canvas);
        mFrontLine.draw(canvas);

        mLeftThumb.draw(canvas);
        mRightThumb.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onActionUp(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX());
                return true;
            default:
                return false;
        }
    }

    private void onActionMove(float x) {
        boolean handle = false;
        if (mLeftThumb.isPress()) {
            mFrontLine.setLeftX(x);
            moveThumb(mLeftThumb, x);
            handle = true;
        } else if ((mRightThumb.isPress())) {
            mFrontLine.setRightX(x);
            moveThumb(mRightThumb, x);
            handle = true;
        }
        if (handle) {
            if (mLeftThumb.getX() > mRightThumb.getX()) {
                Thumb temp = mLeftThumb;
                mLeftThumb = mRightThumb;
                mRightThumb = temp;
            }

            int leftIndex = mBackgroundLine.getNearestTickIndex(mLeftThumb);
            int rightIndex = mBackgroundLine.getNearestTickIndex(mRightThumb);

            if (leftIndex != mLeftIndex || rightIndex != mRightIndex) {
                mLeftIndex = leftIndex;
                mRightIndex = rightIndex;
                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex + mTickStart, mRightIndex + mTickStart);
                }
            }
        }
    }

    private void moveThumb(Thumb thumb, float x) {
        if (x > mBackgroundLine.getRightX()) {
            thumb.setX(mBackgroundLine.getRightX());
        } else if (x < mBackgroundLine.getLeftX()) {
            thumb.setX(mBackgroundLine.getLeftX());
        } else {
            thumb.setX(x);
        }
        invalidate();
    }

    private void onActionUp(float x, float y) {
        mFrontLine.setLeftX(getCoordinateIndex(mLeftIndex));
        mFrontLine.setRightX(getCoordinateIndex(mRightIndex));
        if (mLeftThumb.isPress()) {
            releaseThump(mLeftThumb);
        } else if (mRightThumb.isPress()) {
            releaseThump(mRightThumb);
        }
    }

    private void releaseThump(Thumb thumb) {
        float nearestTickX = mBackgroundLine.getNearestTickCoordinate(thumb);
        thumb.setX(nearestTickX);
        thumb.release();
        invalidate();
    }

    private void onActionDown(float x, float y) {
        if (!mLeftThumb.isPress() && mLeftThumb.isInTargetZone(x, y)) {
            pressThumb(mLeftThumb);
        } else if (!mRightThumb.isPress() && mRightThumb.isInTargetZone(x, y)) {
            pressThumb(mRightThumb);
        }
    }

    private void pressThumb(Thumb thumb) {
        thumb.press();
        invalidate();
    }

    public int getTickStart() {
        return mTickStart;
    }

    public int getTickEnd() {
        return mTickEnd;
    }

    public interface OnRangeBarChangeListener {
        void onIndexChangeListener(RangeSeekbar rangeBar, int leftThumbIndex, int rightThumbIndex);
    }
}
