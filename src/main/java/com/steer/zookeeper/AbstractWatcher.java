package com.steer.zookeeper;

import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.util.List;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/11 18:11
 */
public abstract class AbstractWatcher implements Watcher {

    ReadWriteZookeeper zk;

    public static final String ROOT_CONFIG_PATH = "/config";
    public static final String HOSTS = "127.0.0.1:2181";

    public AbstractWatcher(String hosts) {
        this.zk = new ReadWriteZookeeper();
        try {
            zk.connection(hosts);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path
     */
    public void handleNodeData(String path,Watcher watcher) {
        String value = zk.getNodeDataByWatch(path, watcher);
        System.out.printf("Read %s as %s\n",ROOT_CONFIG_PATH,value);
    }

    /**
     * 监听子节点
     * @param path
     */
    public void handleChildren(String path,Watcher watcher) {
        //再监听该子节点
        List<String> Children = zk.getChildrenByWatch(path,watcher);
        for (String a : Children) {
            String childrenpath = path + "/" + a;
            String recString = zk.getNodeData(childrenpath);
            System.out.println("receive the path:" + childrenpath + ":data:"+ recString);
        }
    }
}
