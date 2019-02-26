import java.util.Hashtable;

class DirectoryManager
{
	Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();
	void enter(StringBuffer key, FileInfo file)
	{
		T.put(key.toString(), file);
		//System.out.println("Store file: "+key+" at sector "+file.diskNumber);
	}
	FileInfo lookup(StringBuffer key)
	{
		FileInfo result = T.get(key.toString());
		//System.out.println("looking file: "+key+" at sector "+result.diskNumber);
		return result;
	}
}

class FileInfo
{
	int diskNumber;
	int startingSector;
	int fileLength;
	FileInfo(){}
	FileInfo(int dN, int sS, int fL)
	{
		diskNumber = dN;
		startingSector = sS;
		fileLength = fL;
	}
}