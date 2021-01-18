package com.base.widget;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;


public class BusinessEditText extends EditText {

    private InputFilter[] inputFilter = {new BusinessInputFilter()};

    public BusinessEditText(Context context) {
        super(context, null);
        setFilters(inputFilter);
    }

    public BusinessEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFilters(inputFilter);
    }

    public BusinessEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        setFilters(inputFilter);
    }

    public BusinessEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFilters(inputFilter);
    }



/*    public void afterTextChanged(Editable s) {
        if (s.toString().contains(".")) {
            int index = s.toString().indexOf(".");
            if (index == 0) {
                s.delete(0, 0);
            } else if (s.length() - index - 1 > 2) {
                s.delete(index + 3, s.length());
            }
        }
    }*/

}
