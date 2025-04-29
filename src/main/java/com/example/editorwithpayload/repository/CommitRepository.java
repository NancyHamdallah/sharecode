package com.example.editorwithpayload.repository;

import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommitRepository extends MongoRepository<Commit, String> {

    Commit findTopByFileIdOrderByVersionDesc(File file);

    Commit save(Commit commit);

    List<Commit> findAllByFileId(File file);
}
