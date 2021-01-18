package tools.extend;


import java.util.List;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FindByIsActive;

public class PhoneUtils {
    public static  String getPhoneService(){
        String phone_service="";
        if (PublicCache.findByIsActive != null&&PublicCache.findByIsActive.getList() != null) {
            List<FindByIsActive.ListBean> list=  PublicCache.findByIsActive.getList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() ==PublicCache.site) {
                    phone_service=list.get(i).getPhone1();
                }
            }
        }
        return phone_service;
    }
    public static  String getPhoneAfter(){
        String phone_after="";
        if (PublicCache.findByIsActive != null&&PublicCache.findByIsActive.getList() != null) {
            List<FindByIsActive.ListBean> list=  PublicCache.findByIsActive.getList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() ==PublicCache.site) {
                    phone_after=list.get(i).getPhone2();
                }
            }
        }
        return phone_after;
    }
}
