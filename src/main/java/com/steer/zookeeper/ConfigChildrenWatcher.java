package com.steer.zookeeper;

import org.apache.zookeeper.WatchedEvent;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/11 18:11
 */
public class ConfigChildrenWatcher extends AbstractWatcher  {


    public ConfigChildrenWatcher(String hosts) {
        super(hosts);
    }

    @Override
    public void process(WatchedEvent event) {
        // 事件类型，状态，和检测的路径
        Event.EventType eventType = event.getType();
        Event.KeeperState state = event.getState();
        String watchPath = event.getPath();
        if(eventType== Event.EventType.NodeChildrenChanged){
            System.out.println(String.format("=====znode children event %s",watchPath));
            this.handleChildren(watchPath,this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigChildrenWatcher childrenWatcher = new ConfigChildrenWatcher("127.0.0.1:2181");
        childrenWatcher.handleChildren("/config",childrenWatcher);
        //childrenWatcher.handleNodeData("/config/app2",childrenWatcher);
        Thread.sleep(Long.MAX_VALUE);
    }
}
