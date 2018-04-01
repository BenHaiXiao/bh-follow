package com.github.bh.follow.mo;

import com.github.bh.follow.constants.Constant;
import com.hyd.ssdb.util.IdScore;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaobenhai
 * @date 2018-04-01 19:45
 */
public class LeaderInfo {
	private Long uid;
	private Long time;

	public LeaderInfo(Long uid, Long time) {
		this.uid = uid;
		this.time = time;
	}
	public static String genStr(Long uid){
		return uid + Constant.SEPARATOR + System.currentTimeMillis();
	}
	public static FollowerInfo reverseStr(String str){
		String[] strs = StringUtils.split(str,Constant.SEPARATOR);
		return new FollowerInfo(Long.valueOf(strs[0]),Long.valueOf(strs[1]));
	}
	public static List<LeaderInfo> toBo(List<IdScore> idScores){
		List<LeaderInfo> leaderInfos = new ArrayList<LeaderInfo>();
		if (idScores == null || idScores.size() <= 0) return leaderInfos;
		for (IdScore idScore : idScores){
			LeaderInfo leaderInfo = new LeaderInfo(Long.valueOf(idScore.getId()),idScore.getScore());
			leaderInfos.add(leaderInfo);
		}
		return leaderInfos;
	}

	@Override
	public String toString() {
		return "LeaderInfo{" +
				"uid=" + uid +
				", time=" + time +
				'}';
	}
}