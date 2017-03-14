package m;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.lang.Exception;

@Service
public interface FileDataService {

    public FileData save(FileData fileData) throws Exception;
    public Path createTempFile(InputStream inputStream) throws Exception;
    public List<FileData> list() throws Exception;
    public FileData get() throws Exception;

}