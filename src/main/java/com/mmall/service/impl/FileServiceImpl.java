package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String filename = file.getOriginalFilename();
        String fileExtensionName = filename.substring(filename.lastIndexOf(".")+1);
        String uploadFilename = UUID.randomUUID()+"."+fileExtensionName;
        logger.info("Starting upload file, the original filename:{}, the filepath:{}, the new filename:{}",filename,path,uploadFilename);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFilename);
        try {
            file.transferTo(targetFile);
            //upload the targetFile to ftp server
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //after uploading to ftp server, delete the targetFile under *upload* dir
            targetFile.delete();
        } catch (IOException e) {
            logger.error("File upload exception.",e);
            return null;
        }
        return targetFile.getName();
    }
}
