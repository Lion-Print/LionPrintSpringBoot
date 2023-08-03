package uz.bprodevelopment.logisticsapp.base.file_storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String save(MultipartFile file);

    FileStorage get(String fileHashId);

}
