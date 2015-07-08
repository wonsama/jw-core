package kr.co.jwsnt.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwMain {

	public static void main(String[] args)
	{
		new JwMain();
	}
	
	Logger logger = LoggerFactory.getLogger(JwMain.class);
	
	public JwMain() {
		logger.info("info");
	}
}
