package com.github.chenfeikun.raftdashboard.entity;

import java.util.Map;

/**
 * @desciption: MetadataResponse
 * @CreateTime: 2019-03-18
 * @author: chenfeikun
 */
public class MetadataResponse extends BaseRequestOrResponse {

    private Map<String, String> peers;

    public Map<String, String> getPeers() {
        return peers;
    }

    public void setPeers(Map<String, String> peers) {
        this.peers = peers;
    }
}
