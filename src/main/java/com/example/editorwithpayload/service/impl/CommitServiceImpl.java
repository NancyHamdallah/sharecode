package com.example.editorwithpayload.service.impl;

import com.example.editorwithpayload.dto.CommitDTO;
import com.example.editorwithpayload.entity.Commit;
import com.example.editorwithpayload.entity.File;
import com.example.editorwithpayload.entity.User;
import com.example.editorwithpayload.repository.CommitRepository;
import com.example.editorwithpayload.repository.FileRepository;
import com.example.editorwithpayload.repository.UserRepository;
import com.example.editorwithpayload.service.CommitService;
import com.example.editorwithpayload.service.SupabaseStorageService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommitRepository commitRepository;
    @Autowired
    private SupabaseStorageService storageService;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.bucket.name}")
    private String bucketName;

    private final Map<String, Object> locks = new ConcurrentHashMap<>();
    @Override


    public Commit createCommit(CommitDTO commitDTO) {
        String fileId = commitDTO.getRoomId();
        Object lock = locks.computeIfAbsent(fileId, id -> new Object());

        synchronized (lock) {
            Optional<File> file = fileRepository.findById(fileId);
            if (file.isEmpty()) throw new RuntimeException("File not found");
            System.out.println("fileId:" + file.get().getId());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username);

            Commit latest = commitRepository.findTopByFileIdOrderByVersionDesc(file.get());
            int newVersion = (latest != null) ? latest.getVersion() + 1 : 1;

            String commitContent = commitDTO.getContent();
            String fileExtension = commitDTO.getLanguage();
            String fileName = "files/" + fileId + "/v" + newVersion + "." + fileExtension;

            boolean uploaded = storageService.uploadFile(fileName, commitContent);
            if (!uploaded) throw new RuntimeException("Failed to upload content to Supabase");

            String publicUrl = supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;

            Commit commit = new Commit();
            commit.setCommitMsg(commitDTO.getCommitMsg());
            commit.setVersion(newVersion);
            commit.setUrlLink(publicUrl);
            commit.setUserId(user);
            commit.setFileId(file.get());

            return commitRepository.save(commit);
        }
    }



    @Override
    public Commit getLatestCommitForFile(File file) {
        return commitRepository.findTopByFileIdOrderByVersionDesc(file);
    }

    @Override
    public String setContentToUrl(String commitContent){
        String url = "";
        return url;
    }

    @Override
    public List<Commit> findAllByFileId(String fileId){
        List<Commit> commits = null;
        Optional<File> file = fileRepository.findById(fileId);
        System.out.println("filename:" + file);
        if (file.isPresent()) {
            commits = commitRepository.findAllByFileId(file.get());
            System.out.println("commits:"+commits.toString());
        }

        return commits;
    }
}
