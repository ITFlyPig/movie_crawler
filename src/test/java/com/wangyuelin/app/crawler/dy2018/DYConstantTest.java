package com.wangyuelin.app.crawler.dy2018; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* DYConstant Tester. 
* 
* @author <Authors name> 
* @since <pre>05/16/2018</pre> 
* @version 1.0 
*/ 
public class DYConstantTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getPageType(String url) 
* 
*/ 
@Test
public void testGetPageType() throws Exception { 

    String  url = "https://www.dy2018.com/";
    DYConstant.PageDYType pageDYType =  DYConstant.getPageType(url);
    System.out.println(pageDYType.getName());

} 

/** 
* 
* Method: getIndex() 
* 
*/ 
@Test
public void testGetIndex() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getName() 
* 
*/ 
@Test
public void testGetName() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRegex() 
* 
*/ 
@Test
public void testGetRegex() throws Exception { 
//TODO: Test goes here... 
} 


} 
