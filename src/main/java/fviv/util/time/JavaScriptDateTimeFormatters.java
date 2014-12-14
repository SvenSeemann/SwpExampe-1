package fviv.util.time;

import java.time.format.DateTimeFormatter;

/**
 * Created by justusadam on 12/12/14.
 */
public abstract class JavaScriptDateTimeFormatters {

    /**
     * DateTimeFormatter corresponding to the JavaScript Date().toISOString() method output.
     */
    public static final DateTimeFormatter javaScriptISODateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    /**
     * DateTimeFormatter corresponding to the Javascript Date().toUTCString() method output.
     *
     * According to the docs 'M' is the pattern letter for 'Month-of-year' as number and 'L' is the pattern letter for
     * 'Month-of-year' as text. However this did not work and apparently 'M' just works for both. Bug?
     */
    public static final DateTimeFormatter javaScriptUTCDateTimeFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");
}
