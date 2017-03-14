package m;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long timestamp;
    private String path;

    protected FileData() {
    }

    protected FileData(Long id, String name, String path, Long timestamp) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.timestamp = timestamp;
    }

    protected FileData(Long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format(
                "FileData[id=%d, name='%s', path='%s', timestamp='%s']",
                id, name, path, timestamp);
    }

}