package edu.upenn.cis.test;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.upenn.cis.cis455.utils.PathNode;
import edu.upenn.cis.cis455.utils.PathTree;
import edu.upenn.cis.cis455.utils.TreeNode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathTreeTest {

	PathTree pathTree = new PathTree();
	
	@Before
	public void before() {
		TreeNode<String> www = new TreeNode<String>();		
		TreeNode<String> var = new TreeNode<String>();
		TreeNode<String> index = new TreeNode<String>();
		TreeNode<String> abc = new TreeNode<String>();		
		
		abc.value = "abc";
		www.value = "www";
		var.value = "var";
		index.value = "index";	
		
		index.children.add(abc);
		www.children.add(index);
		pathTree.root.children.add(www);
		pathTree.root.children.add(var);		
	}
	
	@Test
	public void testExists() {	
		String test = "/www/index/abc";
		Path path = Paths.get(test);
		System.out.println(path.getName(0));
		System.out.println(path.getName(1));
		System.out.println(path.getName(2));
		assertTrue(pathTree.checkPathExists(test));
	}
	
//	@Test
//	public void testSplitPath1() {		
//		String testString = "/index/html/";
//		Path path = pathTree.splitPath(testString);
//		assertTrue(path.toString().equals(testString));
//	}
	
	
	
	@Test
	public void testAdd1() {
		String testString = "/www/index/html/555";
		pathTree.addPathToTree(testString);
		assertTrue(pathTree.checkPathExists(testString));
	}

}
