package my.tocker.webtools.web.util;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpRequestUtils {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DataEntry {
        private String key;
        private String value;
    }

    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }

    public static Map<String, String> parseCookies(String cookies) {
        return parseValues(cookies, ";");
    }

    private static Map<String, String> parseValues(String values, String separator) {
        if (Strings.isNullOrEmpty(values)) {
            return Maps.newHashMap();
        }

        String[] tokens = values.split(separator);
        return Arrays.stream(tokens)
                .map(t -> getKeyValue(t, "="))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(DataEntry::getKey, DataEntry::getValue));
    }

    private static Optional<DataEntry> getKeyValue(String stringToken, String regex) {
        String kv = stringToken.trim();
        if (Strings.isNullOrEmpty(kv)) {
            return Optional.empty();
        }

        String[] tokens = kv.split(regex);
        if (tokens.length != 2) {
            return Optional.empty();
        }

        return Optional.of(new DataEntry(tokens[0].trim(), tokens[1].trim()));
    }

    public static String parseHeader(String header) {
        return getKeyValue(header, ":").orElse(new DataEntry()).getValue();
    }


}
