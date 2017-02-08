import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
public class FirstRatings
{
    public ArrayList loadMovies(String filename){
        FileResource fr = new FileResource(filename);
        ArrayList<Movie> movies = new ArrayList<Movie>();
       
        for (CSVRecord record : fr.getCSVParser()) {
            Movie curMovie = new Movie(record.get("id"),record.get("title"), record.get("year"), record.get("genre"), record.get("director"), record.get("country"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            movies.add(curMovie);
        }
       
        return movies;
    }
   
    public ArrayList<Rater> loadRaters(String filename){
        FileResource fr = new FileResource(filename);
        ArrayList<Rater> raters = new ArrayList();
        //create list to track rater_id's that have been added to raters
        ArrayList<String> raterIDList = new ArrayList();
       
        for (CSVRecord record : fr.getCSVParser()) {
            String rater_id = record.get("rater_id");
            String movie_id = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
           
            if(raterIDList.contains(rater_id) == true){
                for (int k=0; k<raters.size(); k++){
                    Rater curRater = raters.get(k);
                   
                    if(curRater.getID().equals(rater_id)){
                        curRater.addRating(movie_id, rating);
                        raters.set(k,curRater);
                    }
                   
                }               
            }
           
            else{
                Rater newRater = new EfficientRater(rater_id);
                newRater.addRating(movie_id, rating);
                raters.add(newRater);
                raterIDList.add(rater_id);
            }
           
 
        }
       
        return raters;
    }
   
    public void testLoadRaters(){
        ArrayList<Rater> loadedRaters = loadRaters("data/ratings.csv");
       
        String searchRaterID = "193";
        String searchMovieID = "1798709";
       
        System.out.println("Number of Raters: " + loadedRaters.size());
       
        HashMap<String, Integer> raterCounts = new HashMap();
        HashMap<String, Integer> movingRatingCounts = new HashMap();
       
        for (int k=0; k < loadedRaters.size(); k++){
            Rater curRater = loadedRaters.get(k);
            if (curRater.getID().equals(searchRaterID)){
                System.out.println("Rater " + searchRaterID + " has " + curRater.numRatings() + " Ratings");
            }
            //System.out.println("Rater ID " + curRater.getID() + " Rated "+ curRater.numRatings() + " Movies:");
            for (int i=0; i<curRater.numRatings(); i++){
                String curItem = curRater.getItemsRated().get(i);
                //System.out.println("Movie ID: " + curItem + " Rating: " +  curRater.getRating(curItem));
                if(movingRatingCounts.containsKey(curItem)){
                    movingRatingCounts.put(curItem, movingRatingCounts.get(curItem) + 1);
                }
                else{
                    movingRatingCounts.put(curItem, 1);
                }
            }
           
            String curRaterID = curRater.getID();
            raterCounts.put(curRaterID, curRater.numRatings());
 
        }
        int raterRatingsMax = Collections.max(raterCounts.values());
        ArrayList<String> ratersWithMax = new ArrayList();
       
        for (String rater : raterCounts.keySet()){
            if (raterCounts.get(rater).equals(raterRatingsMax)){
                ratersWithMax.add(rater);
            }
        }
       
        System.out.println("Raters that rated the max number ("+ raterRatingsMax + ") of movies: \n" + ratersWithMax);
        System.out.println("Movie ID " + searchMovieID + " was rated by " + movingRatingCounts.get(searchMovieID) + " Raters");
        System.out.println("number of movies rated: " + movingRatingCounts.size());
    }
   
    
    public void testLoadMovies(){
        ArrayList<Movie> loadedMovies = loadMovies("data/ratedmoviesfull.csv");
       
        System.out.println("Number of movies: " + loadedMovies.size());
       
        int genreCount = 0;
        int longCount = 0;
        HashMap<String, Integer> dirCounts = new HashMap();
        for (int k=0; k < loadedMovies.size(); k++){
            Movie curMovie = loadedMovies.get(k);
            if (curMovie.getGenres().contains("Comedy")){
                genreCount++;
            }
            if (curMovie.getMinutes() > 150){
                longCount++;
            }
           
            String director = curMovie.getDirector();
            if (dirCounts.containsKey(director)){
                dirCounts.put(director, dirCounts.get(director) + 1);
            }
            else {
                dirCounts.put(director, 1);
            }
           
        }
       
        int dirMoviesMax = Collections.max(dirCounts.values());
        ArrayList<String> dirWithMax = new ArrayList();
        for (String dir : dirCounts.keySet()){
            if (dirCounts.get(dir) == dirMoviesMax){
                dirWithMax.add(dir);
            }
        }
       
        System.out.println("Number of comedies: "+ genreCount);
        System.out.println("Number over 150 minutes: "+ longCount);
        System.out.println("Max number of movies by a single director: "+ dirMoviesMax);
        System.out.println("Directors that directed the max number of movies: \n" + dirWithMax);
       
        Movie m = loadedMovies.get(1);
        System.out.println("test");
        System.out.println(m);
        System.out.println(m.toString());
    }
 
}