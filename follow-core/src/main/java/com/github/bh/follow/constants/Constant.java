package com.github.bh.follow.constants;

/**
 * @author xiaobenhai
 * @date 2018-04-01 19:31
 */
public class Constant {
    //分隔符
    public final static String SEPARATOR= ":";
    public final static String FOLLER= "follow";
    public final static String LEADER= "leader";
    //被关注者 key
    public static String genLeaderKey(String appId,Long leader){
        //组成 appid + 角色 + uid
        return appId + SEPARATOR +LEADER + SEPARATOR + leader;
    }
    //关注者 key
    public  static String genFollowKey(String appId,Long follow){
        //组成 appid + 角色 + uid
        return appId + SEPARATOR + FOLLER + SEPARATOR + follow;
    }
}
