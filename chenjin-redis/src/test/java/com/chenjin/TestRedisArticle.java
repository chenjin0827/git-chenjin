package com.chenjin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chenjin.cache.service.RedisArticleService;
import org.junit.runners.MethodSorters;

/**
* Redis业务测试用例
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRedisArticle {

	@Resource(name = "redisArticleServiceImpl")
	private RedisArticleService redisArticleService;

	/**
	* 测试用例：用户发布文章
	*/
	@Test
	public void apostArticle() {
		 
	}
	
	
	
	
	/**
	* 测试用例：用户对文章投票
	*/
	@Test
	public void barticleVote(){
		
	}
	
	
	
	
	/**
	* 测试用例：获取文章列表并打印出来
	*/
	@Test
	public void cgetArticles(){
		
	}
}