package com.zjwd.wx.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zjwd.wx.entity.vo.TextMessage;
import com.zjwd.wx.utils.WXMessageUtil;

@Service
public class WXService {
	private static Logger log = Logger.getLogger(WXService.class);

	public String processRequest(HttpServletRequest request) {
		Map<String, String> map = WXMessageUtil.xmlToMap(request);
		log.info(map);
		// 发送方帐号（一个OpenID）
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");
		// 默认回复一个"success"
		String responseMessage = "success";
		// 对消息进行处理
		if (WXMessageUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setMsgType(WXMessageUtil.MESSAGE_TEXT);
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setContent("我已经收到你发来的消息了");
			responseMessage = WXMessageUtil.textMessageToXml(textMessage);
		}
		log.info(responseMessage);
		return responseMessage;

	}
}
