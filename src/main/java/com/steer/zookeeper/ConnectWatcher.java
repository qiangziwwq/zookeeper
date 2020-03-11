package com.steer.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @version 1.0
 * @Description TODO
 * @Author 19050107(卫强)
 * @Date 2020/3/10 22:08
 */
public class ConnectWatcher implements Watcher {

    private static final int TIME_OUT = 5000;
    ZooKeeper zk;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public void connection(String hosts ) throws IOException, InterruptedException {
        zk=new ZooKeeper(hosts , TIME_OUT, this);
        countDownLatch.await();
    }
    @Override
    public void process(WatchedEvent event) {

        if (event.getState()== Event.KeeperState.SyncConnected) {
            System.out.println("========connection success !=========");
            countDownLatch.countDown();
        }
    }

    public void  closeZookeeper() throws InterruptedException {
        zk.close();
    }

}
