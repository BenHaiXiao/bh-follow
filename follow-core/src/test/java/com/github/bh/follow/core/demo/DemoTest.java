package com.github.bh.follow.core.demo;

import com.github.bh.follow.FacadeSdk;
import com.github.bh.follow.mo.FollowerInfo;
import com.github.bh.follow.mo.LeaderInfo;
import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.conf.Cluster;
import com.hyd.ssdb.conf.Server;
import com.hyd.ssdb.conf.SocketConfig;
import com.hyd.ssdb.sharding.ConsistentHashSharding;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaobenhai
 * @date 2018-04-01 21:33
 */
public class DemoTest {
    private FacadeSdk facadeSdk;

    String appId = "bh";
    Long leader = 1234556L;
    Long follower = 654321L;
    List<Long> followers = Arrays.asList(follower);
    List<Long> leaders = Arrays.asList(leader);

    @Before
    public void before(){
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setSoBufferSize(100 * 1024); //设置buffer size
        socketConfig.setSoTimeout(5000); //设置超时时间

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setMaxTotal(500);
        poolConfig.setMinIdle(20);
        poolConfig.setMaxIdle(200);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(false);
        poolConfig.setJmxNamePrefix("ssdb-");


        List<Server> servers1 = Arrays.asList(
                new Server("127.0.0.1",8881,"false",true,socketConfig,poolConfig),
                new Server("127.0.0.1",8882,"false",false,socketConfig,poolConfig)
        );
        List<Server> servers2 = Arrays.asList(
                new Server("127.0.0.1",8883,"false",true,socketConfig,poolConfig),
                new Server("127.0.0.1",8884,"false",true,socketConfig,poolConfig)
        );

        SsdbClient ssdbClient = new SsdbClient(new ConsistentHashSharding(Arrays.asList(
                Cluster.fromServers(servers1),
                Cluster.fromServers(servers2)
        )));
        this.facadeSdk = new FacadeSdk(ssdbClient);
    }
//    @Before
//    public void  before(){
//        SsdbClient ssdbClient = new SsdbClient("172.27.137.12",8888);
//        this.facadeSdk = new FacadeSdk(ssdbClient);
//    }

   @Test
    public void follow() {
       facadeSdk.follow(appId,leader,follower);
    }
    @Test
    public void batchFollow() {
       facadeSdk.batchFollow(appId,leader,followers);
    }
    @Test
    public void unFollow() {
        facadeSdk.unFollow(appId,leader,follower);
    }

    @Test
    public void unBatchFollow1() {
        facadeSdk.unBatchFollow(appId,leaders,follower);
    }

    @Test
    public void unBatchFollow2() {
        facadeSdk.unBatchFollow(appId,leader,followers);
    }
    @Test
    public void listFollerByTimeDesc(){
        List<LeaderInfo> bo = facadeSdk.listFollerByTimeDesc(appId,follower,1,10000);
        System.out.println(bo);
    }

    @Test
    public void listFollerByTimeAes(){
        List<LeaderInfo> bo = facadeSdk.listFollerByTimeAes(appId,follower,1,10000);
        System.out.println(bo);
    }

    @Test
    public void listLeaderByTimeDesc(){
        List<LeaderInfo> bo = facadeSdk.listFollerByTimeDesc(appId,follower,1,10000);
        System.out.println(bo);
    }

    @Test
    public void  listLeaderByTimeAes(){
        List<FollowerInfo> bo = facadeSdk.listLeaderByTimeDesc(appId,leader,1,10000);
        System.out.println(bo);
    }
    @Test
    public void sizeFollower(){
       int bo = facadeSdk.sizeFollower(appId,follower);
        System.out.println(bo);
    }
    @Test
    public void sizeLeader(){
       int bo = facadeSdk.sizeLeader(appId,leader);
        System.out.println(bo);
    }



}
