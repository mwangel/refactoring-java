import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

public class RentalInfo {
  public final int MIN_DAYS_FOR_EXTRA_FREQUENT_BONUS = 3;

  /**
   * Get a text report of the customer's rentals.
   * @param customer Create the report for this Customer.
   * @return A multi-line string value with \n for line separator, as per spec...
   */
  public String statement(Customer customer) {
    BigDecimal totalAmount = BigDecimal.ZERO;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

    for (MovieRental rentalInfo : customer.getRentals()) {
      Movie movie = getMovie( rentalInfo.getMovieId() );
      if( movie == null ) {
        // Log this as an error, maybe add it to the report, then continue.
        continue;
      }

      // Determine amount for each movie.
      BigDecimal price = calculatePrice( movie, rentalInfo );
      totalAmount = totalAmount.add( price );

      // Add frequent bonus points. Consider putting this in a "isEligbleForExtraBonus(MovieRental info)" method or something.
      frequentEnterPoints++;
      if( (movie.getCode() == Movie.Code.NEW) && (rentalInfo.getDays() >= MIN_DAYS_FOR_EXTRA_FREQUENT_BONUS)) {
        frequentEnterPoints++;
      }

      // Report numbers for this rental.
      result.append( "\t" ).append( movie.getTitle() ).append( "\t" ).append( formatAmount(price) ).append( "\n" );
    }

    // add footer lines
    result.append( "Amount owed is " ).append( formatAmount(totalAmount) ).append( "\n" );
    result.append( "You earned " ).append( frequentEnterPoints ).append( " frequent points\n" );

    return result.toString();
  }

  // If you really do this you could have failed already because the report might not reflect the actual price...
  private String formatAmount( BigDecimal amount ) {
    final DecimalFormat formatter = new DecimalFormat("#.0");
    formatter.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance(Locale.US) );
    return formatter.format( amount );
  }

  /**
   * This method is just a stub replacement for DB access so
   * there is no point to optimizing or synchronizing it.
   * Might as well be declared a constant in this project.
   * @return All the movies.
   */
  private HashMap<String, Movie> getMovies() {
    HashMap<String, Movie> movies = new HashMap<>();
    movies.put( "F001", new Movie( "You've Got Mail", Movie.Code.REGULAR ) );
    movies.put( "F002", new Movie( "Matrix", Movie.Code.REGULAR ) );
    movies.put( "F003", new Movie( "Cars", Movie.Code.CHILDRENS ) );
    movies.put( "F004", new Movie( "Fast & Furious X", Movie.Code.NEW ) );
    return movies;
  }

  /**
   * Return pricing information about a movie.
   * As for {@link #getMovies()}, this method is just a stub replacement
   * for DB or other class access so there is no point to optimizing it in any way for this exercise.
   * @return All the movies.
   */
  private PricingStructure getPricing( Movie movie ) {
    HashMap<Movie.Code, PricingStructure> prices = new HashMap<>();
    prices.put( Movie.Code.REGULAR, new PricingStructure( 2, new BigDecimal(2), true, new BigDecimal("1.5") ) );
    prices.put( Movie.Code.NEW, new PricingStructure( new BigDecimal(3) ) );
    prices.put( Movie.Code.CHILDRENS, new PricingStructure( 3, new BigDecimal("1.5"), true, new BigDecimal("1.5") ) );
    return prices.get( movie.getCode() );
  }

  /**
   * Find a single Movie by ID.
   * @param id Movie ID.
   * @return The Movie if found, else NULL.
   */
  private Movie getMovie( String id ) {
    return getMovies().get( id );
  }

  /**
   * Given a Movie and a piece of MovieRental data, return the price that the customer should pay.
   * @return The price for renting the movie based on the sales data.
   * @throws NullPointerException if pricing info is not found for the movie.
   */
  private BigDecimal calculatePrice( Movie movie, MovieRental rentalInfo ) {
    return getPricing( movie ).getCost( rentalInfo.getDays() );
  }
}
