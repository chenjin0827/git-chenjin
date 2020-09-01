package com.chenjin.rpc.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用Protostuff实现序列化
 * 使用 Objenesis 来实例化对象，比 Java 反射更加强大
 */
public class SerializationUtil {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap();
    private static Objenesis objenesis = new ObjenesisStd(true);

    private SerializationUtil() {
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (null == schema) {
            schema = RuntimeSchema.createFrom(cls);
            if (null != schema) {
                cachedSchema.put(cls, schema);
            }

        }
        return schema;
    }

    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        int defaultBufferSize = LinkedBuffer.DEFAULT_BUFFER_SIZE;
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(defaultBufferSize);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, linkedBuffer);
        } finally {
            linkedBuffer.clear();
        }
    }

    /**
     * 反序列化
     * @param data
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        T m = objenesis.newInstance(cls);
        Schema<T> schema = getSchema(cls);
        ProtostuffIOUtil.mergeFrom(data, m, schema);
        return m;
    }
}
