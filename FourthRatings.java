
import java.util.*;

public class FourthRatings {

    private double getAverageByID(String id, int minimalRaters){
        double average = -1;
        int count = 0;
        double runningTot = 0;

        ArrayList<Rater> myRaters = RaterDatabase.getRaters();

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

    private double dotProduct(Rater me, Rater r){
        double simRating = 0;
        ArrayList<String> itemsRatedByMe = me.getItemsRated();

        for (String movieID : itemsRatedByMe){
            if (r.hasRating(movieID)){
                double rRating = r.getRating(movieID) - 5;
                double myRating = me.getRating(movieID) - 5;

                simRating = simRating + (rRating * myRating);
            }
        }

        return simRating;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> raterSimList = new ArrayList();
        ArrayList<Rater> allRaters =  RaterDatabase.getRaters();
        Rater me = RaterDatabase.getRater(id);

        for (Rater rater : allRaters){
            String raterID = rater.getID();

            if (raterID.equals(id) == false){
                double simRatingVal = dotProduct(me, rater);
                Rating simRating = new Rating(raterID, simRatingVal);
                raterSimList.add(simRating);    
            }

        }

        Collections.sort(raterSimList, Collections.reverseOrder());

        return raterSimList;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> movieSimRatings = new ArrayList();
        ArrayList<Rating> raterSimiliarities = getSimilarities(id);
        ArrayList<String> moviesIDs = new ArrayList();
        HashMap<String, Double> similiarityMap = new HashMap();
        int mapSize = getSimilarities(id).size();
        int minIndex = Math.min(mapSize, numSimilarRaters);

        
        for (Rating simil : getSimilarities(id).subList(0,minIndex)){
            if(simil.getValue() > 0){
                similiarityMap.put(simil.getItem(), simil.getValue());
            }
        }
        

        for (String movieID : MovieDatabase.filterBy(filterCriteria)){
            int count = 0;
            double runningTot = 0;

            
            for (Rater curRater : RaterDatabase.getRaters()){
                double rating = -1;

                if (similiarityMap.containsKey(curRater.getID()) && curRater.hasRating(movieID)){
                    rating = curRater.getRating(movieID) * similiarityMap.get(curRater.getID());
                    
                }

                if (rating == -1){}
                else {
                    count++;
                    runningTot = runningTot + rating;
                }
            }

            if(count < minimalRaters || runningTot == 0){}
            else{
                movieSimRatings.add(new Rating(movieID, runningTot/count));

            }

        }
        Collections.sort(movieSimRatings, Collections.reverseOrder());
        return movieSimRatings;
    }
}
















