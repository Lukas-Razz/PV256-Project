package cz.muni.fi.pv256.movio2.uco_410034.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by lukas on 10.12.2017.
 */

public class FavoriteMovieSyncAuthenticatorService extends Service {

    private FavoriteMovieSyncAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new FavoriteMovieSyncAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}