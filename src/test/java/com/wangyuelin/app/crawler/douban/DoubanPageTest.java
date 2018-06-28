package com.wangyuelin.app.crawler.douban; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* DoubanPage Tester. 
* 
* @author <Authors name> 
* @since <pre>05/30/2018</pre> 
* @version 1.0 
*/ 
public class DoubanPageTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: parse(Page page) 
* 
*/ 
@Test
public void testParse() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: seacherMovie(String name, Page page) 
* 
*/ 
@Test
public void testSeacherMovie() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: searchMovieId(final String name) 
* 
*/ 
@Test
public void testSearchMovieId() throws Exception { 
//TODO: Test goes here...
    DoubanPage doubanPage = new DoubanPage();
    System.out.println("开始");
    doubanPage.searchMovieId("超时空同居");
} 

/** 
* 
* Method: getMovieById(final String id) 
* 
*/ 
@Test
public void testGetMovieById() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: parseList(Page page) 
* 
*/ 
@Test
public void testParseList() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = DoubanPage.getClass().getMethod("parseList", Page.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
