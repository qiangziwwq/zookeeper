package com.steer.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/10 22:18
 */
public class ReadWriteZookeeper extends ConnectWatcher{

    public static final String HOSTS = "127.0.0.1:2181";

    public static final Charset CHARSET = Charset.forName("UTF-8");


    /**
     * 创建节点
     * @param path
     * @param data
     * @return
     */
    public String createNode(String path , String data){
        String result = "";
        try {
            result=zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            String format = String.format("crete node success %s", result);
            System.out.println(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    public String getNodeData(String path){
        String result = null;
        Stat stat = new Stat();
        try {
            byte[] data = zk.getData(path, true, stat);
            result=new String(data,CHARSET);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断节点是否存在
     * @param path
     * @param watch
     */
    public void  existsNode(String path,boolean watch){
        try {
            Stat stat = zk.exists(path, watch);
            System.out.println(String.format("======= znode  version is : %d ========",stat.getVersion()));
            System.out.println(String.format("======= znode  children num  is : %d ========",stat.getNumChildren()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 更新节点数据
     * @param path
     * @param data
     * @param version
     */
    public void setNodeData(String path, String data , int version){
        Stat stat;
        try {
            stat = zk.setData(path, data.getBytes(), version);
            System.out.println(String.format("======update znode success and version is :%s======",stat.getVersion()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点(当前节点有子节点时不能删除)
     * @param path
     * @param version
     */
    public void deleteNode(String path,int version){
        try {
            zk.delete(path,version);
            System.out.println(String.format("====delete znode success %s====",path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子节点
     * @param path
     * @param watch
     * @param stat
     * @return
     */
    public List<String>getChildrens(String path,boolean watch ,Stat stat){
        List<String> children = null;
        try {
             children = zk.getChildren(path, watch, stat);
            for (String s : children) {
                System.out.println(String.format("====znode:%s====",s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    /**
     * 读取数据
     * @param path
     * @param watcher
     * @param stat
     * @return
     */
    public String readData(String path, Watcher watcher,Stat  stat){
        byte[] data = new byte[0];
        try {
            data = zk.getData(path, watcher, stat);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new String(data, CHARSET);
    }

    /**
     * 有监听器的获取子节点
     * @param path
     * @param watcher
     * @return
     */
    public List<String> getChildrenByWatch(String path,Watcher watcher) {
        List<String> children=null;
        try {
            children= zk.getChildren(path, watcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return children;
    }

    /**
     *
     * @param path
     * @return
     */
    public String getNodeDataByWatch(String path,Watcher watcher) {
        String result = "";
        try {
            byte[] data = zk.getData(path, watcher, null);
            result = new String(data, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}
