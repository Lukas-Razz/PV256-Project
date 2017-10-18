package cz.muni.fi.pv256.movio2.uco_410034;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lukas on 01.10.2017.
 */

public class App extends Application {

    static {
        System.loadLibrary("apikeys");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            initStrictMode();
        }
    }

    public native String getApiKey();

    private void initStrictMode() {
        StrictMode.ThreadPolicy.Builder tpb = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            tpb.penaltyFlashScreen();
        }
        StrictMode.setThreadPolicy(tpb.build());

        StrictMode.VmPolicy.Builder vmpb = new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            vmpb.detectLeakedClosableObjects();
        }
        StrictMode.setVmPolicy(vmpb.build());
    }
}
