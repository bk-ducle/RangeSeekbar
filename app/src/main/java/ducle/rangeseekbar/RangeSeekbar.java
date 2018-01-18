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
    private Line mLine;
    private Line mLineDame;

    private int mPadding = 30;
    private Integer mTickStart;
    private Integer mTickEnd;
    private boolean isFirst = true;

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
        createLine();
        createThumb();
        createLineDam();
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
        return 30 + index * mLine.getTickDistance();
    }

    public void setLeftIndex(int mLeftIndex) {
        this.mLeftIndex = mLeftIndex = mTickStart;
    }

    public int getRightIndex() {
        return mRightIndex;
    }

    public void setRightIndex(int mRightIndex) {
        this.mRightIndex = mRightIndex - mTickStart;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            mLine.setWHP(canvas.getWidth(), canvas.getHeight(), mPadding);
            mLine.setTick(mTickStart, mTickEnd);
            mLine.setPaint(10f, getContext().getResources().getColor(R.color.gray));
            mLeftThumb.setX(getCoordinateIndex(mLeftIndex));
            mLeftThumb.setY(canvas.getHeight() / 2);
            mRightThumb.setX(getCoordinateIndex(mRightIndex));
            mRightThumb.setY(canvas.getHeight() / 2);
            mLineDame.setLeftX(mLeftThumb.getX());
            mLineDame.setRightX(mRightThumb.getX());
            mLineDame.setY(canvas.getHeight() / 2);
            isFirst = false;
        }
        mLine.draw(canvas, 0);
        mLineDame.drawArea(canvas);

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
            mLineDame.setLeftX(x);
            moveThumb(mLeftThumb, x);
            handle = true;
        } else if ((mRightThumb.isPress())) {
            mLineDame.setRightX(x);
            moveThumb(mRightThumb, x);
            handle = true;
        }
        if (handle) {
            if (mLeftThumb.getX() > mRightThumb.getX()) {
                Thumb temp = mLeftThumb;
                mLeftThumb = mRightThumb;
                mRightThumb = temp;
            }

            int leftIndex = mLine.getNearestTickIndex(mLeftThumb);
            int rightIndex = mLine.getNearestTickIndex(mRightThumb);

            if (leftIndex != mLeftIndex || rightIndex != mRightIndex) {
                mLeftIndex = leftIndex;
                mRightIndex = rightIndex;
                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex + mTickStart, mRightIndex + mTickStart);
                }
            }


//            mLineDame.setLeftX(getCoordinateIndex(mLeftIndex));
//            mLineDame.setRightX(getCoordinateIndex(mRightIndex));
        }
    }

    private void moveThumb(Thumb thumb, float x) {
        if (x > mLine.getRightX()) {
            thumb.setX(mLine.getRightX());
        } else if (x < mLine.getLeftX()) {
            thumb.setX(mLine.getLeftX());
        } else {
            thumb.setX(x);
        }
        invalidate();
    }

    private void onActionUp(float x, float y) {
        mLineDame.setLeftX(getCoordinateIndex(mLeftIndex));
        mLineDame.setRightX(getCoordinateIndex(mRightIndex));
        if (mLeftThumb.isPress()) {
            releaseThump(mLeftThumb);
        } else if (mRightThumb.isPress()) {
            releaseThump(mRightThumb);
        }
    }

    private void releaseThump(Thumb thumb) {
        float nearestTickX = mLine.getNearestTickCoordinate(thumb);
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

    private void createThumb() {
        mLeftThumb = new Thumb(getCoordinateIndex(mLeftIndex), 25
                , getContext().getResources().getColor(R.color.primary)
                , getContext().getResources().getColor(android.R.color.holo_green_light));
        mRightThumb = new Thumb(25
                , getContext().getResources().getColor(R.color.primary)
                , getContext().getResources().getColor(android.R.color.holo_green_light));
    }

    private void createLine() {
        mLine = new Line();
    }

    private void createLineDam() {
        mLineDame = new Line(mLeftThumb.getX(), mRightThumb.getX(), 10f
                , getContext().getResources().getColor(R.color.primary));
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
