package uz.bprodevelopment.logisticsapp.base.file_storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;



@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepo repo;

    @Value("${file.upload}")
    private String uploadFolder;

    @Override
    public String save(MultipartFile multipartFile) {

        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileName(multipartFile.getOriginalFilename());
        fileStorage.setFileExtension(getExtension(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setFileContentType(multipartFile.getContentType());

        repo.save(fileStorage);

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String uploadPath = String.format("upload_files/%d/%d/%d/",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        String uploadFolder = String.format("%s%s",
                this.uploadFolder,
                uploadPath
        );

        File uploadFile = new File(uploadFolder);
        if (!uploadFile.exists() && uploadFile.mkdirs()) {
            System.out.println(uploadFolder + " created");
        }
        fileStorage.setFileHashId(getHash());
        fileStorage.setFileUploadedPath(
                String.format(
                        "%s/%s.%s",
                        uploadPath,
                        fileStorage.getFileHashId(),
                        fileStorage.getFileExtension()));

        repo.save(fileStorage);
        uploadFile = uploadFile.getAbsoluteFile();
        File file = new File(
                uploadFile,
                String.format(
                        "%s.%s",
                        fileStorage.getFileHashId(),
                        fileStorage.getFileExtension()
                )
        );
        try {
            multipartFile.transferTo(file);
            return fileStorage.getFileHashId();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public FileStorage get(String fileHashId) {
        return repo.findByFileHashId(fileHashId);
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.substring(1, fileName.length() - 1).contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String getHash() {
        Long millis = System.currentTimeMillis();
        String hash = getRandomString();
        return millis + "_" + hash;
    }

    private String getRandomString() {
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = upper.toLowerCase(Locale.ROOT);
        final String digits = "0123456789";
        final String alphaNum = upper + lower + digits;
        final Random random = new Random();

        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            randomString.append(alphaNum.charAt(random.nextInt(alphaNum.length())));
        }
        return randomString.toString();
    }

}
