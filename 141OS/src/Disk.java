import javafx.application.Platform;
import javafx.scene.paint.Color;

class Disk
{
	static final int NUM_SECTORS = 1024;
	StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];
	int id;

	Disk(int diski)
	{
		id = diski;
	}
	void write(int sector, StringBuffer data) throws InterruptedException
	{
		Platform.runLater(() -> {
			mainOS.diskRectangles[id-1].setFill(Color.RED);
			if (mainOS.subdiskRectangles[id-1].getFill() == Color.LIGHTGREEN)
				mainOS.subdiskRectangles[id-1].setFill(Color.RED);
		});
		Thread.sleep(200);
		sectors[sector] = data;
		//System.out.println("Disk "+id+" write data "+data+" at sector "+sector);
	}
	void read(int sector, StringBuffer data) throws InterruptedException
	{
		Platform.runLater(() -> {
			mainOS.subdiskRectangles[id-1].setFill(Color.YELLOW);
			if (mainOS.diskRectangles[id-1].getFill() == Color.LIGHTGREEN)
				mainOS.diskRectangles[id-1].setFill(Color.YELLOW);
		});
		Thread.sleep(200);
		data.append(sectors[sector]);
		//System.out.println("Disk "+id+" read data "+data+" at sector "+sector);
	}
}