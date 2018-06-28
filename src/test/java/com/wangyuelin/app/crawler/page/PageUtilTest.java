package com.wangyuelin.app.crawler.page; 

import com.wangyuelin.app.crawler.movie.PageUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* PageUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>05/09/2018</pre> 
* @version 1.0 
*/ 
public class PageUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getMovieType(String url) 
* 
*/ 
@Test
public void testGetMovieType() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getOne(Document doc, String select) 
* 
*/ 
@Test
public void testGetOneForDocSelect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getOne(Element element, String select) 
* 
*/ 
@Test
public void testGetOneForElementSelect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isDetail(String url) 
* 
*/ 
@Test
public void testIsDetail() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: pageType(String url) 
* 
*/ 
@Test
public void testPageType() throws Exception { 
//TODO: Test goes here...
    String url = "http://www.loldyttw.net/Juqingpian/33871.html";
    PageUtil.PageType pageType = PageUtil.pageType(url);
    System.out.println(pageType.ordinal());
} 

/** 
* 
* Method: getUrl(String url) 
* 
*/ 
@Test
public void testGetUrl() throws Exception { 
//TODO: Test goes here... 
} 


} 
