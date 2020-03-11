package com.steer.zookeeper.wwq;

import com.steer.zookeeper.ReadWriteZookeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/10 22:29
 */
public class TestZookeeperNew {

    public static final String HOSTS = "127.0.0.1:2181";

    public static final String ROOT_PATH = "/wwq";
    private ReadWriteZookeeper zk;

    @Before
    public void initZooKeeper() throws IOException {
        this.zk = new ReadWriteZookeeper();
        try {
            zk.connection(HOSTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @After
    public void  closeZookeeper(){
        try {
            zk.closeZookeeper();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreate(){
        //String path = "/wwq";
        String path = "/test";
        String value = "123456";
        String node = zk.createNode(path, value);
        System.out.println(String.format("创建节点：%s",node));
    }

    @Test
    public void testGetNodeData(){
        String path = "/wwq";
        String value = zk.getNodeData(path);
        System.out.println(String.format("===========获取的数据为: %s=========",value));
    }

    @Test
    public void testExists(){
        String path = "/idea";
        zk.existsNode(path,false);
    }

    @Test
    public void testSetData(){
        String data = "Hello world 人民";
        zk.setNodeData(ROOT_PATH,data,-1);
    }
    @Test
    public void testDelete(){
        String path = "/config/app1";
        zk.deleteNode(path, -1);
    }

    @Test
    public void  getZnodeChildren(){
        Stat stat = new Stat();
        zk.getChildrens("/", false, stat);
        System.out.println("stat version: "+stat.getVersion());
    }
    @Test
    public void  testCreateChildren(){
       // String path = ROOT_PATH.concat("/app1");
        String path2 = "/config".concat("/app1");
        String path = "/config".concat("/app2");
        String data = "hello123";
        //zk.createNode(path2,data);
        //zk.createNode(path, data);
        zk.setNodeData(path2,data,-1);
    }

    @Test
    public void testCreateChildrens(){
        String path = "/config";
        Random random = new Random();
        for (int i=1;i<10;i++){
           // zk.createNode(path.concat("/AAA-" + i), String.valueOf(i));
            zk.deleteNode(path.concat("/AAA-" + i),-1);
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 监听器一次一监听，每次都需要注册
     */
    @Test
    public void  testWatcher() throws InterruptedException {
        Random random = new Random();
        String path = "/config";

        while (true){
            String data = String.valueOf(random.nextInt(100));
            zk.setNodeData(path,data,-1);
            System.out.println(String.format("=====Write %s as %s",path,data));
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }
    public static void main(String[] args) {

    }
}
