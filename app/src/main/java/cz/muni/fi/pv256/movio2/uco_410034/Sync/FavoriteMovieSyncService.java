package cz.muni.fi.pv256.movio2.uco_410034.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by lukas on 10.12.2017.
 */
public class FavoriteMovieSyncService extends Service {

    private static final Object LOCK = new Object();
    private static FavoriteMovieSyncAdapter sFavoriteMovieSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (LOCK) {
            if (sFavoriteMovieSyncAdapter == null) {
                sFavoriteMovieSyncAdapter = new FavoriteMovieSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sFavoriteMovieSyncAdapter.getSyncAdapterBinder();
    }
}
