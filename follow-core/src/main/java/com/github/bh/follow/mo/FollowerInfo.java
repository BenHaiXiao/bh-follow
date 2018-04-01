package com.github.bh.follow.mo;

import com.github.bh.follow.constants.Constant;
import com.hyd.ssdb.util.IdScore;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaobenhai
 * @date 2018-04-01 19:40
 */
public class FollowerInfo {
	private Long uid;
	private Long time;

	public FollowerInfo(Long uid, Long time) {
		this.uid = uid;
		this.time = time;
	}

	public static String genStr(Long uid){
      return uid + Constant.SEPARATOR+System.currentTimeMillis();
	}
	public static String[] genStr(List<Long> uids){
		String[] values = new String[uids.size()];
		for (int i = 0; i < uids.size() -1 ; i++){
			values[i] = genStr(uids.get(i));
		}
		return values;
	}

	public static FollowerInfo reverseStr(String str){
		String[] strs = StringUtils.split(str,Constant.SEPARATOR);
		return new FollowerInfo(Long.valueOf(strs[0]),Long.valueOf(strs[1]));
	}

	public static List<FollowerInfo> toBo(List<IdScore> idScores){
		List<FollowerInfo> followerInfos = new ArrayList<FollowerInfo>();
		if (idScores == null || idScores.size() <= 0) return followerInfos;
		for (IdScore idScore : idScores){
			FollowerInfo followerInfo = new FollowerInfo(Long.valueOf(idScore.getId()),idScore.getScore());
			followerInfos.add(followerInfo);
		}
		return followerInfos;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "FollowerInfo{" +
				"uid=" + uid +
				", time=" + time +
				'}';
	}
}