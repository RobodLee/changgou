package com.robod.file;

import lombok.Data;

/**
 * @author Robod
 * @date 2020/7/4 10:33
 * 封装文件上传信息
 * 时间
 * Author
 * type
 * size
 * 附加信息
 * 后缀
 * 文件内容：文件的字节数组
 */
@Data
public class FastDFSFile {

    //文件名
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要
    private String md5;
    //文件作者
    private String author;

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile
            (String name, byte[] content, String ext, String md5, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.author = author;
    }
}
