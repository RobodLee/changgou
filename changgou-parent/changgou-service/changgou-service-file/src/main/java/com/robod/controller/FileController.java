package com.robod.controller;

import com.robod.entity.Result;
import com.robod.entity.StatusCode;
import com.robod.file.FastDFSFile;
import com.robod.utils.FastDFSUtils;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Robod
 * @date 2020/7/4 19:36
 */
@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        FastDFSFile fastDFSFile = new FastDFSFile(
                multipartFile.getOriginalFilename(),
                multipartFile.getBytes(),
                StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        System.out.println(fastDFSFile.toString() );
        String[] upload = FastDFSUtils.upload(fastDFSFile);
        String groupName = upload[0];
        String fileName = upload[1];
        System.out.println(groupName);
        System.out.println(fileName);
        System.out.println("------------------");
        System.out.println("获取文件信息");
        FileInfo fileInfo = FastDFSUtils.getFileInfo(groupName, fileName);
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getFileSize());
        System.out.println(fileInfo.getCreateTimestamp());
        System.out.println(fileInfo.getCrc32());
        System.out.println("----------------------");
        System.out.println("获取Storage信息");
        StorageServer storage = FastDFSUtils.getStorage();
        System.out.println(storage.getStorePathIndex());
        System.out.println("-----------------");
        System.out.println("获取Storage的IP和端口信息");
        ServerInfo[] storageInfo = FastDFSUtils.getStorageInfo(groupName, fileName);
        for (ServerInfo serverInfo : storageInfo) {
            System.out.println(serverInfo.getIpAddr());
            System.out.println(serverInfo.getPort());
        }
        System.out.println("--------------------");
        System.out.println("获取Tracker信息");
        String trackerInfo = FastDFSUtils.getTrackerInfo();
        System.out.println(trackerInfo);
        return new Result<>(true, StatusCode.OK,"文件上传成功","---");
    }
}
