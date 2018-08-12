package com.zjwd.wx.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zjwd.wx.service.WXService;
import com.zjwd.wx.utils.WXCheckUtil;

@RestController
public class WXController {
	
	@Autowired
	WXService wxService;
	
	@RequestMapping(value="/wx", method=RequestMethod.GET)
	public String wxHandle(HttpServletRequest request,
			HttpServletResponse response){
		System.out.println("hello wx!");
		return("hello, this is handle view");
	}
	
	/**
     * 验证微信服务器
     * 
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void wechatService(PrintWriter out, HttpServletResponse response,
            @RequestParam(value = "signature", required = false) String signature, @RequestParam String timestamp,
            @RequestParam String nonce, @RequestParam String echostr) {
        if (WXCheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }
    
    /**
	 * 接收来自微信发来的消息
	 * 
	 * @param out
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/wechat", method = RequestMethod.POST)
	public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
		String responseMessage = wxService.processRequest(request);
		out.print(responseMessage);
		out.flush();
	}
}
