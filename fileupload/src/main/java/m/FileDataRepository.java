package m;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * <p>Performs create, read, update, delete operations</p>
 */
public interface FileDataRepository extends CrudRepository<FileData, Long> {
}