package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class FileServices {

    FileMapper fileMapper;

    public FileServices(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File fileExists(File file, User user) {
        List<File> files = fileMapper.getUserFiles(user);

        for (File fileLoop : files) {
            if (fileLoop.getFilename().equals(file.getFilename()) && fileLoop.getUserid().equals(file.getUserid()))
                return  fileLoop;
        }

        return null;
    }

    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public int addFile(@NotNull File file) {
        return fileMapper.addFile(file);
    }

    public boolean deleteFile(@NotNull File file) {
        if (fileMapper.getFile(file.getFileId()) != null) {
            fileMapper.deleteFile(file);
            return true;
        } else {
            return false;
        }
    }

    public void updateFile(@NotNull File file) {
        fileMapper.updateFile(file);
    }

    public List<File> getUserFiles(@NotNull User user) {
        return fileMapper.getUserFiles(user);
    }
}
