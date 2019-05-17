package Doc2Calendar.v01;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the main class. The user has to input document and path to create ICS
 * file. All methods will be called sequentially.
 * 
 * @author saiven
 *
 */

public class MainClass {

	public static void main(String[] args) {

		String document_to_convert = "files\\Webinar.docx"; // C:\\Document.docx
		String ics_to_create = "files\\Out.ics"; // C:\\Users\\Desktop\\Out.ics
		ArrayList<String> doclist = DocxParser.DocReader(document_to_convert);
		StringBuffer icsbuild = ExtractDetails.EventsGetter(doclist);

		try {
			ICScreator.ICSbuild(icsbuild, ics_to_create);
			System.out.println("Successfully generated.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Creation failed.");
		}

	}

}
