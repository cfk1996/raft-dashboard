package com.github.chenfeikun.raftdashboard.entity;

import java.util.List;

/**
 * @desciption: GetEntriesRequest
 * @CreateTime: 2019-03-18
 * @author: chenfeikun
 */
public class GetEntriesRequest extends BaseRequestOrResponse {

    private Long beginIndex;

    private int maxSize;

    private List<Long> indexList;

    public Long getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Long beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<Long> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<Long> indexList) {
        this.indexList = indexList;
    }
}
