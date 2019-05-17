package Doc2Calendar.v01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to create the final .ics file, which can be imported by
 * any calendar application
 * 
 * @author saiven
 *
 */

public class ICScreator {

	public static void ICSbuild(StringBuffer icsbuild, String ics_to_create) throws IOException {

		File file = new File(ics_to_create);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(icsbuild.toString());
		} finally {
			if (writer != null)
				writer.close();

		}

	}

}