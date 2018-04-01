package com.github.bh.follow;

import com.github.bh.follow.constants.Constant;
import com.github.bh.follow.mo.FollowerInfo;
import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.util.IdScore;
import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaobenhai
 * @date 2018-04-01 20:02
 */
public class FollowSdk {
	protected SsdbClient ssdbClient;

	public FollowSdk(SsdbClient ssdbClient) {
		this.ssdbClient = ssdbClient;
	}
	/**
	 * list队列头部插入最新数据
	 * @param appId
	 * @param leader
	 * @param follower
	 * @return
	 */
	public void add(String appId, Long leader, Long follower) {
		String key = Constant.genLeaderKey(appId,leader);
		ssdbClient.zset(key,String.valueOf(follower),System.currentTimeMillis());
	}

	/**
	 * 批量添加关注
	 * @param appId
	 * @param leader
	 * @param followers
	 * @return
	 */
	public void addBatch(String appId, Long leader, List<Long> followers) {
		String key = Constant.genLeaderKey(appId,leader);
		List<IdScore> idScores = new ArrayList<IdScore>();
		for (Long follow : followers){
			IdScore idScore = new IdScore(String.valueOf(follow),System.currentTimeMillis());
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
	public void remove(String appId, Long leader, Long follower) {
		String key = Constant.genLeaderKey(appId,leader);
		ssdbClient.zdel( key, String.valueOf(follower));
	}

	/**
	 * 批量移除关注
	 * @param appId
	 * @param leader
	 * @param followers
	 */
	public void removeBatch(String appId, Long leader, List<Long> followers) {
		String key = Constant.genLeaderKey(appId,leader);
		List<String> ids = new ArrayList<String>();
		for (Long follow : followers){
			ids.add(String.valueOf(follow));
		}
		ssdbClient.multiZdel( key, ids);
	}

	/**
	 * 时间降序
	 * @param appId
	 * @param leader
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<FollowerInfo> listByTimeDesc(@NotNull String appId,@NotNull Long leader,@NotNull Integer pageNum,@NotNull Integer pageSize){
		String key = Constant.genLeaderKey(appId,leader);
		Integer offset = (pageNum-1)  * pageSize;
		Integer limit = pageSize;
		List<IdScore> idScores = ssdbClient.zrrange(key,offset,limit);
		return FollowerInfo.toBo(idScores);
	}

	/**
	 * 时间升序
	 * @param appId
	 * @param leader
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<FollowerInfo> listByTimeAes(@NotNull String appId,@NotNull Long leader,@NotNull Integer pageNum,@NotNull Integer pageSize){
		String key = Constant.genLeaderKey(appId,leader);
		Integer offset = (pageNum-1) * pageSize;
		Integer limit = pageSize;
		List<IdScore> idScores = ssdbClient.zrange(key,offset,limit);
		return FollowerInfo.toBo(idScores);
	}
	/**
	 * 查询关注数
	 * @param appId
	 * @param leader
	 * @return
	 */
	public int size(String appId,Long leader){
		String key = Constant.genLeaderKey(appId,leader);
		return ssdbClient.zsize(key);
	}
}
