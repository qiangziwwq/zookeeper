package com.steer.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/11 9:26
 */
public class ConfigWatcher implements Watcher {

    private ReadWriteZookeeper zk;
    public static final Charset CHARSET = Charset.forName("UTF-8");
    public static final String ROOT_CONFIG_PATH = "/config";
    public static final String HOSTS = "127.0.0.1:2181";

    public ConfigWatcher(String hosts) {

        this.zk = new ReadWriteZookeeper();
        try {
            zk.connection(hosts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     */
    public void displayConfig(){
        Stat stat = new Stat();
        String data = zk.readData(ROOT_CONFIG_PATH,this,stat);
        System.out.printf("Read %s as %s\n",ROOT_CONFIG_PATH,data);
    }
    @Override
    public void process(WatchedEvent event) {

        if (event.getType()== Event.EventType.NodeDataChanged) {
            displayConfig();
        }else if(event.getType()== Event.EventType.NodeChildrenChanged){
            displayConfig();
            System.out.printf("=====Read %s as %s\n",ROOT_CONFIG_PATH,"children update");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigWatcher configWatcher = new ConfigWatcher(HOSTS);
        //第一次注册监听
        configWatcher.displayConfig();
        Thread.sleep(Long.MAX_VALUE);
    }
}
