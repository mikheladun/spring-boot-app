package m;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements FileSystemService {

    private final Path FILE = Paths.get("./fs/FILE");

    /**
     * <p>Stores files in filesystem upload folder</p>
     */

    @Override
    public Path store(Path filePath) throws Exception {
        Files.copy(Files.newInputStream(filePath), 
            FILE, new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES 
            }
        );

        return FILE;
    }

}