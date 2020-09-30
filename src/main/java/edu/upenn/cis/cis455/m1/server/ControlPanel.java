package edu.upenn.cis.cis455.m1.server;

import java.util.ArrayList;

public class ControlPanel {
	
	private static ArrayList<Thread> threadPool;

	public byte[] getControlPanel(ArrayList<Thread> threadPool) {
		this.threadPool = threadPool;
		String controlPanelBody = constructControlPanelBody();
		return controlPanelBody.getBytes();
	}
	
	private String constructControlPanelBody() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"    <title>Sample File</title>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"<h1>Welcome</h1>\n" + 
				"<ul>");
		buffer.append(constructThreadList());
		buffer.append("<li><a href=\"/shutdown\">Shut down</a></li>\n" + 
				"</ul>\n" + 
				"</body>\n" + 
				"</html>");
		return buffer.toString();
	}
	
	private String constructThreadList() {
		StringBuffer buffer = new StringBuffer();
		for(int thread = 0; thread<threadPool.size();thread++) {
			Thread workerThread = threadPool.get(thread);
			buffer.append("<li>"+workerThread.getName()+"</li>");
		}
		return buffer.toString();
	}
}
