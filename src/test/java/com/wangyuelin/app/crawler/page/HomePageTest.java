package com.wangyuelin.app.crawler.page; 

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* HomePage Tester. 
* 
* @author <Authors name> 
* @since <pre>05/09/2018</pre> 
* @version 1.0 
*/ 
public class HomePageTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUrl() 
* 
*/ 
@Test
public void testGetUrl() throws Exception { 
//TODO: Test goes here... 
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
* Method: parseNewMovies(Page page, PageHomeBean pageHomeBean) 
* 
*/ 
@Test
public void testParseNewMovies() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("parseNewMovies", Page.class, PageHomeBean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: fillType(String title, List<MovieBean> movies, PageHomeBean pageHomeBean)
* 
*/ 
@Test
public void testFillType() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("fillType", String.class, List<MovieBean>.class, PageHomeBean.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: fillRankType(String title, List<MovieBean> movies, PageHomeBean pageHomeBean)
* 
*/ 
@Test
public void testFillRankType() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("fillRankType", String.class, List<MovieBean>.class, PageHomeBean.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: parseRankMovies(Page page, PageHomeBean pageHomeBean) 
* 
*/ 
@Test
public void testParseRankMovies() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("parseRankMovies", Page.class, PageHomeBean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: parseLi(Element li) 
* 
*/ 
@Test
public void testParseLi() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("parseLi", Element.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/

    Element li = new Element("<li><a href=\"https://www.loldytt.com/Juqingdianying/ZG56383/\" class=\"db\" title=\"捉鬼\"><img src=\"//img.aolusb.com/im/201805/20185819303950540.jpg\" alt=\"捉鬼\"></a><strong><a href=\"https://www.loldytt.com/Juqingdianying/ZG56383/\">捉鬼</a></strong><p>剧情片</p></li>");

    HomePage homePage = new HomePage();


} 

/** 
* 
* Method: parseRecmond(Page page) 
* 
*/ 
@Test
public void testParseRecmond() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HomePage.getClass().getMethod("parseRecmond", Page.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
