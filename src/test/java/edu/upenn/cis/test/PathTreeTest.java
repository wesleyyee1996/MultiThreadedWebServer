package edu.upenn.cis.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.upenn.cis.cis455.utils.PathNode;
import edu.upenn.cis.cis455.utils.PathTree;
import edu.upenn.cis.cis455.utils.TreeNode;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathTreeTest {

	PathTree pathTree = new PathTree();
	
	@Before
	public void before() {
		TreeNode<String> www = new TreeNode<String>();
		www.value = "www";
		TreeNode<String> var = new TreeNode<String>();
		var.value = "var";		
		pathTree.root.children.add(www);
		pathTree.root.children.add(var);
		
		TreeNode<String> index = pathTree.root.children.get(0);
		index.value = "index";
		TreeNode<String> abc = new TreeNode<String>();
		abc.value = "abc";
		index.children.add(abc);
	}
	
//	@Test
//	public void testExists() {
//		pathTree.checkPathExists("/www/index/abc");
//		
//	}
	
	@Test
	public void testSplitPath1() {		
		String testString = "/index/html/";
		PathNode<String> path = pathTree.splitPath(testString);
		String testerOutput = "/";
		while(path.next != null) {
			testerOutput += path.value + "/";
			path = path.next;
		}
		assertTrue(testerOutput.equals(testString));
	}
	
	
	
//	@Test
//	public void testAdd1() {
//		String testString = "/index/html/555";
//		pathTree.addPathToTree(testString);
//		while(pathTree.root.next != null) {
//			
//		}
//	}

}
