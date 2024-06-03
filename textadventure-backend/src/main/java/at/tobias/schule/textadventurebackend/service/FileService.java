package at.tobias.schule.textadventurebackend.service;


import at.tobias.schule.textadventurebackend.dto.response.GameInfoDTO;
import at.tobias.schule.textadventurebackend.exception.FileEmptyException;
import at.tobias.schule.textadventurebackend.exception.FileUploadException;
import at.tobias.schule.textadventurebackend.exception.GameFileReadException;
import at.tobias.schule.textadventurebackend.exception.GameHeaderNotReadableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class FileService {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ObjectMapper objectMapper = new ObjectMapper();



    @CacheEvict(value = "game", key = "#multipartFile.originalFilename")
    public String processFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("No file found or file is empty");
        }

        try {
            // Save the file locally
            byte[] bytes = multipartFile.getBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);

            readWriteLock.writeLock().lock();
            File dir = new File("upload");
            if (dir.mkdir())
                log.debug("Created Dir upload");

            String path = String.format("upload/%s", multipartFile.getOriginalFilename());
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
                bos.write(bytes);
            }

            return content;


        } catch (IOException e) {
            throw new FileUploadException(String.format("Failed to upload file %s", multipartFile.getOriginalFilename()));
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    @Cacheable("game")
    public String readFile(String name) {
        String path = String.format("upload/%s", name);
        String content = "n.a";
        readWriteLock.readLock().lock();
        try {
            content = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new GameFileReadException(String.format("Cannot read file %s", path));
        } finally {
            readWriteLock.readLock().unlock();
        }
        return content;
    }

    @Cacheable("game")
    public GameInfoDTO readHeaderOfFile(String name) {
        String path = String.format("upload/%s", name);
        String content;
        GameInfoDTO gameInfoDTO;
        readWriteLock.readLock().lock();
        try {
            content = Files.readString(Path.of(path));
            gameInfoDTO = objectMapper.readValue(content, GameInfoDTO.class);
        } catch (IOException e) {
            throw new GameFileReadException(String.format("Cannot read file %s", path));
        } finally {
            readWriteLock.readLock().unlock();
        }
        if (gameInfoDTO.getDisplayName() == null || gameInfoDTO.getAuthor() == null)
            throw new GameHeaderNotReadableException(String.format("Cannot read file %s", name));
        return gameInfoDTO;
    }

    public GameInfoDTO readHeaderOfFileFromString(String content) {

        GameInfoDTO gameInfoDTO;
        try {
            gameInfoDTO = objectMapper.readValue(content, GameInfoDTO.class);
        } catch (JsonProcessingException e) {
            throw new GameHeaderNotReadableException(String.format("Cannot read string %s", content));
        }
        if(gameInfoDTO.getAuthor() == null||gameInfoDTO.getDisplayName()==null)
            throw new GameHeaderNotReadableException(String.format("Cannot read string %s", content));

        return gameInfoDTO;
    }

    public List<GameInfoDTO> readAllFiles(){
        List<GameInfoDTO> allGameInfos = new ArrayList<>();
        readWriteLock.readLock().lock();
        File dir = new File("upload");
        for(File file : Objects.requireNonNull(dir.listFiles())){
            try {
                allGameInfos.add(readHeaderOfFile(file.getName()));

            }
            catch (GameFileReadException | GameHeaderNotReadableException ignored){

            }
        }
        readWriteLock.readLock().unlock();
        return allGameInfos;
    }
    public String getFileNameByName(String textAdventure){
        String fileName = "";
        readWriteLock.readLock().lock();
        File dir = new File("upload");

        for(File file : Objects.requireNonNull(dir.listFiles())){
            try {
                GameInfoDTO gameInfoDTO = readHeaderOfFile(file.getName());
                if(gameInfoDTO.getDisplayName().equals(textAdventure)) {
                    fileName = file.getName();
                    break;
                }
            }
            catch (GameFileReadException | GameHeaderNotReadableException ignored){
                ignored.printStackTrace();
            }
        }
        readWriteLock.readLock().unlock();
        return fileName;
    }
}
