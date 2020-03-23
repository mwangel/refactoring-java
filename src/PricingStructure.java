import java.math.BigDecimal;

/**
 * Pricing storage to support the business logic.
 * There is a one-to-one relationship between objects of this class and Movie,
 * but that is not encoded here at this time. That supports a long-term strategy
 * of expanding into AI-calculated per-movie pricing. Though not per-customer.
 *
 * For the future we could split this into subclasses and override the getCost method
 * but why bother when it's this simple? There might be better uses of our time.
 */
public class PricingStructure {
    int daysAtRegularPrice;
    BigDecimal regularPrice;
    BigDecimal pricePerEachAdditionalDay;
    boolean differentPriceForAdditionalDays;

    /** Simple pricing, same price each day. */
    public PricingStructure( BigDecimal regularPrice ) {
        this( 1, regularPrice, false, regularPrice );
    }

    /** A start price for a number of days, then another price for each additional day. */
    public PricingStructure(
            int daysAtRegularPrice,
            BigDecimal regularPrice,
            boolean differentPriceForAdditionalDays,
            BigDecimal pricePerEachAdditionalDay )
    {
        this.daysAtRegularPrice = daysAtRegularPrice;
        this.regularPrice = regularPrice;
        this.differentPriceForAdditionalDays = differentPriceForAdditionalDays;
        this.pricePerEachAdditionalDay = pricePerEachAdditionalDay;
    }

    /** The business logic and raison d'être of this class. */
    public BigDecimal getCost( int days ) {
        // Pay at least this much...
        BigDecimal result = regularPrice;

        //...and then some more per day beyond the base.
        if( days > daysAtRegularPrice ) {
            if( differentPriceForAdditionalDays ) {
                result = result.add( pricePerEachAdditionalDay.multiply( BigDecimal.valueOf( days - daysAtRegularPrice ) ) );
            }
            else {
                return regularPrice.multiply( BigDecimal.valueOf( days - daysAtRegularPrice ) );
            }
        }

        return result;
    }
}
