package cn.com.taodaji.ui.activity.integral.tools;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    public boolean isRemoving; // 正在删除栈中多个的activities (add for
    // 输密界面，交易结束时，出现安全模块异常)

    /**
     * 实例化一个activity管理类
     *
     * @return
     */
    public static synchronized ActivityManager getActivityManager() {

        if (instance == null) {
            instance = new ActivityManager();
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 把activity加入到管理类堆栈底,如果已经存在堆栈中将推入栈顶
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (isRemoving)
            isRemoving = false;
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activityStack.add(activity);
            } else {
                activityStack.push(activity);
            }

        }
    }

    /**
     * 返回堆栈中activity的数量
     *
     * @return
     */
    public int getActivityNum() {
        return activityStack.size();
    }

    /**
     * 将activity推入栈顶
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        int i = activityStack.indexOf(activity);
        if (i != -1) {
            activityStack.remove(i);
            activityStack.push(activity);
        }
    }

    /**
     * 获得栈顶的activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if ((activityStack != null) && (activityStack.size() > 0)) {
            return (Activity) activityStack.lastElement();
        } else {
            return null;
        }
    }

    /**
     * 退出栈中指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activity.finish();
                activityStack.remove(activity);
                System.out.println("remove:" + activity.getClass().getName().toString());
                System.out.println("activityStack.size:" + activityStack.size());
                activity = null;
            }
        }
    }

    /**
     * 关闭指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            // 使用迭代器安全删除
            for (Iterator<Activity> it = activityStack.iterator(); it.hasNext(); ) {
                Activity activity = it.next();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 清除掉除cls以外的其他类 全部退出的时候要使用removeAllActivity()
     *
     * @param cls
     */
    public void removeAllActivityExceptOne(Class<?> cls) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                continue;
            }
            removeActivity(activity);
        }
    }
    /**
     * 清除掉除cls以外的其他类 全部退出的时候要使用removeAllActivity()
     *
     * @param cls
     */
    public void removeAllActivityExceptTwo(Class<?> cls, Class<?> cls1) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls) || activity.getClass().equals(cls1)) {
                continue;
            }
            removeActivity(activity);
        }
    }

    /**
     * 将栈中的activity自顶而下删掉，直到指定的Class
     *
     * @param cls
     */
    public void removeActivityTo(Class<?> cls) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
        }
    }

    /**
     * 计算栈中指定类(cls)对象的个数 add by chenkehui @2013.07.10
     */
    public int activityCount(Class<?> cls) {
        int count = 0;
        int size = activityStack.size();
        Activity activity = null;
        for (int i = 0; i < size; i++) {
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 退出所有activity
     */
    public void removeAllActivity() {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity != null) {
                removeActivity(activity);
            }
        }
        System.gc();
    }

    /**
     * 是否存在
     * @return
     */
    public boolean containsActivity(Class<?> cls){
        int size = activityStack.size();
        for (int i=0; i<size; i++){
            if (activityStack.get(i).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 回退到某个页面
     * @param activity
     */
    public void backToActivity(Class<?> cls){

        int index = -1;
        int size = activityStack.size();
        Activity activity = null;
        for (int i=0; i<size; i++){
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                index = i;
            }
        }

        if(index != -1){
            System.out.println("回退删除");
            for(int i=index+1; i<size; i++){
                activity = activityStack.get(index+1);
                removeActivity(activity);
            }
        }
    }

    /**
     * 获取Activity索引
     * @param activity
     * @return
     */
    public int getActivityIndex(Class<?> cls){
        int size = activityStack.size();
        for (int i=size-1; i>-1; i--){
            if (activityStack.get(i).getClass().equals(cls)) {
                return i;
            }
        }
        return -1;
    }
}
