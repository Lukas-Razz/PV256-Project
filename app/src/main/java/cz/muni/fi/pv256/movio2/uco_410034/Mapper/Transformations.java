package cz.muni.fi.pv256.movio2.uco_410034.Mapper;

import android.util.Log;

import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lukas on 14.12.2017.
 */

@Named("Transformations")
public class Transformations {

    public static final String TAG = "Transformations";

    @Named("apiToModelDate")
    long apiToModelDate(String release_date) {
        try {
            return (new SimpleDateFormat("yyyy-mm-dd").parse(release_date).getTime());
        } catch (ParseException e) {
            Log.e(TAG, "apiToModelDateTransformation cannot parse: " + release_date);
            return 0;
        }
    }

    @Named("apiToModelPopularity")
    float apiToModelPopularity(Double vote_average) {
        return (float)((vote_average) / 2);
    }
}
