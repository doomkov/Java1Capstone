
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile){
        FirstRatings fr = new FirstRatings();
        
        myRaters = fr.loadRaters(ratingsfile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for (String id : movies){
            
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0){
                Rating curRating = new Rating(id, avg);
                avgRatingList.add(curRating);
            }
        }
        
        return avgRatingList;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> avgRatingList = new ArrayList();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        for (String id : movies){
            
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0){
                Rating curRating = new Rating(id, avg);
                avgRatingList.add(curRating);
            }
        }
        
        return avgRatingList;
    }
}
























