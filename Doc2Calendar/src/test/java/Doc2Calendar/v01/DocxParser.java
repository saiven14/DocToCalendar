package Doc2Calendar.v01;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * This is the DocxParser class. It uses Apache POI library to read the .docx file and
 * parse it into an ArrayList.
 * 
 * @author saiven
 *
 */

public class DocxParser {

	public static ArrayList<String> DocReader(String document) {

		try {
			FileInputStream fis = new FileInputStream(document);
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			String[] temp = extractor.getText().split("\n");
			ArrayList<String> doclist = new ArrayList<String>(Arrays.asList(temp));
			return doclist;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

}
