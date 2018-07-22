package my.tocker.webtools.web.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class IOUtils {

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        int readSize = br.read(body, 0, contentLength);
        log.debug(String.format("[BUFFERREAD] %d bytes", readSize));
        return String.copyValueOf(body);
    }
}
