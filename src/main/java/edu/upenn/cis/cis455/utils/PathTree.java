package edu.upenn.cis.cis455.utils;

import java.util.ArrayList;
import java.util.LinkedList;

public class PathTree {

	private Node<String> root;
	
	public PathTree(String rootVal) {
		root = new Node<String>();
		root.value = rootVal;
		root.children = new ArrayList<Node<String>>();
	}
	
	public void addToTree(LinkedList<String> pathList) {
		
	}
	
	public boolean checkPathMatch(Node<String> treeNode, LinkedList<String> pathList) {
		
	}
	
	private void 
}
