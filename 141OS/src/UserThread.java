import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.paint.Color;

class UserThread extends Thread
{
	int id;
	StringBuffer data;
	BufferedReader reader;
	UserThread(int index) throws IOException
	{
		id = index;
		File file = new File("./inputs/USER" + id);
		reader = new BufferedReader(new FileReader(file));
	}
	void read() throws IOException
	{
		data = new StringBuffer(reader.readLine());
	}

	@Override
	public void run()
	{
		try 
		{
			read();
			while (data != null)
			{
				if (data.lastIndexOf(".save") != -1)
				{
					StringBuffer fileName = data.delete(0, 6);
					FileInfo file = new FileInfo();

					Platform.runLater(() -> {
						mainOS.userCircles[id-1].setFill(Color.YELLOW);
					});
					file.diskNumber = mainOS.diskManager.request();
					
					Platform.runLater(() -> {
						mainOS.userCircles[id-1].setFill(Color.RED);
						mainOS.drawLineBetUserAndDisk(id-1, file.diskNumber);
					});
					
					file.startingSector = mainOS.diskManager.nextSector(file.diskNumber);
					file.fileLength = 0;
					read();
					while (data.lastIndexOf(".end") == -1)
					{
						mainOS.disks[file.diskNumber].write(file.startingSector + file.fileLength, data);
						mainOS.diskManager.addCount(file.diskNumber);
						file.fileLength++;
						read();
					}
					//System.out.println("UserThread "+id+" created file "+fileName+" on disk "+(file.diskNumber+1)+" at sector "+file.startingSector);
					mainOS.diskManager.release(file.diskNumber);
					mainOS.directoryManager.enter(fileName, file);
					
					Platform.runLater(() -> {
						mainOS.userCircles[id-1].setFill(Color.LIGHTGREEN);
						mainOS.removeLineBetUserAndDisk(id-1, file.diskNumber);
						if (mainOS.subdiskRectangles[file.diskNumber].getFill() == Color.YELLOW)
							mainOS.diskRectangles[file.diskNumber].setFill(Color.YELLOW);
						else if (mainOS.subdiskRectangles[file.diskNumber].getFill() == Color.RED)
							mainOS.cleanDisk1();
					});
				}
				else if (data.lastIndexOf(".print") != -1)
				{
					StringBuffer fileName = data.delete(0, 7);
					PrintJobThread printJob = new PrintJobThread(fileName);
					printJob.start();
				}
				read();
			}
			reader.close();
		}
		catch (Exception e)
		{}
	}
}