package com.secKill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.secKill.entity.SecKill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);
	
	 private JedisPool jedisPool;
	 
	 private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);
	 
	 public RedisDao(String ip, int port){
		 jedisPool = new JedisPool(ip, port);
	 }
	 
	 
	 public SecKill getSecKill(long seckillId){
		 //Redis操作逻辑
		 try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null){
					SecKill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes , seckill, schema);
					return seckill;
				}
			} finally{
				jedis.close();
			} 
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
		 return null;
	 }
	 
	 public String putSeckill(SecKill secKill){
		 try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + secKill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60*60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(),e);
		}
		 return null;
	 }
}
