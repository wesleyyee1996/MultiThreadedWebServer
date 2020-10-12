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
		PathNode<String> pathLinkedList = splitPath(pathString);
		add(root, pathLinkedList);
	}	
	
	/**
	 * Adds a path to the PathTree
	 * Assumes that both treeNode and pathNode start with "/"
	 * @param treeNode
	 * @param pathNode
	 */
	private void add(TreeNode<String> treeNode, PathNode<String> pathNode) {
		if (pathNode != null) {
			
			// If the path up to current pathNode exists, then keep traversing
			// through matching path components
			if (treeNode.value.equals(pathNode.value)) {
				for (TreeNode<String> child : treeNode.children) {
					if (child.value.equals(pathNode.next.value)) {
						add(child, pathNode.next);
					}
				}
			}
			
			// If the path up to current pathNode doesn't exist, then keep adding until
			// you reach the end of the path
			else {
				if (pathNode.next != null) {
					TreeNode<String> newLeaf = new TreeNode<String>();
					newLeaf.value = pathNode.value;
					newLeaf.sharedPathCount += 1;
					treeNode.children.add(newLeaf);
					add(newLeaf, pathNode.next);
				}
			}
		}
	}
	
	/**
	 * Check if a given path exists
	 * @param treeNode
	 * @param path
	 * @return
	 */
	public boolean checkPathExists(String path) {
		PathNode<String> pathNode = splitPath(path);
				
		return exists(root, pathNode);
	}
	
	/**
	 * Internal recursive method to check if a path exists
	 * @param treeNode
	 * @param pathNode
	 * @return
	 */
	private boolean exists(TreeNode<String> treeNode, PathNode<String> pathNode) {
		
		// This means we are at the end of the path and also at a leaf node
		if (treeNode == null && pathNode == null) {
			return true;
		}
		
		// If the value of the treeNode is equal to the pathNode value, then
		// loop through the treeNode's children and see if any of them are equal to the 
		// next path component's value. If so, then keep traversing through the 
		// tree and the list
		if (treeNode.value.equals(pathNode.value)) {
			for (TreeNode<String> child : treeNode.children) {
				if (child.value.equals(pathNode.next.value)) {
					 return exists(child, pathNode.next);
				}
			}
		}
		
		// If any part of the path doesn't match, then return false
		return false;
	}
	
	/**
	 * Splits the path and returns a LinkedList PathNode<String>
	 * @param path
	 * @return
	 */
	public PathNode<String> splitPath(String path){
		PathNode<String> pathLinkedList = new PathNode<String>();
		Path pathPath = Paths.get(path);
		pathLinkedList.value = "/";
		
		int idx = 0;
		
		pathLinkedList.next = new PathNode<String>();
		split(idx, pathPath, pathLinkedList.next);
		
		return pathLinkedList;
	}
	
	/**
	 * Internal method to split copy the given path recursively
	 * @param idx
	 * @param path
	 * @param node
	 */
	private void split(int idx, Path path, PathNode<String> node) {
		if (idx < path.getNameCount()) {
			node.value = path.getName(idx).toString();
			idx++;
			node.next = new PathNode<String>();
			split(idx, path, node.next);
		}
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
