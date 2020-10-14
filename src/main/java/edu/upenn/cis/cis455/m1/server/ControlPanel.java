package edu.upenn.cis.cis455.m1.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m2.server.WebService;

public class ControlPanel {
	final static Logger logger = LogManager.getLogger(ControlPanel.class);
	
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
				"    <title>Control Panel</title>\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"<h1>Control Panel</h1>\n" + 
				"<ul>");
		buffer.append(constructThreadList());
		buffer.append("<li><a href=\"/shutdown\">Shut down</a></li>\n");
		buffer.append(constructErrorLog());
		buffer.append("</ul>\n" + 
				"</body>\n" + 
				"</html>");
				
		return buffer.toString();
	}
	
	private String constructThreadList() {
		StringBuffer buffer = new StringBuffer();
		for(int thread = 0; thread<threadPool.size();thread++) {
			Thread workerThread = threadPool.get(thread);
			buffer.append("<li>"+workerThread.getName()+" "+WebService.getInstance().threadStatuses.get(workerThread.getName())+"</li>");
		}
		return buffer.toString();
	}
	
	private String constructErrorLog() {
		StringBuffer buffer = new StringBuffer();
		Path path = Paths.get(WebService.getInstance().getRootDir(),"app.log");
		try {
			System.out.println(path);
			List<String> errorLogLines = Files.readAllLines(path);
			for (String logline : errorLogLines) {
				buffer.append("<li>"+logline+"</li>");
			}			
		} catch (IOException e) {
			logger.error("There was an issue processing the app.log file: "+e);
		}
		return buffer.toString();
	}
}
