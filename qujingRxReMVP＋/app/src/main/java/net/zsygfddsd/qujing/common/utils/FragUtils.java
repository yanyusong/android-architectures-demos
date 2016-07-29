package net.zsygfddsd.qujing.common.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 16/3/5.
 */
public class FragUtils {

    private FragmentManager fm;
    private String currentFragTag = "-1";
    private int containerViewId;
    private List<String> fragTags ;
    private IFragmentInitMethods fragInitMethods;

    public interface IFragmentInitMethods {
        Fragment initFrag(String fragTag);
    }

    public FragUtils(FragmentManager fm, int containerViewId, String[] fragTags, IFragmentInitMethods fragInitMethods) {
        this.fm = fm;
        this.containerViewId = containerViewId;
        this.fragTags = new ArrayList<>(Arrays.asList(fragTags));
        this.fragTags.add(new String("-1"));
        this.fragInitMethods = fragInitMethods;
    }

    public void showFragment(String fragtag) {
        String prefragtag = currentFragTag;
        if (fragTags.size() < 1) {
            Log.e("FragUtils", "Error:请至少添加一个fragment");
            return;
        }
        if (!fragTags.contains(prefragtag)) {
            Log.e("FragUtils", "Error:fragTags中不包含" + prefragtag);
            return;
        }
        if (!fragTags.contains(fragtag)) {
            Log.e("FragUtils", "Error:fragTags中不包含" + fragtag);
            return;
        }
        if (prefragtag.equals("-1")) {
            //没有prefragment，初次进来显示的
            fm.beginTransaction()
                    .replace(containerViewId, fragInitMethods.initFrag(fragtag), fragtag).commit();
        } else {
            if (!prefragtag.equals(fragtag)) {
                // 查找fragment栈，看是否该fragment已存在，存在show,不存在则新建
                Fragment tempFragment = fm.findFragmentByTag(
                        fragtag);
                Fragment preFragment = fm.findFragmentByTag(
                        prefragtag);
                if (preFragment == null) {
                    return;
                }
                if (tempFragment == null) {
                    for (int i = 0; i < fragTags.size(); i++) {
                        if (fragTags.get(i).equals(fragtag)) {
                            fm.beginTransaction()
                                    .hide(preFragment)
                                    .add(containerViewId, fragInitMethods.initFrag(fragtag),
                                            fragtag).commit();
                            //此处必须break不能return，因为下边还有currentFragTag = fragtag;要执行
                            break;
                        }
                    }

                } else {
                    fm.beginTransaction()
                            // .setCustomAnimations(R.anim.from_right_slide_in, R.anim.from_left_slide_out)
                            .hide(preFragment)
                            .show(tempFragment).commit();
                }

            }
        }
        currentFragTag = fragtag;
    }

    public Fragment getCurrentFragment() {
        return fm.findFragmentByTag(currentFragTag);
    }

    public String getCurrentFragTag() {
        return currentFragTag;
    }
}
