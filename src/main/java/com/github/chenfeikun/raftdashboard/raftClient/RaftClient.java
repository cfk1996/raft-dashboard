package com.github.chenfeikun.raftdashboard.raftClient;

import com.alibaba.fastjson.JSON;
import com.github.chenfeikun.raftdashboard.entity.*;
import org.apache.rocketmq.remoting.RemotingClient;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.netty.NettyRemotingClient;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desciption: RaftClient
 * @CreateTime: 2019-04-07
 * @author: chenfeikun
 */
@Component
public class RaftClient {

    private static final Logger logger = LoggerFactory.getLogger(RaftClient.class);

    private static final String PEERS = "n0-localhost:10001;n1-localhost:10002;n2-localhost:10003";
    private static final String DEFAULT_GROUP = "default";

    private RemotingClient rpcClient;
    private Map<String, String> peerMap = new ConcurrentHashMap<>();

    public RaftClient() {
        updatePeerMap();
        this.rpcClient =  new NettyRemotingClient(new NettyClientConfig(), null);
        this.rpcClient.start();
    }

    public String getLeaderId() {
        for (String remoteId : peerMap.keySet()) {
            try {
                MetadataRequest request = new MetadataRequest();
                request.setGroup(DEFAULT_GROUP);
                request.setRemoteId(remoteId);
                MetadataResponse response = metadata(request);
                if (response != null && response.getLeaderId() != null) {
                    return response.getLeaderId();
                }
            } catch (Exception e) {
                if (e instanceof RemotingConnectException) {
                    continue;
                }
                logger.error("Unexpected error. serverId = {}",remoteId, e);
            }
        }
        logger.info("Get leaderId error. No leader found.");
        return null;
    }


    public GetEntriesResponse get(GetEntriesRequest request) throws Exception {
        RemotingCommand wrapperRequest = RemotingCommand.createRequestCommand(RequestCode.GET.getCode(), null);
        wrapperRequest.setBody(JSON.toJSONBytes(request));
        RemotingCommand wrapperResponse = this.rpcClient.invokeSync(getPeerAddr(request.getRemoteId()), wrapperRequest, 3000);
        GetEntriesResponse response = JSON.parseObject(wrapperResponse.getBody(), GetEntriesResponse.class);
        return response;
    }

    public GetEntriesResponse getSingle(GetEntriesRequest request) throws Exception {
        RemotingCommand wrapperRequest = RemotingCommand.createRequestCommand(RequestCode.GET_SINGLE.getCode(), null);
        wrapperRequest.setBody(JSON.toJSONBytes(request));
        RemotingCommand wrapperResponse = this.rpcClient.invokeSync(getPeerAddr(request.getRemoteId()), wrapperRequest, 3000);
        GetEntriesResponse response = JSON.parseObject(wrapperResponse.getBody(), GetEntriesResponse.class);
        return response;
    }

    public AppendEntryResponse append(AppendEntryRequest request) throws Exception {
        RemotingCommand wrapperRequest = RemotingCommand.createRequestCommand(RequestCode.APPEND.getCode(), null);
        wrapperRequest.setBody(JSON.toJSONBytes(request));
        RemotingCommand wrapperResponse = this.rpcClient.invokeSync(getPeerAddr(request.getRemoteId()), wrapperRequest, 3000);
        AppendEntryResponse response = JSON.parseObject(wrapperResponse.getBody(), AppendEntryResponse.class);
        return response;
    }

    private String getPeerAddr(String serverId) {
        return peerMap.get(serverId);
    }

    private void updatePeerMap() {
        for (String peerInfo : PEERS.split(";")) {
            peerMap.put(peerInfo.split("-")[0], peerInfo.split("-")[1]);
        }
    }

    public MetadataResponse metadata(MetadataRequest request) throws Exception {
        RemotingCommand wrapperRequest = RemotingCommand.createRequestCommand(RequestCode.METADATA.getCode(), null);
        wrapperRequest.setBody(JSON.toJSONBytes(request));
        RemotingCommand wrapperResponse = this.rpcClient.invokeSync(getPeerAddr(request.getRemoteId()), wrapperRequest, 3000);
        MetadataResponse response = JSON.parseObject(wrapperResponse.getBody(), MetadataResponse.class);
        return response;
    }
}
