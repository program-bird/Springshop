package com.taotao.controller;

//首页跳转
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 打开首页
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}	
	/**
	 * 打开其他功能页
	 */
	@RequestMapping("/{page}")
	public String showpage( String page) {
		return page;
	}

}
