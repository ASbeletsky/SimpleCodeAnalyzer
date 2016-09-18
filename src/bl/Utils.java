package bl;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Peter on 17.09.2016.
 */
public class Utils {
    @Nullable
    public static String fileToString(File file) {
        return pathToString(file.toPath());
    }

    @Nullable
    public static String pathToString(Path path) {
        try {
            return new String(Files.readAllBytes(path.toAbsolutePath()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
