/**
 * The enum ValidationOrder contains the constants needed for deciding
 * the application order of the validator to the data.
 * @author Ben Mulongo
 * @version 1.0
 * @since 2020-10-04
 */
public enum ValidationOrder
{
    /* The enum ValidationOrder is implemented in one separated class in order to
    avoid DRY and to make it easily accessible to every other classes */
    BYINSERTIONORDER,
    BYPRIORITYORDER
}
