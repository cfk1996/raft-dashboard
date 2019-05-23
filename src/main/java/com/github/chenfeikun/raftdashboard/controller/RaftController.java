package com.github.chenfeikun.raftdashboard.controller;

import com.github.chenfeikun.raftdashboard.entity.*;
import com.github.chenfeikun.raftdashboard.http.Response;
import com.github.chenfeikun.raftdashboard.raftClient.RaftClient;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @desciption: RaftController
 * @CreateTime: 2019-04-07
 * @author: chenfeikun
 */
@RestController
public class RaftController {

    private static final Logger logger = LoggerFactory.getLogger(RaftController.class);

    private static String DEFAULT_GROUP = "default";

    @Autowired
    private RaftClient raftClient;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/five")
    public ModelAndView indexFor5Server() {
        return new ModelAndView("server5");
    }

    @PostMapping("/append")
    public Response<AppendEntryResponse> appendLog(@RequestBody Map<String, String> reqMap) {
        String data = reqMap.get("data");
        if (data == null) {
            return Response.errorResponse(400, "need param [data] in request.");
        }
        String leaderId = raftClient.getLeaderId();
        if (leaderId == null) {
            return Response.errorResponse(500, "no leader found.");
        }
        try {
            AppendEntryRequest request = new AppendEntryRequest();
            request.setGroup(DEFAULT_GROUP);
            request.setBody(data.getBytes());
            request.setRemoteId(leaderId);
            AppendEntryResponse response = raftClient.append(request);
            return Response.succResponse(response);
        } catch (Exception e) {
            logger.error("appendLog error.", e);
            return Response.errorResponse(400, "error when send append rpc.");
        }
    }

    @GetMapping(value = "/get/{serverId}")
    public Response<GetEntriesResponse> getLogByServerId(@PathVariable String serverId, @RequestParam("index") long index) {
        try {
            GetEntriesRequest request = new GetEntriesRequest();
            request.setGroup(DEFAULT_GROUP);
            request.setRemoteId(serverId);
            request.setBeginIndex(index);
            GetEntriesResponse response = raftClient.getSingle(request);
            if (response.getEntries().size() != 0) {
                response.getEntries().get(0).setData(new String(response.getEntries().get(0).getBody()));
            }
            return Response.succResponse(response);
        } catch (Exception e) {
            if (e instanceof RemotingConnectException) {
                return Response.errorResponse(400, "raft server-" + serverId + " is offline.");
            }
            logger.error("get single error. serverId = {}",serverId, e);
            return Response.errorResponse(400, "Unexcept error: " + e.getMessage());
        }

    }

    @GetMapping(value = "/get")
    public Response<GetEntriesResponse> getLogByRaft(@RequestParam("index") long index) {
        String leaderId = raftClient.getLeaderId();
        if (leaderId == null) {
            return Response.errorResponse(500, "no leader found.");
        }
        try {
            GetEntriesRequest request = new GetEntriesRequest();
            request.setGroup(DEFAULT_GROUP);
            request.setRemoteId(leaderId);
            request.setBeginIndex(index);
            GetEntriesResponse response = raftClient.get(request);
            if (response.getEntries().size() != 0) {
                response.getEntries().get(0).setData(new String(response.getEntries().get(0).getBody()));
            }
            return Response.succResponse(response);
        } catch (Exception e) {
            logger.error("Unexpected error while get by raft, leaderId = {}", leaderId, e);
            return Response.errorResponse(400, "Unexpected error.　" + e.getMessage());
        }
    }
}
