package com.yang.hdyplm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.dto.MessageDTO;
import com.yang.hdyplm.mapper.MessageMapper;
import com.yang.hdyplm.pojo.Activity;
import com.yang.hdyplm.pojo.Message;
import com.yang.hdyplm.service.MessageService;
import com.yang.hdyplm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void sendMsg(MessageDTO messageDTO) {
        Message message=new Message(messageDTO.getMessageId(),messageDTO.getMessageContent(),messageDTO.getMessageTime(), BaseContext.getCurrentId().toString());
        redisCache.setCacheList("lastMsg",message); //存入redis
        messageMapper.save(message);
    }

    @Override
    public PageInfo<MessageDTO> getMsg(Integer pageNo) {
        PageHelper.startPage(pageNo, 6);
        List<MessageDTO> msg = messageMapper.getMsg();
        PageInfo pageInfo=new PageInfo(msg);
        return pageInfo;
    }

    @Override
    public void delMsg(Integer messageId) {
        messageMapper.del(messageId);
    }

    @Override
    public void updateMsg(Integer messageId,String messageContent) {
        messageMapper.update(messageId,messageContent);
    }
}
