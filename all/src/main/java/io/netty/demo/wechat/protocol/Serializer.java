package io.netty.demo.wechat.protocol;

/**
 * 序列化接口
 * 序列化接口有三个方法，
 * getSerializerAlgorithm() 获取具体的序列化算法标识，
 * serialize() 将 Java 对象转换成字节数组，
 * deserialize() 将字节数组转换成某种类型的 Java 对象，
 */
public interface Serializer {
    byte JSON_SERILIZER = 1;

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
