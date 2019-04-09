package com.github.chenfeikun.raftdashboard.entity;

/**
 * @desciption: AppendEntryRequest
 * @CreateTime: 2019-03-18
 * @author: chenfeikun
 */
public class AppendEntryRequest extends BaseRequestOrResponse {

    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
