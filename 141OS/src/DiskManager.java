class DiskManager extends ResourceManager
{
	int[] freeSectors;
	DiskManager(int NUMBER_OF_DISKS)
	{
		super(NUMBER_OF_DISKS);
		freeSectors = new int[NUMBER_OF_DISKS];
	}
	void addCount(int index)
	{
		freeSectors[index]++;
	}
	int nextSector(int index)
	{
		return freeSectors[index];
	}
}