package com.example.editorwithpayload.service.impl;

import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.repository.FileRepository;
import com.example.editorwithpayload.repository.UserRepository;
import com.example.editorwithpayload.service.CommitService;
import com.example.editorwithpayload.service.FileService;
import com.example.editorwithpayload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommitService commitService;

    @Override
    public List<File> findUserFiles(){
        String email = userService.getEmail();
        List<File> allFiles = fileRepository.findByCreatedBy(email);
        return allFiles;

    }

    @Override
    public void createFile(File file){
        fileRepository.save(file);

    }

    private String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
