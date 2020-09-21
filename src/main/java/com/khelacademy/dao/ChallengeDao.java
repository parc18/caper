package com.khelacademy.dao;

import com.khelacademy.dto.ChallengeDto;

public interface ChallengeDao {
	public void sendChallenge(ChallengeDto challengeDto) throws Exception;
	public void actionOnChallenge(ChallengeDto challengeDto) throws Exception;;
}
