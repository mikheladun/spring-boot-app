package m;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.lang.Exception;

@RestController
public class FileDataController {

    private final FileDataService fileDataService;

    @Autowired
    public FileDataController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    /**
     * <p>Default API endpoint</p>
     * 
     * <p>Expected HTTP POST and request '/'.</p>
     */

    @RequestMapping(value="/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileData>> index() throws Exception {
        return ResponseEntity.ok()
                .body(fileDataService.list());
    }

    /**
     * <p>API to upload a file with a few meta-data fields. 
     * Persist meta-data in persistence store (In memory DB or file system 
     * and store the content on a file system)</p>
     * 
     * <p>Expected HTTP POST and request '/upload'.</p>
     */

    @RequestMapping(value="/upload", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FileData> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        Path tempFilePath = fileDataService.createTempFile(multipartFile.getInputStream());

        FileData fileData = new FileData();
        fileData.setName(multipartFile.getName());
        fileData.setPath(tempFilePath.toUri().getPath());
        fileDataService.save(fileData);

        return ResponseEntity.ok()
                .body(fileData);
    }

    /**
     * <p>API to get file meta-data</p>
     * 
     * <p>Expected HTTP POST and request '/metadata'.</p>
     */
    @RequestMapping(value="/metadata", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileData>> metadata() throws Exception {
        return ResponseEntity.ok()
                .body(fileDataService.list());
    }

    /**
     * <p>API to download content stream</p>
     * 
     * <p>Expected HTTP POST and request '/download'.</p>
     */
    @RequestMapping(value="/download", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Resource> download() throws Exception {
        FileData fileData = fileDataService.get();
        Resource resource = new UrlResource(Paths.get(fileData.getPath()).toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}