package com.github.chenfeikun.raftdashboard.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @desciption: GetEntriesResponse
 * @CreateTime: 2019-03-18
 * @author: chenfeikun
 */
public class GetEntriesResponse extends BaseRequestOrResponse {

    private List<Entry> entries = new ArrayList<>();

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
