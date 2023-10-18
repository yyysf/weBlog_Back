package com.yang.hdyplm.service;

import com.github.pagehelper.PageInfo;
import com.yang.hdyplm.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MessageService {
    void sendMsg(MessageDTO messageDTO);

    PageInfo<MessageDTO> getMsg(Integer pageNo);

    void delMsg(Integer messageId);

    void updateMsg(Integer messageId,String messageContent);
}
