package cz.muni.fi.pv256.movio2.uco_410034.Db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import cz.muni.fi.pv256.movio2.uco_410034.Db.Dao.MovieDao;
import cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie;

/**
 * Created by lukas on 06.12.2017.
 */

@Database(entities = {Movie.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase{

    private static MovieDatabase INSTANCE;

    public static final String DATABASE_NAME = "move-database";

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
