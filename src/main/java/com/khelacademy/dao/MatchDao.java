package com.khelacademy.dao;

import com.khelacademy.dto.MatchDto;
import com.khelacademy.model.Match;

public interface MatchDao {
	public Match startMatch(MatchDto matchDto) throws Exception;
	public Match updateScore(MatchDto matchDto) throws Exception;
	public Match concludeMatch(MatchDto matchDto) throws Exception;
}
