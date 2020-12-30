/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFileByFileName(String fileName, Integer userId) {
        File file = fileMapper.getFileByFileName(fileName, userId);
        return file;
    }

    public File getFileByFileId(Integer fileId) {
        File file = fileMapper.getFileByFileId(fileId);
        return file;
    }

    public Integer saveFile(File file) {
        Integer rowNumber = fileMapper.saveFile(file);
        return rowNumber;
    }

    public List<File> getFilesByUserId(Integer userId) {
        List<File> listOfFiles = fileMapper.getFilesByUserId(userId);
        return listOfFiles;
    }

    public void deleteFileByFileId(Integer fileId) {
        fileMapper.deleteFileByFileId(fileId);
    }
}
