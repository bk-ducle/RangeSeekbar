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
        mRangeSeebar.setLeftIndex(2);
        mRangeSeebar.setRightIndex(4);
        mRangeSeebar.setOnRangeBarChangeListener(new RangeSeekbar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeSeekbar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                mTv2.setText(leftThumbIndex + " - " + rightThumbIndex);
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
