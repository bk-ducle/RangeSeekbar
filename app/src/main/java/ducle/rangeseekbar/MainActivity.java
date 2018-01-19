package ducle.rangeseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RangeSeekbar mRangeSeebar;
    private TextView mTv1;
    private TextView mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mRangeSeebar.setLeftIndex(5);
        mRangeSeebar.setRightIndex(10);
        mRangeSeebar.setBackgroundLineColor(getResources().getColor(R.color.gray));
        mRangeSeebar.setFrontLineColor(getResources().getColor(android.R.color.holo_red_light));
        mRangeSeebar.setNormalThumbColor(getResources().getColor(R.color.primary));
        mRangeSeebar.setPressThumbcolor(getResources().getColor(R.color.colorAccent));
        mRangeSeebar.setBackgroundLineWidth(10f);
        mRangeSeebar.setOnRangeBarChangeListener(new RangeSeekbar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeSeekbar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                mTv2.setText(new StringBuffer().append(leftThumbIndex).append(" - ").append(rightThumbIndex));
            }
        });
    }

    private void initViews() {
        mRangeSeebar = findViewById(R.id.rs);
        mTv1 = findViewById(R.id.tvKhoangCho);
        mTv2 = findViewById(R.id.tvKhoangChon);
        mTv1.setText(mRangeSeebar.getTickStart() + " - " + mRangeSeebar.getTickEnd());
    }
}
