package cz.muni.fi.pv256.movio2.uco_410034.Db.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie;

/**
 * Created by lukas on 06.12.2017.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movie);

    @Delete
    void delete(Movie... movie);
}
