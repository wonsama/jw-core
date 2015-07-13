package kr.co.jwsnt.core;

import java.io.IOException;

import kr.co.jwsnt.core.parser.JwWordReaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwMain {

	private Logger logger = LoggerFactory.getLogger(JwMain.class);
	
	public static void main(String[] args)
	{
		new JwMain();
	}
	
	public JwMain() {
		
		String PATH = "/Users/parkwon/Desktop/mans/";
		String pathname = PATH + "정우snt_방카슈랑스_김종혁_중급.doc";
		String[] words = {"개발"};
		
		try {
			JwWordReaderUtil.isIncludeWords(pathname, words);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
