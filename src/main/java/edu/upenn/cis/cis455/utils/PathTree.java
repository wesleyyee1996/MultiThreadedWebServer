package edu.upenn.cis.cis455.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class PathTree {

	public TreeNode<String> root;
	
	public PathTree() {
		root = new TreeNode<String>();
		root.value = "/";
		root.children = new ArrayList<TreeNode<String>>();
		root.sharedPathCount += 1;
	}
	
	/**
	 * Given a path in String format that starts w/ "/", adds the path to the tree
	 * @param pathString
	 */
	public void addPathToTree(String pathString) {
		Path path = splitPath(pathString);
		int idx = 0;
		add(root, path, idx);
	}	
	
	/**
	 * Adds a path to the PathTree
	 * Assumes that both treeNode and pathNode start with "/"
	 * @param treeNode
	 * @param pathNode
	 */
	private void add(TreeNode<String> treeNode, Path path, int idx) {
		if (idx < path.getNameCount()) {
			
			boolean childExists = false;
			
			if (treeNode.value.equals("/")) {
				for (TreeNode<String> child : treeNode.children){
					if (child.value.equals(path.getName(0).toString())){
						childExists = true;
						idx++;
						add(child, path, idx);
					}
				}
			}
			
			// If the path up to current pathNode exists, then keep traversing
			// through matching path components
			else {
				for (TreeNode<String> child : treeNode.children) {
					if (child.value.equals(path.getName(idx).toString())) {
						childExists = true;
						idx++;
						add(child,path,idx);
					}
				}
			}
			
			if (childExists == false) {
				TreeNode<String> newLeaf = new TreeNode<String>();
				newLeaf.value = path.getName(idx).toString();
				newLeaf.sharedPathCount += 1;
				treeNode.children.add(newLeaf);
				idx++;
				add(newLeaf, path, idx);
			}
		}
	}
	
	/**
	 * Check if a given path exists
	 * @param treeNode
	 * @param path
	 * @return
	 */
	public boolean checkPathExists(String pathString) {
		Path path = splitPath(pathString);
		int idx = 0;
		return exists(root, path, idx);
	}
	
	/**
	 * Internal recursive method to check if a path exists
	 * @param treeNode
	 * @param pathNode
	 * @return
	 */
	private boolean exists(TreeNode<String> treeNode, Path path, int idx) {
		
		// This means we are at the end of the path and also at a leaf node
		if (treeNode.children.isEmpty() && idx < path.getNameCount()) {
			return true;
		}
		
		if (treeNode.value.equals("/")) {
			for (TreeNode<String> child : treeNode.children) {
				if (child.value.equals(path.getName(idx).toString())) {		
					return exists(child, path, idx);				
				}
			}
		}
		else {
			for (TreeNode<String> child : treeNode.children) {
				if (child.value.equals(path.getName(idx+1).toString())) {		
					idx++;
					return exists(child, path, idx);				
				}
			}
		}
		
		// If the value of the treeNode is equal to the pathNode value, then
		// loop through the treeNode's children and see if any of them are equal to the 
		// next path component's value. If so, then keep traversing through the 
		// tree and the list
			
			
		// If any part of the path doesn't match, then return false
		return false;
	}
	
	/**
	 * Splits the path and returns a LinkedList PathNode<String>
	 * @param path
	 * @return
	 */
	public Path splitPath(String path){
		return Paths.get(path);
	}
		

//	/**
//	 * Removes a specified path from the tree
//	 * @param path
//	 */
//	public void removePath(String path) {
//		PathNode<String> pathLinkedList = splitPath(path);
//		remove(root, pathLinkedList);
//	}
//	
//	/**
//	 * Internal recursive method to remove a specified path from the path tree
//	 * @param treeNode
//	 * @param pathNode
//	 */
//	private void remove(TreeNode<String> treeNode, PathNode<String> pathNode) {
//		if ()
//	}
//	
	
}
