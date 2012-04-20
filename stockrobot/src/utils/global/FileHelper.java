package utils.global;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
	public static List<File> getFiles(File path) {
		List<File> files = new ArrayList<File>();
		File[] entries = path.listFiles();

		if (entries != null) {
			for (File f : entries) {
				if (!f.isDirectory())
					files.add(f);
			}
		}

		return files;
	}
}
