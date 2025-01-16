import java.util.*;

public class Movie {
    int id;
    String movieName;
    int duration;
    
    public Movie (int id, String movieName, int duration) {
        this.movieName = movieName;
        this.id = id;
        this.duration = duration;
    }
}

public class MovieController {
    Map<City, List<Movies>> cityVsMovies;
    List<Movies> allMovies;

    public MovieController() {
        cityVsMovies = new HashMap<>();
        allMovies = new ArrayList<>();
    }

    public void addMovie(City city, Movie movie) {
        cityVsMovies.put(city, cityVsMovies.getOrDefault(city, new ArrayList<Movie>()).add(movie));
        allMovies.add(movie);
    }

    public Movie getMovieByName (Movie movie) {
        for (Movie m : allMovies) {
            if (m.equals(movies)) {
                return m;
            }
        }
        return null;
    }

    public List<Movie> getMovieByCity(City city) {
        return cityVsMovies.get(city);
    }
}

public class Theatre {
    int id;
    String address;
    City city;
    List<Screen> screenList;
    List<Show> showList;

}

public class Show {
    int id;
    Movie movie;
    Screen screen;
    int startTime;
    List<Seat> bookedSeats;
}

public class TheatreController {
    Map<City, List<Theatre>> cityVsTheatre;
    List<Theatre> allTheatres;

    public void addTheatre (City city, Theatre theatre) {
        cityVsTheatre.put(city, cityVsTheatre.getOrDefault(city, new ArrayList<Theatre>()).add(theatre));
    }

    public Map<Theatre, List<Show>> getAllShows(City city, Movie movie) {
        Map<Theatre, List<Show>> theatreForShow;
        List<Theatre> theatreList = cityVsTheatre.get(city);
        for (Theatre theatre : theatreList) {
            List<Show> givenMovieShow = new ArrayList<>();
            List<Show> theatreShows = theatre.getShow();
            for (Show show : theatreShows) {
                if (show.getMovieId()==movie.getId()) {
                    givenMovieShow.add(show);
                }
            }
            if (!givenMovieShow.isEmpty()) {
                theatreForShow.add(theatre, givenMovieShow);
            }
        }
        return theatreForShow;
    }
}

public class Screen {
    int id;
    int width;
    List<Seat> seats;
}

public class Seat {
    int id;
    Category category;
    int price;
    int row;
    int col;
}

public enum Category {
    SILVER,
    GOLD,
    PLATINUM;
}

public enum City {
    DELHI,
    BANGALORE;
}

public class Booking {
    Show show;
    List<Seat> seats;
    Payment payment;
}

public class Payment {
    int id;
    boolean success;
}

public class BookMyShow {
    MovieController movieController;
    TheatreController theatreController;
    public BookMyShow() {
        this.movieController = new MovieController();
        this.theatreController = new TheatreController();
    }   
    public void createBooking(City userCity, Movie userMovie) {
        List<Movie> movies = movieController.getMovieByCity(userCity);
        Movie interestedMovie = null;
        for (Movie movie: movies) {
            if (movie.getId() == userMovie.getId()) {
                interestedMovie = movie;
            }
        }
        Map<Theatre, List<Show>> showsTheatreWise = theatreController.getAllShows(interestedMovie, userCity);
        //4. select the particular show user is interested in
        Map.Entry<Theatre,List<Show>> entry = showsTheatreWise.entrySet().iterator().next();
        List<Show> runningShows = entry.getValue();
        Show interestedShow = runningShows.get(0);
        int seatNumber = 30;
        List<Integer> bookedSeats = interestedShow.getBookedSeatIds();
        if(!bookedSeats.contains(seatNumber)){
            bookedSeats.add(seatNumber);
            //startPayment
            Booking booking = new Booking();
            List<Seat> myBookedSeats = new ArrayList<>();
            for(Seat screenSeat : interestedShow.getScreen().getSeats()) {
                if(screenSeat.getSeatId() == seatNumber) {
                    myBookedSeats.add(screenSeat);
                }
            }
            booking.setBookedSeats(myBookedSeats);
            booking.setShow(interestedShow);
        } else {
            //throw exception
            System.out.println("seat already booked, try again");
            return;
        }

        System.out.println("BOOKING SUCCESSFUL");

    }
    private void initialize() {
        //create movies
        createMovies();
        //create theater with screens, seats and shows
        createTheatre();
    }

    //creating 2 theatre
    private void createTheatre() {
        Movie avengerMovie = movieController.getMovieByName("AVENGERS");
        Movie baahubali = movieController.getMovieByName("BAAHUBALI");
        Theatre inoxTheatre = new Theatre();
        inoxTheatre.setTheatreId(1);
        inoxTheatre.setScreen(createScreen());
        inoxTheatre.setCity(City.Bangalore);
        List<Show> inoxShows = new ArrayList<>();
        Show inoxMorningShow = createShows(1, inoxTheatre.getScreen().get(0), avengerMovie, 8);
        Show inoxEveningShow = createShows(2, inoxTheatre.getScreen().get(0), baahubali, 16);
        inoxShows.add(inoxMorningShow);
        inoxShows.add(inoxEveningShow);
        inoxTheatre.setShows(inoxShows);

        Theatre pvrTheatre = new Theatre();
        pvrTheatre.setTheatreId(2);
        pvrTheatre.setScreen(createScreen());
        pvrTheatre.setCity(City.Delhi);
        List<Show> pvrShows = new ArrayList<>();
        Show pvrMorningShow = createShows(3, pvrTheatre.getScreen().get(0), avengerMovie, 13);
        Show pvrEveningShow = createShows(4, pvrTheatre.getScreen().get(0), baahubali, 20);
        pvrShows.add(pvrMorningShow);
        pvrShows.add(pvrEveningShow);
        pvrTheatre.setShows(pvrShows);

        theatreController.addTheatre(inoxTheatre, City.Bangalore);
        theatreController.addTheatre(pvrTheatre, City.Delhi);

    }

    private List<Screen> createScreen() {
        List<Screen> screens = new ArrayList<>();
        Screen screen1 = new Screen();
        screen1.setScreenId(1);
        screen1.setSeats(createSeats());
        screens.add(screen1);

        return screens;
    }

    private Show createShows(int showId, Screen screen, Movie movie, int showStartTime) {
        Show show = new Show();
        show.setShowId(showId);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setShowStartTime(showStartTime); //24 hrs time ex: 14 means 2pm and 8 means 8AM
        return show;
    }

    //creating 100 seats
    private List<Seat> createSeats() {

        //creating 100 seats for testing purpose, this can be generalised
        List<Seat> seats = new ArrayList<>();

        //1 to 40 : SILVER
        for (int i = 0; i < 40; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.SILVER);
            seats.add(seat);
        }

        //41 to 70 : SILVER
        for (int i = 40; i < 70; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.GOLD);
            seats.add(seat);
        }

        //1 to 40 : SILVER
        for (int i = 70; i < 100; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.PLATINUM);
            seats.add(seat);
        }

        return seats;
    }

    private void createMovies() {

        //create Movies1
        Movie avengers = new Movie();
        avengers.setMovieId(1);
        avengers.setMovieName("AVENGERS");
        avengers.setMovieDuration(128);

        //create Movies2
        Movie baahubali = new Movie();
        baahubali.setMovieId(2);
        baahubali.setMovieName("BAAHUBALI");
        baahubali.setMovieDuration(180);


        //add movies against the cities
        movieController.addMovie(avengers, City.Bangalore);
        movieController.addMovie(avengers, City.Delhi);
        movieController.addMovie(baahubali, City.Bangalore);
        movieController.addMovie(baahubali, City.Delhi);
    }

}