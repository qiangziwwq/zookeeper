package com.steer.zookeeper;

import org.apache.zookeeper.WatchedEvent;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/11 17:37
 */
public class ConfigWatcherData extends AbstractWatcher {

    /**
     * 连接zk
     * @param hosts
     */
    public ConfigWatcherData(String hosts) {
        super(hosts);
    }

    @Override
    public void process(WatchedEvent event) {
        // 事件类型，状态，和检测的路径
        Event.EventType eventType = event.getType();
        Event.KeeperState state = event.getState();
        String watchPath = event.getPath();
        switch (eventType){
            //创建事件
            case NodeCreated: {
                System.out.println(String.format("=====znode create event %s",watchPath));
                this.handleNodeData(watchPath,this);
                break;
            }
            //删除事件
            case NodeDeleted: {
                System.out.println(String.format("=====znode delete event %s",watchPath));
                zk.existsNode(watchPath,false);
                break;
            }
            //更改事件
            case NodeDataChanged:{
                System.out.println(String.format("=====znode delete event %s",watchPath));
                this.handleNodeData(watchPath,this);
                break;
            }
            default: {
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigWatcherData watcherData = new ConfigWatcherData("127.0.0.1:2181");
        watcherData.handleNodeData("/config",watcherData);
        Thread.sleep(Long.MAX_VALUE);
    }

}
