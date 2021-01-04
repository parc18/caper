package com.khelacademy.document;

import java.util.List;
import java.util.Map;

public class PoolFixture {
	private Map<String, List<UserVersus>> poolNameWithVersus;

	public Map<String, List<UserVersus>> getPoolNameWithVersus() {
		return poolNameWithVersus;
	}

	public void setPoolNameWithVersus(Map<String, List<UserVersus>> poolNameWithVersus) {
		this.poolNameWithVersus = poolNameWithVersus;
	}
}
