package udacity.android_nanodegree.popularmovies_stage1;

/**
 * Created by root on 22/2/16.
 */
public class Movie {
    private String originalTitle;
    private String posterPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    private String[] trailerName;
    private String[] trailerPath;

    private String review;

    private long id;

    public Movie(String originalTitle, String posterPath, String overview, String voteAverage, String releaseDate, long id) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public Movie(String originalTitle, String posterPath, String overview, String voteAverage, String releaseDate, long id, String review, String[] trailerName, String[] trailerPath) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.id = id;
        this.review = review;
        this.trailerName = trailerName;
        this.trailerPath = trailerPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String[] getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String[] trailerName) {
        this.trailerName = trailerName;
    }

    public String[] getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String[] trailerPath) {
        this.trailerPath = trailerPath;
    }
}
