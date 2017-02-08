import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class MovieRunnerAverage {
    
    public void printAverageRatings(){
        int minimumRaters = 12;
        String moviefile = "data/ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        SecondRatings sr = new SecondRatings(moviefile, ratingsfile);
        
        System.out.println("Number of movies rated: "+ sr.getMovieSize());
        System.out.println("Number of raters: "+ sr.getRaterSize());
        
        String lowest = "";
        double lowRating = 11.0;
        for (Rating rating : sr.getAverageRatings(minimumRaters)){
            System.out.println(rating.getValue() + "  " + sr.getTitle(rating.getItem()));
            if (rating.getValue() <  lowRating){
                lowest = sr.getTitle(rating.getItem());
                lowRating = rating.getValue();
            }
        }
        
        System.out.println(sr.getID("Her"));
        System.out.println("Lowest rated is '" + lowest + "' with rating " + lowRating);
    }
    
    public void getAverageRatingOneMovie(){
        int minimumRaters = 1;
        String title = "Vacation";
        String moviefile = "data/ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        SecondRatings sr = new SecondRatings(moviefile, ratingsfile);
        
        String id = sr.getID(title);
        for (Rating rating : sr.getAverageRatings(minimumRaters)){
            if (rating.getItem().equals(id)){
                System.out.println("Average rating for '" + title + "' is: " + rating.getValue());
            }
        }        
    }

}
