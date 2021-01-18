package com.base.utils;

import com.zyinux.specialstring.relase.SpecialStyle;
import com.zyinux.specialstring.relase.style.AbsoluteSizeStyle;
import com.zyinux.specialstring.relase.style.StyleWrapper;

import java.util.HashMap;

public class MySpecialStyle extends SpecialStyle {

    public SpecialStyle setAbsoluteSizeStyle(int fontSize){
        return setAbsoluteSizeStyle(fontSize,false);
    }

    public SpecialStyle setAbsoluteSizeStyle(int fontSize,boolean save){
        AbsoluteSizeStyle absoluteSizeStyle;
        HashMap<Class,StyleWrapper> styles=getStyles();
        if (styles.containsKey(AbsoluteSizeStyle.class)){
            absoluteSizeStyle=(AbsoluteSizeStyle) styles.get(AbsoluteSizeStyle.class);
            absoluteSizeStyle.setFontSize(fontSize);
            absoluteSizeStyle.setSave(save);
        }else {
            absoluteSizeStyle=new AbsoluteSizeStyle(fontSize);
            absoluteSizeStyle.setSave(save);
            styles.put(absoluteSizeStyle.getClass(),absoluteSizeStyle);
        }
        return this;
    }

}
