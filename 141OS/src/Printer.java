import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Printer
{
	int index;
	Printer(int PRINTERi) throws IOException
	{
		index = PRINTERi;
	}
	void print(StringBuffer data) throws InterruptedException, IOException
	{
		Thread.sleep(2750);
		String content = data.toString() + "\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter("./outputs/PRINTER" + index, true));
		writer.write(content);
		writer.close();
		//System.out.println("Printer "+index+" print data "+data);
	}
}