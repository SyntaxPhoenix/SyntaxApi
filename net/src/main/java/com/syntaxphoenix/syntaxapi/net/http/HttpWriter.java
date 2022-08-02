package com.syntaxphoenix.syntaxapi.net.http;

import java.io.IOException;
import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class HttpWriter {

    public static final DateTimeFormatter FORMATTER;

    static {
        HashMap<Long, String> dow = new HashMap<>();
        dow.put(1L, "Mon");
        dow.put(2L, "Tue");
        dow.put(3L, "Wed");
        dow.put(4L, "Thu");
        dow.put(5L, "Fri");
        dow.put(6L, "Sat");
        dow.put(7L, "Sun");
        HashMap<Long, String> moy = new HashMap<>();
        moy.put(1L, "Jan");
        moy.put(2L, "Feb");
        moy.put(3L, "Mar");
        moy.put(4L, "Apr");
        moy.put(5L, "May");
        moy.put(6L, "Jun");
        moy.put(7L, "Jul");
        moy.put(8L, "Aug");
        moy.put(9L, "Sep");
        moy.put(10L, "Oct");
        moy.put(11L, "Nov");
        moy.put(12L, "Dec");
        FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient().appendText(ChronoField.DAY_OF_WEEK, dow)
            .appendLiteral(", ").appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(' ')
            .appendText(ChronoField.MONTH_OF_YEAR, moy).appendLiteral(' ').appendValue(ChronoField.YEAR, 4).appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendLiteral(' ').appendLiteral("GMT").toFormatter().withZone(ZoneId.of("GMT"));
    }

    private final PrintStream output;

    public HttpWriter(PrintStream output) {
        this.output = output;
    }

    /*
     * 
     */

    public PrintStream getStream() {
        return output;
    }

    /*
     * 
     */

    public HttpWriter write(int code) {
        getStream().println("HTTP/1.1 " + code + ' ' + ResponseCode.getName(code));
        return this;
    }

    /*
     * 
     */

    public HttpWriter writeServer() {
        return write("Server", "Java SyntaxPhoenix HTTP Server from Lauriichan : 1.0");
    }

    public HttpWriter writeDate() {
        return write("Date", FORMATTER.format(OffsetDateTime.now()));
    }

    public HttpWriter writeLength(int length) {
        return write("Content-Length", length);
    }

    public HttpWriter writeType(ContentType type) {
        return writeType(type.type());
    }

    public HttpWriter writeType(String type) {
        return write("Content-Type", type + "; charset=UTF-8");
    }

    public HttpWriter writeCookies(Cookie... cookies) {
        for (int index = 0; index < cookies.length; index++) {
            writeCookie(cookies[index]);
        }
        return this;
    }

    public HttpWriter writeCookie(Cookie cookie) {
        StringBuilder builder = new StringBuilder();
        builder.append(cookie.getName());
        builder.append('=');
        builder.append(cookie.getValue());
        HashMap<String, Object> properties = cookie.getProperties();
        if (!properties.isEmpty()) {
            synchronized (properties) {
                builder.append("; ");
                builder.append(properties.entrySet().stream()
                    .map(entry -> entry.getValue() == null ? entry.getKey() : entry.getKey() + '=' + entry.getValue())
                    .collect(Collectors.joining("; ")));
            }
        }
        return write("Set-Cookie", builder.toString());
    }

    /*
     * 
     */

    public <T extends Object> HttpWriter write(Entry<String, T> entry) {
        return write(entry.getKey(), entry.getValue());
    }

    public HttpWriter write(String key, Object value) {
        return write(key, value.toString());
    }

    public HttpWriter write(String key, String value) {
        getStream().println(key + ": " + value);
        return this;
    }

    /*
     * 
     */

    public HttpWriter write(byte[] buffer) throws IOException {
        getStream().write(buffer);
        return this;
    }

    public HttpWriter line() {
        getStream().println();
        return this;
    }

    /*
     * 
     */

    public HttpWriter clear() {
        getStream().flush();
        return this;
    }

    public HttpWriter close() {
        getStream().close();
        return this;
    }

}
