package tools.activity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.base.activity.ActivityManage;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;

import java.lang.reflect.Method;

/**
 * 继承该类的子类，可以设置MenuToolbar的相关内容
 */
public abstract class MenuToolbarActivity extends SimpleToolbarActivity {

    //Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        setOverflowIconVisible(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_homepage:
                goToPage(0);
                break;
            case R.id.action_market:
                goToPage(1);
                break;
            case R.id.action_pickfood:
                goToPage(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void goToPage(int currentItem) {
        ActivityManage.setTopActivity(ManageActivity.class);
        ManageActivity manageActivity = ActivityManage.getActivity(ManageActivity.class);
        if (manageActivity != null && manageActivity.mViewPager != null)
            manageActivity.mViewPager.setCurrentItem(currentItem, false);
    }


    /**
     * 显示OverflowMenu的Icon
     *
     * @param menu
     */
    private void setOverflowIconVisible(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.d("OverflowIconVisible", e.getMessage());
                }
            }
        }
    }
}
