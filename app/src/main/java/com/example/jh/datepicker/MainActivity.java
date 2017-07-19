package com.example.jh.datepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jh.datepicker.view.ChangeDatePopwindow;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从项目抽离的日期选择器
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.ll_selectedtime)
    LinearLayout ll_selectedtime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_nextday)
    Button btnNextday;
    @BindView(R.id.btn_preday)
    Button btnPreday;

    private long mStartTime;
    private long mEndTime;
    GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showCurrentTime();
        ll_selectedtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] str = new String[10];
                final ChangeDatePopwindow mChangeBirthDialog = new ChangeDatePopwindow(MainActivity.this);
//                mChangeBirthDialog.setDate("2017", "6", "20");
                mChangeBirthDialog.showAtLocation(frameLayout, Gravity.TOP, 0, 400);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {

                        selectDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                    }
                });
            }
        });
    }

    private void showCurrentTime() {

        GregorianCalendar today = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));

        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);
        int hour = today.get(Calendar.HOUR_OF_DAY);
        int min = today.get(Calendar.MINUTE);
        GregorianCalendar startTime = new GregorianCalendar(year, month, day, 0, 0, 0);
        startTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        GregorianCalendar endTime = new GregorianCalendar(year, month, day, hour, min, 0);
        endTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mStartTime = startTime.getTimeInMillis() / 1000;
        mEndTime = endTime.getTimeInMillis() / 1000;
        showDate(year, month + 1, day);
        System.out.println("---" + mStartTime + "-" + "" + mEndTime);
    }


    public void selectDate(int year, int month, int day) {
        GregorianCalendar startTime = new GregorianCalendar(year, month, day, 0, 0, 0);
        startTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mStartTime = startTime.getTimeInMillis() / 1000;
        GregorianCalendar endTime = new GregorianCalendar(year, month, day, 24, 0, 0);
        endTime.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mEndTime = endTime.getTimeInMillis() / 1000;
        System.out.println("---" + mStartTime + "-" + "" + mEndTime);
        showDate(year, month + 1, day);

    }

    private void showDate(int year, int month, int date) {
        tvTime.setText(year + "-" + month + "-" + date);
    }

    @OnClick({R.id.btn_nextday, R.id.btn_preday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_nextday:
                mStartTime = mStartTime + 86400;
                mEndTime = mStartTime + 86400;

                System.out.println("---" + mStartTime + "-" + "" + mEndTime);
                gregorianCalendar.setTimeInMillis(mStartTime * 1000);
                int year = gregorianCalendar.get(Calendar.YEAR);
                int month = gregorianCalendar.get(Calendar.MONTH);
                int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
                // 与当前日期调整不要超过今天
//                if(day > Calendar.DAY_OF_MONTH){
//                    Toast.makeText(this, "明天可以查看最新轨迹哦", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                showDate(year, month + 1, day);
                break;
            case R.id.btn_preday:
                mStartTime = mStartTime - 86400;
                mEndTime = mStartTime + 86400;
                System.out.println("---" + mStartTime + "-" + "" + mEndTime);

                gregorianCalendar.setTimeInMillis(mStartTime * 1000);
                year = gregorianCalendar.get(Calendar.YEAR);
                month = gregorianCalendar.get(Calendar.MONTH);
                day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
                showDate(year, month + 1, day);
                break;
        }
    }
}
