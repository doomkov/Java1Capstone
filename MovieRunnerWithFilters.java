import java.util.*;
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerWithFilters {
    public void printAverageRatings(){
        int minimumRaters = 35;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        int yrFilVal = 1900;
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        
        Filter yrFilter = new YearAfterFilter(yrFilVal);
        
        int afterYrCount = 0;
        ArrayList<String> moviesFilByYear = MovieDatabase.filterBy(yrFilter);
        ArrayList<Rating> avgRatings = sr.getAverageRatings(minimumRaters);
        Collections.sort(avgRatings);
        System.out.println("Movies with a minimum of "+ minimumRaters + " raters:");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " + MovieDatabase.getTitle((rating.getItem())));
            if (moviesFilByYear.contains(rating.getItem())){
                afterYrCount++;
                
            }
        }
        
        System.out.println("-------------------------------------------------------------");
        System.out.println("Number of movies rated: "+ MovieDatabase.size());
        System.out.println("Number of movies after " + yrFilVal + ": "+ MovieDatabase.filterBy(yrFilter).size());
        System.out.println("Number of raters: "+ sr.getRaterSize());
        System.out.println("Number of movies with min raters: "+ avgRatings.size());
        System.out.println("Number of movies after " + yrFilVal + " with minimum of " + minimumRaters + " raters: "+ afterYrCount);
        
    }
    
    public void printAverageRatingsByYear(){
        int minimumRaters = 20;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        int yrFilVal = 2000;
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        Filter yrFilter = new YearAfterFilter(yrFilVal);
        
        int afterYrCount = 0;
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, yrFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " + MovieDatabase.getTitle((rating.getItem())));
        }        
    }
    
    public void printAverageRatingsByGenre(){
        int minimumRaters = 20;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        String genreFilVal = "Comedy";
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        Filter genreFilter = new GenreFilter(genreFilVal);
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, genreFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("     " + MovieDatabase.getGenres((rating.getItem())));
        }        
    }
    
    public void printAverageRatingsByMinutes(){
        int minimumRaters = 5;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        int minMinutes = 105;
        int maxMinutes = 135;
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        Filter minFilter = new MinutesFilter(minMinutes, maxMinutes);
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, minFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("       Time: " + MovieDatabase.getMinutes((rating.getItem())));
        }        
    }
    
    public void printAverageRatingsByDirectors(){
        int minimumRaters = 4;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        Filter dirFilter = new DirectorsFilter(directors);
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, dirFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("       " + MovieDatabase.getDirector((rating.getItem())));
        }        
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        int minimumRaters = 8;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        int year = 1990;
        String genre = "Drama";
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);
        
        AllFilters yrGenFilter = new AllFilters();
        yrGenFilter.addFilter(new YearAfterFilter(year));
        yrGenFilter.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, yrGenFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " +MovieDatabase.getYear((rating.getItem())) + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("       " + MovieDatabase.getGenres((rating.getItem())));
        }        
    }
    
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        int minimumRaters = 3;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "data/ratings.csv";
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        int minMinutes = 90;
        int maxMinutes = 180;
        
        ThirdRatings sr = new ThirdRatings(ratingsfile);	
        MovieDatabase.initialize(moviefile);
        
        AllFilters dirMinFilter = new AllFilters();
        dirMinFilter.addFilter(new DirectorsFilter(directors));
        dirMinFilter.addFilter(new MinutesFilter(minMinutes,maxMinutes ));
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, dirMinFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + sr.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + " Time: " +MovieDatabase.getMinutes((rating.getItem())) + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("       " + MovieDatabase.getDirector((rating.getItem())));
        }        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
