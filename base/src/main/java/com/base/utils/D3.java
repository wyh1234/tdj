package com.base.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D3 {


    public static void getrad() {
        List list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            int bule = (int) (1 + Math.random() * 10000);
            list.add(bule);
        }
        List<Integer> sste = JavaMethod.sortAsc(list);
        for (Integer integer : sste) {
            for (int i = 1; i <= integer; i++) {
                List lls = getss();
                if (i == integer) Log.e("ssd3", TextUtils.join(",", lls.toArray()));
            }
        }
    }


    private static List getss() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33));
        List ss = new ArrayList();
        for (int i = 0; i < 6; i++) {
            int rad = (int) (Math.random() * list.size());
            ss.add(list.get(rad));
            list.remove(rad);
        }
        int bule = (int) (1 + Math.random() * 16);
        List<Integer> sste = JavaMethod.sortAsc(ss);
        sste.add(bule);
        return sste;
    }


}
