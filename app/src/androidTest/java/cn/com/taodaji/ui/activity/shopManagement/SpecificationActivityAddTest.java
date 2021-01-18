package cn.com.taodaji.ui.activity.shopManagement;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matchers;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsSpecification;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by yangkuo on 2018-07-30.
 */
public class SpecificationActivityAddTest {
    //    putRelation(R.id.price, "price");
//    putRelation(R.id.level1Unit, "level1Unit");
//    putRelation(R.id.level2Value, "level2Value");
//    putRelation(R.id.level2Unit, "level2Unit");
//    putRelation(R.id.level3Value, "level3Value");
//    putRelation(R.id.level3Unit, "level3Unit");
    public void add(GoodsSpecification spec) {
        if (spec != null) {
            onView(withId(R.id.price)).perform(replaceText(String.valueOf(spec.getPrice())));
            onView(withId(R.id.stock)).perform(replaceText(String.valueOf(spec.getStock())));
            onView(withId(R.id.level1Unit)).perform(click());
            int type = spec.getLevelType();
            switch (type) {
                case 1:
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel1Unit()))), click()));
                    break;
                case 2:
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel2Unit()))), click()));
                    onView(withId(R.id.level2Value)).check(matches(isDisplayed())).perform(replaceText(String.valueOf(spec.getLevel2Value())));
                    onView(withId(R.id.level2Unit)).perform(click());
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel1Unit()))), click()));
                    break;
                case 3:
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel3Unit()))), click()));
                    onView(withId(R.id.level2Value)).check(matches(isDisplayed())).perform(replaceText(String.valueOf(spec.getLevel3Value())));
                    onView(withId(R.id.level2Unit)).perform(click());
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel2Unit()))), click()));
                    onView(withId(R.id.level3Value)).check(matches(isDisplayed())).perform(replaceText(String.valueOf(spec.getLevel3Value())));
                    onView(withId(R.id.level3Unit)).perform(click());
                    onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(Matchers.allOf(withId(R.id.image_name), withText(spec.getLevel1Unit()))), click()));

                    break;
            }

            onView(withId(R.id.bt_add)).perform(click());

        }
    }

}