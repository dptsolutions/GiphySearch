package dptsolutions.com.giphysearch.rest;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;

import java.lang.reflect.Type;

import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static org.threeten.bp.format.ResolverStyle.STRICT;

/**
 * Giphy doesn't use a standard ISO Date/Time string, so we must roll our own converter
 */
public class GiphyDateTimeConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE).appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter()
            .withResolverStyle(STRICT)
            .withChronology(IsoChronology.INSTANCE);

    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
          throws JsonParseException {
        return FORMATTER.parse(json.getAsString(), LocalDateTime.FROM);
    }
}