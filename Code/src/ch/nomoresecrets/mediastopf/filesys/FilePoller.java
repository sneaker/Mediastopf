package ch.nomoresecrets.mediastopf.filesys;
//test
import java.io.File;
import java.io.IOException;

class FilePoller extends Thread {
	public void run() {
		File file = new File("/tmp");
		File[] lastList = null;
		lastList = file.listFiles();
		for (int i = 0; i < 10; i++) {
			try {
				sleep(2000);

				if (sameFiles(lastList, file.listFiles()))
					System.out.println("samesize");
				else
					System.out.println("NNNNNNNEEEEEEEEEEUUUUUUUU");
				lastList = file.listFiles();
				if (i == 2)
					makeFilesystemChange();

			} catch (InterruptedException e) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Polling");
		}

	}

	private void makeFilesystemChange() throws IOException {
		File f = new File("/tmp/testjlkjx");
		if (f.exists())
			f.delete();
		else
			f.createNewFile();
	}

	private boolean sameFiles(File[] a, File[] b) {
		for (int i = 0; i < a.length; i++) {
			if (!a[i].getName().equals(b[i].getName()))
				return false;
		}
		return true;
	}

}
