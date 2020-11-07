package com.kuang;

import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ygl
 * @description
 * @date 2020/10/10 14:59
 */
public class testPing {
    public static void main(String[] args) throws InterruptedException {
        //1、new Jedis对象即可
        Jedis jedis = new Jedis("192.168.80.37", 6379);
        //Jedis的所有指令就是所学习的指令
        String ping = jedis.ping();
        System.out.println("是否Ping通："+ping);
        System.out.println("清空数据："+jedis.flushDB());
        System.out.println("判断某个键是否存在："+jedis.exists("username"));
        System.out.println("新增<‘username’,‘kuangshen’>的键值对："+jedis.set("username","kuangshen"));
        System.out.println("新增<‘password’,‘password’>的键值对："+jedis.set("password","password"));
        System.out.println("系统中所有的键值如下：");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
        System.out.println("删除键password："+jedis.del("password"));
        System.out.println("判断键password是否存在："+jedis.exists("password"));
        System.out.println("查看键username所存储值的类型："+jedis.type("username"));
//        System.out.println("新增<‘password’,‘password’>的键值对："+jedis.set("password","password"));
        System.out.println("随机返回key空间的一个："+jedis.randomKey());
        System.out.println("重命名key："+jedis.rename("username","name"));
        System.out.println("取出更改后的name："+jedis.get("name"));
        System.out.println("按照索引进行查询："+jedis.select(0));
        System.out.println("返回当前数据库中key的数目："+jedis.dbSize());
        System.out.println("删除当前数据库的所有key："+jedis.flushDB());
        System.out.println("返回当前数据库中key的数目："+jedis.dbSize());
        System.out.println("删除所有数据库中的key："+jedis.flushAll());

        jedis.flushDB();
        System.out.println("=============增加数据============");
        System.out.println(jedis.set("key1","value1"));
        System.out.println(jedis.set("key2","value2"));
        System.out.println(jedis.set("key3","value3"));
        System.out.println("删除键key2："+jedis.del("key2"));
        System.out.println("获取键key2："+jedis.get("key2"));
        System.out.println("修改键key1的值："+jedis.set("key1","value1Change"));
        System.out.println("获取键key1的值："+jedis.get("key1"));
        System.out.println("在key3后面加入值："+jedis.append("key3","End"));
        System.out.println("获取key3的值："+jedis.get("key3"));
        System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02","key03","value03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
        System.out.println("删除多个键值对："+jedis.del("key01","key03"));
        System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
        jedis.flushDB();
        System.out.println("==================新增键值对防止覆盖原先值=======================");
        System.out.println("新增键值对key1："+jedis.setnx("key1","value1"));
        System.out.println("新增键值对key2："+jedis.setnx("key2","value2"));
        System.out.println("覆盖键值对key2："+jedis.setnx("key2","value2New"));
        System.out.println("获取键值对key2："+jedis.get("key2"));

        System.out.println("================新增键值对并设置有效时间===================");
        System.out.println("新增键值对并设置有效时间："+jedis.setex("key3",3,"value3"));
        System.out.println("获取键值对key3："+jedis.get("key3"));
//        TimeUnit.SECONDS.sleep(3);
        System.out.println("睡眠30s后获取键值对key3："+jedis.get("key3"));

        System.out.println("================获取原始值并更新新值====================");
        System.out.println("获得key2的值："+jedis.get("key2"));
        System.out.println("获取key2的值并更新新值："+jedis.getSet("key2","value2New"));
        System.out.println("更新后的key2的值："+jedis.get("key2"));
        System.out.println("获得key2的值的字符串："+jedis.getrange("key2",2,4));

        jedis.flushDB();
        System.out.println("==================添加一个list===================");
        System.out.println("添加collections的list："+jedis.lpush("collections","ArrayList","Vector","Stack","HashMap","WeakHashMap","LinkedHashMap"));
        System.out.println("向collections的list中添加一个值："+jedis.lpush("collections","HashSet"));
        System.out.println("向collections的list中添加一个值："+jedis.lpush("collections","TreeSet"));
        System.out.println("向collections的list中添加一个值："+jedis.lpush("collections","TreeMap"));
        System.out.println("collections中的所有值："+jedis.lrange("collections",0,-1));
        System.out.println("collections中0到3的值："+jedis.lrange("collections",0,3));
        System.out.println("======================");
        System.out.println("删除指定元素个数："+jedis.lrem("collections",2,"HashMap"));
        System.out.println("collections的值："+jedis.lrange("collections",0,-1));
        System.out.println("删除0-3元素以外的内容："+jedis.ltrim("collections",0,3));
        System.out.println("collections的值："+jedis.lrange("collections",0,-1));
        System.out.println("collections表出栈（左端）："+jedis.lpop("collections"));
        System.out.println("collections的值："+jedis.lrange("collections",0,-1));
        System.out.println("collections从右端出栈："+jedis.rpop("collections"));
        System.out.println("collections的值："+jedis.lrange("collections",0,-1));
        System.out.println("修改collections中下标为1的值："+jedis.lset("collections",1,"HashSet111"));
        System.out.println("collections的值："+jedis.lrange("collections",0,-1));
        System.out.println("=====================");
        System.out.println("collections的长度："+jedis.llen("collections"));
        System.out.println("获取下标为1的值："+jedis.lindex("collections",1));
        System.out.println("=====================");
        jedis.lpush("sortedList","3","2","6","4","0");
        System.out.println("排序前："+jedis.lrange("sortedList",0,-1));
        System.out.println("进行排序："+jedis.sort("sortedList"));
        System.out.println("排序后："+jedis.lrange("sortedList",0,-1));
        jedis.flushDB();
        System.out.println("==============向set中添加元素（不重复）=================");
        System.out.println("向eleSet中添加元素：");

    }
}
