package com.zsygfddsd.spacestation.base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mac on 16/5/12.
 */
public class BaseApplication extends Application {

    private List<Activity> allActivity = new LinkedList<>();
    
    public void exit() {
        for (Activity activity : allActivity) {
            activity.finish();
        }
        System.exit(0);
    }

    public void addActivity(Activity activity) {
        allActivity.add(activity);
    }

    public void removeActivity(Activity activity) {
        allActivity.remove(activity);
    }

    public List<Activity> getAllActivity() {
        return allActivity;
    }


}
