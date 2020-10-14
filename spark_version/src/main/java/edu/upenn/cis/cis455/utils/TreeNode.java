package edu.upenn.cis.cis455.utils;

import java.util.ArrayList;

public class TreeNode<T> {
	public int sharedPathCount = 0;
    public String value;
    public ArrayList<TreeNode<T>> children = new ArrayList<TreeNode<T>>();
    public TreeNode<T> parent;
}
