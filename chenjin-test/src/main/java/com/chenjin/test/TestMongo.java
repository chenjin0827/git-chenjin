package com.chenjin.test;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import org.bson.types.ObjectId;

public class TestMongo {
    public static void main(String[] args) {
        Mongo mg = new Mongo("192.168.100.166", 27017);
        DB db = mg.getDB("filetest");
        GridFS fs = new GridFS(db, "test_111");
        String a1 ="5d4ae9f2a5dacf686e3c05a2";

        DBCollection coll=db.getCollection("test_111");
        DBObject doc=new BasicDBObject();
        ObjectId objid=new ObjectId(a1);
        doc.put("_id", objid);
        coll.remove(doc);
    }
}
