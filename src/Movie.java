public class Movie {
    /** A movie can have at most one Code. */
    public enum Code { REGULAR, CHILDRENS, NEW }

    private String title;
    private Code code;

    public Movie( String title, Code code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public Code getCode() {
        return code;
    }
}
