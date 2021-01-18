package cn.com.taodaji.ui.activity.login;


import com.orm.SugarRecord;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import static cn.com.taodaji.common.Constants.PURCHASER;
import static cn.com.taodaji.common.Constants.SUPPLIER;

/**
 * Created by yangkuo on 2018-09-17.
 */
public class LoginUtils {

    public static void loginSupplier() {
        //初始化登陆数据
        LoginSupplierBean loginSupplier = LoginSupplierBean.first(LoginSupplierBean.class);
        if (loginSupplier != null) {
            //已登录  初始化数据
            PublicCache.loginSupplier = loginSupplier;
            PublicCache.loginPurchase = null;
            PublicCache.login_mode = SUPPLIER;
            PublicCache.site = PublicCache.loginSupplier.getSite();
            PublicCache.site_name = PublicCache.loginSupplier.getSiteName();
            PublicCache.site_login = PublicCache.site;
            PublicCache.site_name_login = PublicCache.site_name;
            PublicCache.userNameId = loginSupplier.getEntityId();
        }
    }


    public static void loginPurchase(){
        LoginPurchaseBean loginPurchase = SugarRecord.first(LoginPurchaseBean.class);
        //已登录  初始化数据
        if (loginPurchase != null) {
            PublicCache.loginPurchase = loginPurchase;
            PublicCache.loginSupplier = null;
            PublicCache.login_mode = PURCHASER;
            PublicCache.site = PublicCache.loginPurchase.getSite();
            PublicCache.site_name = PublicCache.loginPurchase.getSiteName();
            PublicCache.site_login = PublicCache.site;
            PublicCache.site_name_login = PublicCache.site_name;
            int en;
            if (loginPurchase.getEmpRole() == 0) en = loginPurchase.getEntityId();
            else en = loginPurchase.getSubUserId();
            PublicCache.userNameId = en;

        }
    }

}
