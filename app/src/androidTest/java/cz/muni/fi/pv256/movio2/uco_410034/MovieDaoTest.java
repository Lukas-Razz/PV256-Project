package cz.muni.fi.pv256.movio2.uco_410034;

import android.arch.persistence.room.Room;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Db.MovieDatabase;

/**
 * Created by lukas on 07.12.2017.
 */

public class MovieDaoTest {

    private MovieDatabase mDatabase;

    @Before
    public void before() {
        mDatabase = Room.inMemoryDatabaseBuilder(new MockContext(), MovieDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void after() {
        mDatabase.close();
    }

    @Test
    public void getAll_MovieList_ThreeMovies() {
        Movie m1 = new Movie();
        m1.setId(1);
        m1.setPopularity(1.5f);
        m1.setBackdrop("backdrop");
        m1.setCoverPath("coverPath");
        m1.setDescription("blabla");
        m1.setReleaseDate(123456789);
        m1.setTitle("M1");
        Movie m2 = new Movie();
        m2.setId(1);
        m2.setPopularity(1.5f);
        m2.setBackdrop("backdrop");
        m2.setCoverPath("coverPath");
        m2.setDescription("blabla");
        m2.setReleaseDate(123456789);
        m2.setTitle("M2");
        Movie m3 = new Movie();
        m3.setId(1);
        m3.setPopularity(1.5f);
        m3.setBackdrop("backdrop");
        m3.setCoverPath("coverPath");
        m3.setDescription("blabla");
        m3.setReleaseDate(123456789);
        m3.setTitle("M3");
        mDatabase.movieDao().insert(m1);
        mDatabase.movieDao().insert(m2);
        mDatabase.movieDao().insert(m3);

        List<Movie> movies = mDatabase.movieDao().getAll();

        Assert.assertTrue(movies.size() == 3);
    }

    @Test
    public void insert_MovieInserted_ValidMovie() {
        Movie m1 = new Movie();
        m1.setId(1);
        m1.setPopularity(1.5f);
        m1.setBackdrop("backdrop");
        m1.setCoverPath("coverPath");
        m1.setDescription("blabla");
        m1.setReleaseDate(123456789);
        m1.setTitle("M1");

        mDatabase.movieDao().insert(m1);

        List<Movie> movies = mDatabase.movieDao().getAll();

        Assert.assertTrue(movies.size() == 1);
    }

    @Test
    public void delete_MovieDeleted_ValidMovie() {
        Movie m1 = new Movie();
        m1.setId(1);
        m1.setPopularity(1.5f);
        m1.setBackdrop("backdrop");
        m1.setCoverPath("coverPath");
        m1.setDescription("blabla");
        m1.setReleaseDate(123456789);
        m1.setTitle("M1");

        mDatabase.movieDao().insert(m1);
        mDatabase.movieDao().delete(m1);

        List<Movie> movies = mDatabase.movieDao().getAll();

        Assert.assertTrue(movies.size() == 0);
    }
}
