package com.kuang;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author ygl
 * @description
 * @date 2020/10/10 16:52
 */
public class testTX {
    public static void main(String[] args) {
        //1、new Jedis对象即可
        Jedis jedis = new Jedis("192.168.80.37", 6379);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("name","kuangShen");
        String result = jsonObject.toJSONString();
        Transaction multi = jedis.multi();

        try {
            multi.set("user1",result);
            multi.set("user2",result);
            multi.exec();//开启事务
        } catch (Exception e) {
            multi.discard();//放弃事务
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();//关闭连接
        }






    }
}
