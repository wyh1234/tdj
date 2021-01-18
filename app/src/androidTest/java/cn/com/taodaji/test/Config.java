package cn.com.taodaji.test;

/**
 * <p>
 * UiDevice
 * ：用于在目标应用运行的设备上访问和执行操作。您可以调用其方法来访问设备属性，如当前屏幕方向或显示尺寸，按“返回”、“主屏幕”或“菜单”按钮等。
 * UiCollection
 * ：枚举容器的 UI 元素以便计算子元素个数，或者通过可见的文本或内容描述属性来指代子元素。
 * UiObject
 * ：表示设备上可见的 UI 元素。
 * UiScrollable
 * ：为在可滚动 UI 容器中搜索项目提供支持。
 * UiSelector
 * ：表示在设备上查询一个或多个目标 UI 元素。
 * Configurator
 * ：允许您设置运行 UI Automator 测试所需的关键参数。
 * <p>
 * <p>
 * <p>
 * allOf	所有都匹配
 * anyOf	任意一个匹配
 * not	不是
 * equalTo	对象等于
 * is	是
 * hasToString	包含toString
 * instanceOf,isCompatibleType	类的类型是否匹配
 * notNullValue,nullValue	测试null
 * sameInstance	相同实例
 * hasEntry,hasKey,hasValue	测试Map中的Entry、Key、Value
 * hasItem,hasItems	测试集合(collection)中包含元素
 * hasItemInArray	测试数组中包含元素
 * closeTo	测试浮点数是否接近指定值
 * greaterThan,greaterThanOrEqualTo,lessThan,lessThanOrEqualTo	数据对比
 * equalToIgnoringCase	忽略大小写字符串对比
 * equalToIgnoringWhiteSpace	忽略空格字符串对比
 * containsString,endsWith,startsWith,isEmptyString,isEmptyOrNullString	字符串匹配
 *
 * espresso
 * onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));
 *
 * onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Effective Java ")), click()));
 *
 *
 * //系统toolbar的三个点
 *  openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
 *
 *  //自定义的pop
 *   openContextualActionModeOverflowMenu();
 */
public interface Config {
    String purchaserUserName = "13986397100";//采购商账号
    String purchaserPassword = "111111";//采购商密码
    String supplierUserName = "18271251272";//供应商账号
    String supplierPassword = "111111";//供应商密码
    String smscode = "3061631";//验证码

}
