package cz.muni.fi.pv256.movio2.uco_410034.Sync;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 14.12.2017.
 */

public class FavoriteMovieSyncBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FavoriteMovieSyncAdapter.getSyncAccount(context);
    }
}
