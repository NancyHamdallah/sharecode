package com.example.editorwithpayload.service;


public interface SupabaseStorageService {
    boolean uploadFile(String filePath, String content);
}
