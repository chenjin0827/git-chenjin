package com.chenjin.mongo;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 导出mongoDb中的所有pdf文件
 * //        args[0] 举例 127.0.0.1  mongoDb的host
 * //        args[1] 举例 27017 mongoDb的端口
 * //        args[2] 举例 file  数据库名称
 * //        args[3] 举例 contract 桶名称
 */
public class ExportGridFSPdf {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {
        Mongo mg = new Mongo(args[0], Integer.valueOf(args[1]));
        DB db = mg.getDB(args[2]);
        exportPdf(db, args[3]);
    }

    private static void exportPdf(DB db, String bucket) throws IOException {
        GridFS gridFS = new GridFS(db, bucket);
        DBCursor fileList = gridFS.getFileList();
        System.out.println("bucket" + bucket + "中文件数量为====" + fileList.size());
        while (fileList.hasNext()) {
            FileOutputStream fileOutputStream = null;
            try {
                DBObject dbObject = fileList.next();
                String filename = (String) dbObject.get("filename");
                if (filename.endsWith(".pdf")) {
                    GridFSDBFile file = gridFS.find(filename).get(0);
                    File directory = new File("/home/pdf/");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    File f = new File("/home/pdf/" + filename);
                    f.createNewFile();
                    fileOutputStream = new FileOutputStream(f);
                    file.writeTo(fileOutputStream);
                    System.out.println("文件" + filename + "生成成功！");
                }
            } finally {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            }
        }
        System.out.println("所有文件生成成功！");
    }

}
