package uz.bprodevelopment.logisticsapp.base.file_storage;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileStorageRepo extends JpaRepository<FileStorage, Long>{

    FileStorage findByFileHashId(String fileHashId);

}
