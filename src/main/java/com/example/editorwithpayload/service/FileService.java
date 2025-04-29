package com.example.editorwithpayload.service;

import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.entity.File;

import java.util.List;


public interface FileService {
List<File> findUserFiles();
void createFile(File file);


}
