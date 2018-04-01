package com.github.bh.follow;

import com.github.bh.follow.mo.FollowerInfo;
import com.github.bh.follow.mo.LeaderInfo;
import com.hyd.ssdb.SsdbClient;

import java.util.List;

/**
 * @author xiaobenhai
 * @date 2018-04-01 20:48
 */
public class FacadeSdk {
    private FollowSdk followSdk;
    private LeaderSdk leaderSdk;

    public FacadeSdk(SsdbClient ssdbClient) {
        this.followSdk = new FollowSdk(ssdbClient);
        this.leaderSdk = new LeaderSdk(ssdbClient);
    }
    public FacadeSdk(FollowSdk followSdk, LeaderSdk leaderSdk) {
        this.followSdk = followSdk;
        this.leaderSdk = leaderSdk;
    }



    /**
     * 用户关注
     * @param appId
     * @param leader
     * @param follower
     */
    public void follow(String appId, Long leader, Long follower) {
       followSdk.add(appId,leader,follower);
       leaderSdk.add(appId,follower,leader);
    }

    /**
     * 用户批量关注
     * @param appId
     * @param leader
     * @param followers
     * @return
     */
    public void batchFollow(String appId, Long leader, List<Long> followers) {
        followSdk.addBatch(appId,leader,followers);
        for (Long follow : followers){
            leaderSdk.add(appId,follow,leader);
        }
    }

    /**
     * 用户批量关注
     * @param appId
     * @param leaders
     * @param follower
     */
    public void batchFollow(String appId, List<Long> leaders, Long follower) {
        for (Long leader : leaders){
            followSdk.add(appId,leader,follower);
        }
        leaderSdk.addBatch(appId,follower,leaders);
    }

    /**
     * 取消关注
     * @param appId
     * @param leader
     * @param follower
     */
    public void unFollow(String appId, Long leader, Long follower) {
        followSdk.remove(appId,leader,follower);
        leaderSdk.remove(appId,follower,leader);
    }

    /**
     * 批量取消关注
     * @param appId
     * @param leaders
     * @param follower
     */
    public void unBatchFollow(String appId, List<Long> leaders, Long follower) {
        for (Long leader : leaders){
            followSdk.remove(appId,leader,follower);
        }
        leaderSdk.removeBatch(appId,follower,leaders);
    }

    /**
     * 批量添加关注
     * @param appId
     * @param leader
     * @param followers
     */
    public void unBatchFollow(String appId, Long leader, List<Long> followers) {
        followSdk.removeBatch(appId,leader,followers);
        for (Long follow : followers){
            leaderSdk.remove(appId,leader,follow);
        }

    }


    /**
     * 查询用户关注列表时间降序
     * @param appId
     * @param leader
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<LeaderInfo> listFollerByTimeDesc( String appId,  Long leader,  Integer pageNum,  Integer pageSize){
        return leaderSdk.listByTimeAes(appId,leader,pageNum,pageSize);
    }

    /**
     * 查询follower关注列表，时间升序
     * @param appId
     * @param follower
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<LeaderInfo> listFollerByTimeAes(String appId, Long follower, Integer pageNum, Integer pageSize){
        return leaderSdk.listByTimeAes(appId,follower,pageNum,pageSize);
    }

    /**
     * 查询用户关注列表时间降序
     * @param appId
     * @param leader
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<FollowerInfo> listLeaderByTimeDesc( String appId,  Long leader,  Integer pageNum,  Integer pageSize){
        return followSdk.listByTimeAes(appId,leader,pageNum,pageSize);
    }

    /**
     * 时间升序
     * @param appId
     * @param leader
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<FollowerInfo> listLeaderByTimeAes( String appId,Long leader,Integer pageNum,Integer pageSize){
        return followSdk.listByTimeAes(appId,leader,pageNum,pageSize);
    }
    /**
     * 查询关注数
     * @param appId
     * @param follower
     * @return
     */
    public int sizeFollower(String appId,Long follower){
        return leaderSdk.size(appId,follower);
    }
    /**
     * 查询关注数
     * @param appId
     * @param leader
     * @return
     */
    public int sizeLeader(String appId,Long leader){
        return followSdk.size(appId,leader);
    }


}
