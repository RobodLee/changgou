package com.robod.utils;

import com.robod.file.FastDFSFile;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Robod
 * @date 2020/7/4 11:25
 * 实现FastFDS文件管理
 * 文件上传
 * 文件删除
 * 文件下载
 * 文件信息获取
 * Storage信息获取
 * Tracker信息获取
 */
public class FastDFSUtils {

    private static StorageClient storageClient;
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;

    static {
        try {
            String path = new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker连接信息
            ClientGlobal.init(path);
            //创建一个Tracker的客户端对象
            trackerClient = new TrackerClient();
            //通过TrackerClient访问TrackerServer服务，获取连接对象
            trackerServer = trackerClient.getConnection();
            //通过TrackerServer的连接信息去获取Storage的连接信息，存储进StorageClient对象中
            storageClient = new StorageClient(trackerServer, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @param file
     */
    public static String[] upload(FastDFSFile file) throws Exception {
        //上传文件
        return storageClient.upload_file(file.getContent(), file.getExt(), null);
    }

    /**
     * 下载文件
     * @param groupName         文件所在的组名     group1
     * @param remoteFileName    文件的路径及名字   M00/00/00/wKgfyF8AjpSADeBLABXmlvc7iOY701.jpg
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception {
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 删除文件
     * @param groupName         文件所在的组名     group1
     * @param remoteFileName    文件的路径及名字   M00/00/00/wKgfyF8AjpSADeBLABXmlvc7iOY701.jpg
     */
    public static int deleteFile(String groupName, String remoteFileName) throws Exception {
        return storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取文件信息
     * @param groupName         文件所在的组名     group1
     * @param remoteFileName    文件的路径及名字   M00/00/00/wKgfyF8AjpSADeBLABXmlvc7iOY701.jpg
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName) throws Exception {
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /**
     * 获取storage信息
     * @return  store_path_index
     */
    public static StorageServer getStorage() throws IOException {
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取Storage的IP和端口信息
     * @param groupName
     * @param fileName
     * @return ServerInfo：ip_addr，port
     * @throws IOException
     */
    public static ServerInfo[] getStorageInfo(String groupName, String fileName) throws IOException {
        return trackerClient.getFetchStorages(trackerServer,groupName,fileName);
    }

    /**
     * 获取Tracker信息
     * @return
     */
    public static String getTrackerInfo() {
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int port = ClientGlobal.getG_tracker_http_port();
        return new StringBuilder(ip).append(":").append(port).toString();
    }

}
