package com.example.editorwithpayload.service;

import com.example.editorwithpayload.dto.CommitDTO;
import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.entity.File;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommitService {

    public Commit getLatestCommitForFile(File file) ;
    public Commit createCommit(CommitDTO commitDTO);
    String setContentToUrl(String commitContent);
    List<Commit> findAllByFileId(String fileId);
}
