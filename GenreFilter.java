
public class GenreFilter implements Filter{
    private String genre;
    
    public GenreFilter(String inputGenre){
        genre = inputGenre;
    }
    
    @Override
    public boolean satisfies(String id){
        return MovieDatabase.getGenres(id).contains(genre);
    }
}
