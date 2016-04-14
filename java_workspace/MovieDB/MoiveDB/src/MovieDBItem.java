
/******************************************************************************
 * This class stores MovieDBItem; genre&title.
 * This class is usually called by method SEARCH and PRINT of MovieDB. 
 */
public class MovieDBItem {

    private final String genre;
    private final String title;

    public MovieDBItem(String genre, String title) {
        if (genre == null) throw new NullPointerException("genre");
        if (title == null) throw new NullPointerException("title");

        this.genre = genre;
        this.title = title;
    }

    public String getGenre() {//return name of genre
        return genre;
    }

    public String getTitle() {//return name of title
        return title;
    }
}