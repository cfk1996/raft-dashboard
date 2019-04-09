package com.github.chenfeikun.raftdashboard.entity;

/**
 * @desciption: AppendEntryResponse
 * @CreateTime: 2019-03-18
 * @author: chenfeikun
 */
public class AppendEntryResponse extends BaseRequestOrResponse {

    private long index = -1;
    private long pos = -1;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }
}
