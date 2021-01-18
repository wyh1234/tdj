package com.base.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import com.base.R;
import com.base.utils.DateUtils;

public abstract class DatePickDialog implements OnDateChangedListener {
    private DatePicker datePicker;
    // private TimePicker timePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;

    private TextView textview;

    public DatePickDialog(Activity activity, String initDateTime, TextView textview) {
        this.activity = activity;
        this.initDateTime = initDateTime;
        this.textview = textview;
        dateTimePicKDialog();
    }

    public abstract void setOnclick(DialogInterface dialog, int whichButton, String dateStr);

    @SuppressLint("WrongConstant")
    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-";
        }

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

    }

    @SuppressLint("InflateParams")
    public void dateTimePicKDialog() {
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.t_datepick, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        init(datePicker);

        ad = new AlertDialog.Builder(activity)
                // .setTitle(initDateTime)
                .setView(dateTimeLayout).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onDateChanged(null, 0, 0, 0);
                        textview.setText(dateTime);
                        setOnclick(dialog, whichButton, dateTime);
                    }
                }).setNegativeButton("取消", null).create();
        ad.show();
        onDateChanged(null, 0, 0, 0);
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        dateTime = DateUtils.parseCalendar("yyyy-MM-dd", calendar);
        ad.setTitle(dateTime);
    }

    private Calendar getCalendarByInintData(String initDateTime) {

        return DateUtils.parseCalendar(initDateTime, "yyyy-MM-dd");
    }

}
