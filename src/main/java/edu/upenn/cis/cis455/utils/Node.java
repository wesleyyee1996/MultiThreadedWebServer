package edu.upenn.cis.cis455.utils;

import java.util.ArrayList;

public class Node<T> {
    String value;
    ArrayList<Node<T>> children;
    Node<T> parent;
}
