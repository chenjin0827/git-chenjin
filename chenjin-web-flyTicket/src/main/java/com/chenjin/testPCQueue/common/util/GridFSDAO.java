package com.chenjin.testPCQueue.common.util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridFSDAO
{
    private DB db;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public GridFSDAO(String hostname, int port, String dbname)
    {
        Mongo mg = new Mongo(hostname, port);
        this.db = mg.getDB(dbname);
    }

    public String saveFile(File file, String fileName, String bucket) {
        String id = null;
        try
        {
            GridFSInputFile gfsInput = new GridFS(this.db, bucket).createFile(file);
            gfsInput.setFilename(fileName);
            gfsInput.save();
            id = gfsInput.getId().toString();
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            return null;
        }
        GridFSInputFile gfsInput;
        return id;
    }

    public String saveFile(byte[] data, String fileName, String bucket) {
        String id = null;

        GridFSInputFile gfsInput = new GridFS(this.db, bucket).createFile(data);
        gfsInput.setFilename(fileName);
        gfsInput.save();
        id = gfsInput.getId().toString();
        return id;
    }

    public void findFileByIdToOutputStream(String fileId, String bucket, HttpServletResponse response) {
        try {
            GridFSDBFile gfsFile = new GridFS(this.db, bucket).find(new ObjectId(fileId));

            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(gfsFile.getFilename(), "UTF-8"));

            OutputStream out = response.getOutputStream();
            gfsFile.writeTo(out);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    public void readFileByIdToOutputStream(String fileId, String bucket, HttpServletResponse response) {
        try {
            GridFSDBFile gfsFile = new GridFS(this.db, bucket).find(new ObjectId(fileId));

            OutputStream out = response.getOutputStream();
            gfsFile.writeTo(out);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    public String findFileByIdToString(String fileId, String bucket) {
        String fileString = null;
        ByteArrayOutputStream baos = null;
        try {
            GridFSDBFile gfsFile = new GridFS(this.db, bucket).find(new ObjectId(fileId));

            baos = new ByteArrayOutputStream();

            if (gfsFile != null) {
                gfsFile.writeTo(baos);
            }
            fileString = Base64.encodeBase64String(baos.toByteArray());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return fileString;
    }

    public byte[] findFileById(String fileId, String bucket) {
        byte[] b = null;
        try {
            GridFSDBFile gfsFile = new GridFS(this.db, bucket).find(new ObjectId(fileId));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            if (gfsFile != null) {
                gfsFile.writeTo(baos);
            }
            b = baos.toByteArray();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return b;
    }
}