JFLAGS = -g
JC = javac -cp src
JVM = java -cp ./src 
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	src/DirectoryManager.java \
	src/Disk.java \
	src/ResourceManager.java \
	src/Printer.java \
	src/DiskManager.java \
	src/UserThread.java \
	src/PrintJobThread.java \
    src/mainOS.java

MAIN = mainOS

default: classes
	$(JVM) $(MAIN)

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/*.class