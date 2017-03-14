package m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    private final Path FILEDIR = Paths.get("./fs");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FileSystemService fileSystemService;
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Setting up database schema");
        jdbcTemplate.execute("DROP TABLE FILEDATA IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE FILEDATA (ID INTEGER, NAME VARCHAR(255), PATH VARCHAR(255), TIMESTAMP BIGINT)");

        log.info("Setting up upload directory");
        FileSystemUtils.deleteRecursively(FILEDIR.toFile());
        Files.createDirectory(FILEDIR);

    }
}
