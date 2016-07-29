package net.zsygfddsd.qujing;

import android.app.Application;

/**
 * Created by mac on 16/5/12.
 */
public class QJapplication extends Application {

    private static final String TAG = QJapplication.class.getSimpleName();
    private static QJapplication qjApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        qjApplication = this;
    }

    public static QJapplication getInstance() {
        return qjApplication;
    }


}
