package m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.List;
import java.util.Arrays;

@Service
public class FileDataStorageService implements FileDataService {

    private final Path TEMP = Paths.get("./fs/TMP");

    @Autowired
    private FileDataRepository fileDataRepository;
    @Autowired
    private FileSystemService fileSystemService;

    /**
     * <p>Stores file metadata in database.</p>
     */
    @Override
    public FileData save(FileData fileData) throws Exception {
        if(fileData != null) {
            //store only one record
            fileDataRepository.deleteAll();
            fileData.setTimestamp(System.currentTimeMillis());
            fileData.setId(1L);
            fileData = fileDataRepository.save(fileData);
            fileSystemService.store(Paths.get(fileData.getPath()));
        }

        return fileData;
    }

    /**
     * <p>Creates temp file on filesystem</p>
     */

    @Override
    public Path createTempFile(InputStream inputStream) throws Exception {
        Files.copy(inputStream, 
            TEMP, 
            new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES 
            }
        );

        return TEMP;
    }

    /**
     * <p>Retrieves all file data from database</p>
     */

    @Override
    public List<FileData> list() {
        return (List<FileData>) fileDataRepository.findAll();
    }

    /**
     * <p>Retrieves file data from database</p>
     */

    @Override
    public FileData get() {
        return ((FileData) fileDataRepository.findAll().iterator().next());
    }
}