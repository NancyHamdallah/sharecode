package com.example.editorwithpayload.repository;

import com.example.editorwithpayload.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FileRepository extends MongoRepository<File, String> {

    List<File> findByCreatedBy(String createdBy);


}
