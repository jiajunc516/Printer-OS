import javafx.application.Platform;
import javafx.scene.paint.Color;

class PrintJobThread extends Thread
{
	StringBuffer fileName;
	PrintJobThread(StringBuffer newFileName)
	{
		fileName = newFileName;
	}

	@Override
	public void run()
	{
		try
		{
			FileInfo file = mainOS.directoryManager.lookup(fileName);

			int printeri = mainOS.printerManager.request();
			
			Platform.runLater(() -> {
				mainOS.printerRectangles[printeri].setFill(Color.RED);
				mainOS.drawLineBetDiskAndPrinter(file.diskNumber, printeri);
			});
			
			for (int i = 0; i < file.fileLength; i++)
			{
				StringBuffer data = new StringBuffer();
				mainOS.disks[file.diskNumber].read(file.startingSector + i, data);
				mainOS.printers[printeri].print(data);
			}
			mainOS.completedOutput++;
			
			Platform.runLater(() -> {
				mainOS.printerRectangles[printeri].setFill(Color.LIGHTGREEN);
				mainOS.removeLineBetDiskAndPrinter(file.diskNumber, printeri);
				mainOS.cleanDisk2();
			});
			mainOS.printerManager.release(printeri);
		}
		catch (Exception e)
		{
			System.out.println("In PrintJobThread Catch exception: "+e);
		}
	}
}