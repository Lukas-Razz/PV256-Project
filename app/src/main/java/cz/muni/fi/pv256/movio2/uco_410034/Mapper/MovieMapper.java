package cz.muni.fi.pv256.movio2.uco_410034.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;


/**
 * Created by lukas on 06.12.2017.
 */

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie dbMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie movie);
    Movie[] dbMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie[] movie);
    List<Movie> dbMovieToMovie(List<cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie> movie);

    @Mapping(target = "id", ignore = true)
    cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie movieToDbMovie(Movie movie);
    cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie[] movieToDbMovie(Movie[] movie);
    List<cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie> movieToDbMovie(List<Movie> movie);
}
