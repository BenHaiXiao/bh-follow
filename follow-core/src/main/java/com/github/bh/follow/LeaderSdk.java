/**
 * 
 */
package com.github.bh.follow;

import com.github.bh.follow.constants.Constant;
import com.github.bh.follow.mo.LeaderInfo;
import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.util.IdScore;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaobenhai
 * @date 2018-04-01 20:11
 */
public class LeaderSdk {
	protected SsdbClient ssdbClient;

	public LeaderSdk(SsdbClient ssdbClient) {
		this.ssdbClient = ssdbClient;
	}
	/**
	 * 添加关注对象
	 * @param appId
	 * @param leader
	 * @param follower
	 * @return
	 */
	public void add(String appId, Long follower,Long leader) {
		String key = Constant.genFollowKey(appId,follower);
		ssdbClient.zset(key,String.valueOf(leader),System.currentTimeMillis());
	}

    /**
     * 批量添加关注
	 * @param appId
     * @param follower
     * @param leaders
     * @return
     */
	public void addBatch(String appId, Long follower, List<Long> leaders) {
		String key = Constant.genFollowKey(appId,follower);
		List<IdScore> idScores = new ArrayList<IdScore>();
		for (Long leader : leaders){
			IdScore idScore = new IdScore(String.valueOf(leader),System.currentTimeMillis());
			idScores.add(idScore);
		}
		ssdbClient.multiZset( key,idScores);
	}

	/**
     * 移除关注
	 * @param appId
     * @param leader
     * @param follower
	 */
	public void remove(String appId, Long follower, Long leader) {
		String key = Constant.genFollowKey(appId,follower);
		ssdbClient.zdel( key, String.valueOf(leader));
	}

    /**
     * 批量移除关注
	 * @param appId
     * @param follower
     * @param leaders
	 */
	public void removeBatch(String appId, Long follower, List<Long> leaders) {
		String key = Constant.genFollowKey(appId,follower);
		List<String> ids = new ArrayList<String>();
		for (Long leader : leaders){
			ids.add(String.valueOf(leader));
		}
		ssdbClient.multiZdel( key, ids);
	}

	/**
	 * 时间降序
	 * @param appId
	 * @param follower
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<LeaderInfo> listByTimeDesc(@NotNull String appId,@NotNull Long follower,@NotNull Integer pageNum,@NotNull Integer pageSize){
		String key = Constant.genFollowKey(appId,follower);
		Integer offset = (pageNum-1)  * pageSize;
		Integer limit = pageSize;
		List<IdScore> idScores = ssdbClient.zrrange(key,offset,limit);
		return LeaderInfo.toBo(idScores);
	}

	/**
	 * 时间升序
	 * @param appId
	 * @param follower
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<LeaderInfo> listByTimeAes(@NotNull String appId, @NotNull Long follower, @NotNull Integer pageNum, @NotNull Integer pageSize){
		String key = Constant.genFollowKey(appId,follower);
		Integer offset = (pageNum-1) * pageSize;
		Integer limit = pageSize;
		List<IdScore> idScores = ssdbClient.zrange(key,offset,limit);
		return LeaderInfo.toBo(idScores);
	}
	/**
     * 查询关注数
	 * @param appId
     * @param follower
     * @return
     */
	public int size(String appId,Long follower){
		String key = Constant.genFollowKey(appId,follower);
		return ssdbClient.zsize(key);
	}
}
