
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile){
        FirstRatings fr = new FirstRatings();
        
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters){
        
        double average = -1;
        int count = 0;
        double runningTot = 0;
        for (Rater rater : myRaters){
            double rating = rater.getRating(id);
            if (rating == -1){}
            else {
                count++;
                runningTot = runningTot + rating;
            }
        }
        
        if(count < minimalRaters){return 0.0;}
        else{return runningTot/count;}
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> avgRatingList = new ArrayList();
        
        for (Movie movie : myMovies){
            String id = movie.getID();
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0){
                Rating curRating = new Rating(id, avg);
                avgRatingList.add(curRating);
            }
        }
        
        return avgRatingList;
    }
    
    public String getTitle(String id){
        String title = "";
        for (Movie movie : myMovies){
            if(movie.getID().equals(id)){
                title = movie.getTitle();
                break;
            }
        }
        if(title == ""){return "NO SUCH ID";}
        else {return title;}
        
    }
    
    public String getID(String title){
        String id = "";
        for (Movie movie : myMovies){
            if(movie.getTitle().equals(title)){
                id = movie.getID();
                break;
            }
        }
        if(id == ""){return "NO SUCH TITLE";}
        else {return id;}
        
    }
    
    
    public void testAvgByID(String id, int min){
        double avg = getAverageByID(id,min);
        System.out.println(id + ": " + avg); 
        ArrayList<Rating> testARL = getAverageRatings(min);
        System.out.println(testARL); 
    }
}
























