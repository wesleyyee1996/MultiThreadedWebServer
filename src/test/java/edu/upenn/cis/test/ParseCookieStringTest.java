package edu.upenn.cis.test;

import static org.junit.Assert.assertTrue;

import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.m1.server.RequestObj;
import edu.upenn.cis.cis455.m2.interfaces.Request;

public class ParseCookieStringTest {

	Request request = new RequestObj();
	
	@Test
	public void test() {
		String cookieString = "yummy_cookie=choco; tasty_cookie=strawberry";
		Hashtable<String,String> hash = request.parseCookieString(cookieString);
		assertTrue(hash.get("yummy_cookie").equals("choco"));
		assertTrue(hash.get("tasty_cookie").equals("strawberry"));
	}
	
	@Test
	public void test1() {
		String cookieString = "PHPSESSID=298zf09hf012fh2; csrftoken=u32t4o3tb3gg43; _gat=1";
		Hashtable<String,String> hash = request.parseCookieString(cookieString);
		assertTrue(hash.get("PHPSESSID").equals("298zf09hf012fh2"));
		assertTrue(hash.get("csrftoken").equals("u32t4o3tb3gg43"));
		assertTrue(hash.get("_gat").equals("1"));
	}
	
	
	
	
}
