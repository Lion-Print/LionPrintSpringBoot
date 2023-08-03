package uz.bprodevelopment.logisticsapp.base.file_storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uz.bprodevelopment.logisticsapp.base.file_storage.FileStorage;
import uz.bprodevelopment.logisticsapp.base.file_storage.FileStorageService;

import java.net.MalformedURLException;
import java.net.URLEncoder;

import static uz.bprodevelopment.logisticsapp.base.config.Urls.FILES_URL;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FileStorageService storageService;

    @Value("${file.upload}")
    private String uploadFolder;

    @GetMapping(FILES_URL + "/show/{fileHashId}")
    public ResponseEntity<?> showFile(
            @PathVariable(name = "fileHashId") String fileHashId
    ) {
        FileStorage file = storageService.get(fileHashId);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(file.getFileName()))
                    .contentType(MediaType.parseMediaType(file.getFileContentType()))
                    .contentLength(file.getFileSize())
                    .body(new FileUrlResource(String.format("%s/%s", uploadFolder, file.getFileUploadedPath())));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping(FILES_URL + "/download/{fileHashId}")
    public ResponseEntity<?> downloadFile(
            @PathVariable(name = "fileHashId") String fileHashId
    ) {
        FileStorage file = storageService.get(fileHashId);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "download; fileName=\"" + URLEncoder.encode(file.getFileName()))
                    .contentType(MediaType.parseMediaType(file.getFileContentType()))
                    .contentLength(file.getFileSize())
                    .body(new FileUrlResource(String.format("%s/%s", uploadFolder, file.getFileUploadedPath())));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }



}
