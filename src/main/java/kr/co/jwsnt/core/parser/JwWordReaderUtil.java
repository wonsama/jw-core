package kr.co.jwsnt.core.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ms word 유틸
 * @author parkwon
 * @since 2015.07.08
 */
public class JwWordReaderUtil {

	/**
	 * 로거
	 */
	private static Logger logger = LoggerFactory.getLogger(JwWordReaderUtil.class);

	/**
	 * 읽어들인 word 파일이 특정 단어를 모두 포함하는지 여부를 파악한다
	 * @param pathname 파일 경로 
	 * @param words 검색 단어 
	 * @return 처리 결과 
	 * @throws IOException 오류
	 * @since 2015.07.08
	 */
	public static Map<String, Integer> isIncludeWords(String pathname, final String[] words) throws IOException
	{	
		//read stream
		InputStream is = readStream(pathname);
		
		//check file extension
		String ext = FilenameUtils.getExtension(pathname);
		
		//process job
		String message = null;
		if( ext.equalsIgnoreCase("doc") ){
			//doc
			message = getTextDoc(is);
		}else if( ext.equalsIgnoreCase("docx") ){
			//docx
			message = getTextDocx(is);
		}else{
			return null;
		}
		
		//map init
		Map<String, Integer> map = new HashMap<String, Integer>();
		for( String w : words ){
			map.put(w, 0);
		}
		
		//check match words count
		if( message != null ){
			for( String w : words ){
				if( message.indexOf(w.toUpperCase())>=0){
					map.put(w, map.get(w) + 1);
				}
			}
		}
		
		return map;
	}
	
	
	/**
	 * <pre>
	 * 파일을 읽어 들여 stream을 얻어온다.
	 * 파일이 존재하지 않는 경우 null을 반환한다
	 * </pre>
	 * @param pathname 파일 경로 
	 * @return stream 정보
	 * @throws FileNotFoundException 오류 : 파일 못찾음
	 * @since 2015.07.09
	 */
	private static InputStream readStream(String pathname) throws FileNotFoundException{
		//read file
		File file = new File(pathname);
		if( !file.exists() ){
			logger.error("file not found : " + pathname);
			return null;
		}
		
		InputStream is  = new FileInputStream(file);
		return is;
	}
	
	/**
	 * word doc 파일을 읽어들인다  
	 * @param is 스트림 
	 * @return 읽어들인 문자열 
	 * @throws IOException 오류
	 * @since 2015.07.09
	 */
	private static String getTextDoc(InputStream is) throws IOException{
		HWPFDocument document = new HWPFDocument(is);
		WordExtractor extractor = new WordExtractor(document);
		String message = extractor.getText();
		extractor.close();
		
		return message;
	}
	
	
	/**
	 * word의 docx 파일을 읽어들인다  
	 * @param is 스트림 
	 * @return 읽어들인 문자열 
	 * @throws IOException 오류
	 * @since 2015.07.09
	 */
	private static String getTextDocx(InputStream is) throws IOException{
		XWPFDocument document = new XWPFDocument(is);
		XWPFWordExtractor docxExtractor = new XWPFWordExtractor(document);
		String message = docxExtractor.getText();
		docxExtractor.close();
		return message;
	}
	
}
