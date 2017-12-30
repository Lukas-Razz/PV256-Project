package cz.muni.fi.pv256.movio2.uco_410034.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;


/**
 * Created by lukas on 06.12.2017.
 */

@Mapper( uses = Transformations.class )
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    //DB -> Model
    Movie dbMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie movie);
    Movie[] dbMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie[] movie);
    List<Movie> dbMovieToMovie(List<cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie> movie);

    //Model -> DB
    cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie movieToDbMovie(Movie movie);
    cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie[] movieToDbMovie(Movie[] movie);
    List<cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie> movieToDbMovie(List<Movie> movie);

    //API -> Model
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "coverPath", source = "poster_path"),
            @Mapping(target = "backdrop", source = "backdrop_path"),
            @Mapping(target = "description", source = "overview"),
            @Mapping(target = "releaseDate", source = "release_date", qualifiedByName = {"Transformations", "apiToModelDate"}),
            @Mapping(target = "popularity",  source = "vote_average", qualifiedByName = {"Transformations", "apiToModelPopularity"})
    })
    Movie apiMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Api.Model.Movie movie);
    Movie[] apiMovieToMovie(cz.muni.fi.pv256.movio2.uco_410034.Api.Model.Movie[] movie);
    List<Movie> apiMovieToMovie(List<cz.muni.fi.pv256.movio2.uco_410034.Api.Model.Movie> movie);
}
