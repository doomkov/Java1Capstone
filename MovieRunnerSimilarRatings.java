import java.util.*;

public class MovieRunnerSimilarRatings
{
    public void printAverageRatings(){
        int minimumRaters = 35;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        int yrFilVal = 1900;
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        
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
        System.out.println("Number of raters: "+ RaterDatabase.size());
        System.out.println("Number of movies with min raters: "+ avgRatings.size());
        System.out.println("Number of movies after " + yrFilVal + " with minimum of " + minimumRaters + " raters: "+ afterYrCount);
        
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        int minimumRaters = 8;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        int year = 1990;
        String genre = "Drama";
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        
        AllFilters yrGenFilter = new AllFilters();
        yrGenFilter.addFilter(new YearAfterFilter(year));
        yrGenFilter.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> avgRatings = sr.getAverageRatingsByFilter(minimumRaters, yrGenFilter);
        Collections.sort(avgRatings);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        for (Rating rating : avgRatings){
            System.out.println(rating.getValue() + "  " +MovieDatabase.getYear((rating.getItem())) + "  " + MovieDatabase.getTitle((rating.getItem())));
            System.out.println("       " + MovieDatabase.getGenres((rating.getItem())));
        }        
    }
    
    public void printSimilarRatings(){
        String raterID = "71";
        int minimumRaters = 5;
        int numSimilarRaters = 20;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        
        ArrayList<Rating> recommendations = sr.getSimilarRatings(raterID, numSimilarRaters, minimumRaters);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + recommendations.size() + " movies");
        for (Rating rating : recommendations){
            System.out.println(MovieDatabase.getTitle((rating.getItem()))+ " | Sim Rating: " + rating.getValue());
            //System.out.println("       " + MovieDatabase.getGenres((rating.getItem())));
        }    
    }
    
    public void printSimilarRatingsByGenre(){
        String raterID = "964";
        int minimumRaters = 5;
        int numSimilarRaters = 20;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        String genre = "Mystery";
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        Filter genreFilter = new GenreFilter(genre);
        
        ArrayList<Rating> recommendations = sr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimumRaters, genreFilter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + recommendations.size() + " movies");
        for (Rating rating : recommendations){
            System.out.println(MovieDatabase.getTitle((rating.getItem()))+ " | Sim Rating: " + rating.getValue());
            System.out.println("     " + MovieDatabase.getGenres((rating.getItem())));
        }    
    }
    
    public void printSimilarRatingsByDirector (){
        String raterID = "120";
        int minimumRaters = 2;
        int numSimilarRaters = 10;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        Filter dirFilter = new DirectorsFilter(directors);
        
        ArrayList<Rating> recommendations = sr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimumRaters, dirFilter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + recommendations.size() + " movies");
        for (Rating rating : recommendations){
            System.out.println(MovieDatabase.getTitle((rating.getItem()))+ " | Sim Rating: " + rating.getValue());
            System.out.println("       " + MovieDatabase.getDirector((rating.getItem())));
        }    
    }
    
    public void printSimilarRatingsByGenreAndMinutes (){
        String raterID = "168";
        int minimumRaters = 3;
        int numSimilarRaters = 10;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        String genre = "Drama";
        int minMinutes = 80;
        int maxMinutes = 160;
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        
        AllFilters genMinFilter = new AllFilters();
        genMinFilter.addFilter(new GenreFilter(genre));
        genMinFilter.addFilter(new MinutesFilter(minMinutes,maxMinutes));
        
        ArrayList<Rating> recommendations = sr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimumRaters, genMinFilter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + recommendations.size() + " movies");
        for (Rating rating : recommendations){
            System.out.println(MovieDatabase.getTitle((rating.getItem()))+ " | Minutes: " + MovieDatabase.getMinutes((rating.getItem())) + " | Sim Rating: " + rating.getValue());
            System.out.println("     " + MovieDatabase.getGenres((rating.getItem())));
        }    
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes (){
        String raterID = "314";
        int minimumRaters = 5;
        int numSimilarRaters = 10;
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        int year = 1975;
        int minMinutes = 70;
        int maxMinutes = 200;
        
        FourthRatings sr = new FourthRatings();
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingsfile);
        
        AllFilters yrMinFilter = new AllFilters();
        yrMinFilter.addFilter(new YearAfterFilter(year));
        yrMinFilter.addFilter(new MinutesFilter(minMinutes,maxMinutes));
        
        ArrayList<Rating> recommendations = sr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimumRaters, yrMinFilter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + recommendations.size() + " movies");
        for (Rating rating : recommendations){
            System.out.println(MovieDatabase.getTitle((rating.getItem()))+ " | Year:  " +MovieDatabase.getYear((rating.getItem())) + " | Minutes: " + MovieDatabase.getMinutes((rating.getItem())) + " | Sim Rating: " + rating.getValue());
            
        }    
    }
}






































