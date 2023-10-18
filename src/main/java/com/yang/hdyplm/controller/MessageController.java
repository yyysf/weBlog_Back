package com.yang.hdyplm.controller;

import com.github.pagehelper.PageInfo;
import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.dto.MessageDTO;
import com.yang.hdyplm.pojo.Activity;
import com.yang.hdyplm.pojo.Message;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.MessageService;
import com.yang.hdyplm.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hdyplm/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisCache redisCache;
    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody MessageDTO messageDTO){
       messageService.sendMsg(messageDTO);
       return Result.success();
    }
    @GetMapping ("/getMsg")
    public Result getMsg(Integer pageNo){
        PageInfo<MessageDTO> msg = messageService.getMsg(pageNo);
        Map data=new HashMap();
        data.put("rows",msg.getList());
        data.put("pageSize",msg.getPageSize());
        data.put("total",msg.getTotal());
        data.put("userId", BaseContext.getCurrentId());
        List<Message> lastMsg = redisCache.getCacheList("lastMsg");
        if (lastMsg.size()!=0){
            Message msg1 =(Message) redisCache.getListRrange("lastMsg");
            redisCache.setCacheList("lastMsg",msg1);
            data.put("lastMsg",msg1);
        }
        System.out.println(msg);
        return Result.success(data);
    }
    @DeleteMapping ("/delMsg/{id}")
    public Result delMsg(@PathVariable Integer id ){
      messageService.delMsg(id);
      return Result.success();
    }
    @PutMapping ("/updateMessage/{id}")
    public Result updateMsg(@PathVariable("id") Integer messageId,@RequestBody String messageContent){
        messageService.updateMsg(messageId,messageContent);
        return Result.success();
    }
}
