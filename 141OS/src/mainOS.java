import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainOS extends Application
{
	// OS stuff
	static int NUMBER_OF_USERS = 4;
	static int NUMBER_OF_DISKS = 2;
	static int NUMBER_OF_PRINTERS = 3;
	static DiskManager diskManager = new DiskManager(NUMBER_OF_DISKS);
	static ResourceManager printerManager = new ResourceManager(NUMBER_OF_PRINTERS);
	static DirectoryManager directoryManager = new DirectoryManager();
	static Disk[] disks = new Disk[NUMBER_OF_DISKS];
	static Printer[] printers = new Printer[NUMBER_OF_PRINTERS];
	static UserThread[] users = new UserThread[NUMBER_OF_USERS];
	
	// GUI stuff
	static Button button;
    static Stage window;
    static Group root = new Group();
    static int radius = 25;
    static Circle[] userCircles = new Circle[NUMBER_OF_USERS];
    static Rectangle[] diskRectangles = new Rectangle[NUMBER_OF_DISKS];
    static Rectangle[] subdiskRectangles = new Rectangle[NUMBER_OF_DISKS];
    static Rectangle[] printerRectangles = new Rectangle[NUMBER_OF_PRINTERS];
    static Line[] lines = new Line[14];
    static int completedOutput = 0;
    
    public static void main(String[] args)
	{
		System.out.println("Program starts.");
		try
		{
			for (int i = 0; i < disks.length; i++)
			{
				disks[i] = new Disk(i+1);
			}
			for (int i = 0; i < printers.length; i++)
			{
				printers[i] = new Printer(i+1);
			}

			
			for (int i = 0; i < users.length; i++)
			{
				users[i] = new UserThread(i+1);
			}

			launch(args);
		}
		catch(Exception e)
		{
			System.out.println("In mainOS Catch exception: "+e);
		}

		System.out.println("Program ends.");
	}
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
    	drawElements();
    	window = primaryStage;
        window.setTitle("141OS Simulation");
        
        button = new Button("START");
        button.setLayoutX(350);
        button.setLayoutY(550);
        button.setMinWidth(100);
        button.setOnAction(e -> process());

        root.getChildren().add(button);
        Scene scene = new Scene(root, 800, 600);

        window.setScene(scene);
        window.show();
    }
    
    static void drawLineBetUserAndDisk(int id1, int id2)
    {
    	lines[id1*2+id2] = new Line(130, (id1+1)*100+50, 240, id2*180+210);
    	lines[id1*2+id2].setStroke(Color.RED);
    	lines[id1*2+id2].setStrokeWidth(3);
    	root.getChildren().add(lines[id1*2+id2]);
    }
    static void removeLineBetUserAndDisk(int id1, int id2)
    {
    	root.getChildren().remove(lines[id1*2+id2]);
    	lines[id1*2+id2] = null;
    }
    static void drawLineBetDiskAndPrinter(int id1, int id2)
    {
    	lines[8+id1*3+id2] = new Line(360, id1*180+210, 660, id2*115+185);
    	lines[8+id1*3+id2].setStroke(Color.YELLOW);
    	lines[8+id1*3+id2].setStrokeWidth(3);
    	root.getChildren().add(lines[8+id1*3+id2]);
    }
    static void removeLineBetDiskAndPrinter(int id1, int id2)
    {
    	root.getChildren().remove(lines[8+id1*3+id2]);
    	lines[8+id1*3+id2] = null;
    }
    
    static void cleanDisk1()
    {
    	boolean clean = true;
    	for (int i = 0; i < 8; i += 2)
        {
        	if (lines[i] != null)
        	{
        		clean = false;
        		break;
        	}
        }
		if (clean)
		{
			diskRectangles[0].setFill(Color.LIGHTGREEN);
			subdiskRectangles[0].setFill(Color.LIGHTGREEN);
		}
		clean = true;
    	for (int i = 1; i < 8; i += 2)
    	{
    		if (lines[i] != null)
    		{
    			clean = false;
        		break;
    		}
    	}
    	if (clean)
    	{
        	diskRectangles[1].setFill(Color.LIGHTGREEN);
			subdiskRectangles[1].setFill(Color.LIGHTGREEN);
    	}
    }
    static void cleanDisk2()
    {
    	boolean clean = true;
    	for (int i = 8; i < 11; i++)
        {
        	if (lines[i] != null)
        	{
        		clean = false;
        		break;
        	}
        }
		if (clean)
		{
			diskRectangles[0].setFill(Color.LIGHTGREEN);
			subdiskRectangles[0].setFill(Color.LIGHTGREEN);
		}
		clean = true;
    	for (int i = 11; i < 14; i++)
    	{
    		if (lines[i] != null)
    		{
    			clean = false;
        		break;
    		}
    	}
    	if (clean)
    	{
        	diskRectangles[1].setFill(Color.LIGHTGREEN);
			subdiskRectangles[1].setFill(Color.LIGHTGREEN);
    	}
    	waitToEnd();
    }
    static void waitToEnd()
    {
    	if (completedOutput == 24)
    	{
    		button.setText("END");
    		button.setOnAction(e -> window.close());
    	}
    }
    
    void drawElements()
    {
    	// headers
    	Line divider = new Line(0, 100, 800, 100);
        divider.setStrokeWidth(1);
        
        Text user = new Text("Users");
        user.setX(85);
        user.setY(20);
        user.setScaleX(2);
        user.setScaleY(2);
        
        Text disk = new Text("Disks");
        disk.setX(280);
        disk.setY(20);
        disk.setScaleX(2);
        disk.setScaleY(2);
        
        Text printer = new Text("Printers");
        printer.setX(680);
        printer.setY(20);
        printer.setScaleX(2);
        printer.setScaleY(2);
        
        Circle user1 = new Circle(70, 40, 10, Color.LIGHTGREEN);
        Circle user2 = new Circle(70, 62, 10, Color.YELLOW);
        Circle user3 = new Circle(70, 84, 10, Color.RED);
        Text u1 = new Text("free");
        u1.setX(90);
        u1.setY(42);
        Text u2 = new Text("wait");
        u2.setX(90);
        u2.setY(64);
        Text u3 = new Text("write");
        u3.setX(90);
        u3.setY(86);      
        root.getChildren().addAll(user1, user2, user3, u1, u2, u3);

        Rectangle disk1 = new Rectangle(250, 30, 15, 15);
        disk1.setFill(Color.LIGHTGREEN);
        Rectangle disk2 = new Rectangle(250, 50, 15, 15);
        disk2.setFill(Color.YELLOW);
        Rectangle disk3 = new Rectangle(250, 70, 15, 15);
        disk3.setFill(Color.RED);
        Text d1 = new Text("free");
        d1.setX(270);
        d1.setY(42);
        Text d2 = new Text("read");
        d2.setX(270);
        d2.setY(64);
        Text d3 = new Text("write");
        d3.setX(270);
        d3.setY(86);     
        root.getChildren().addAll(disk1, disk2, disk3, d1, d2, d3);
        
        Rectangle printer1 = new Rectangle(670, 30, 15, 15);
        printer1.setFill(Color.LIGHTGREEN);
        Rectangle printer2 = new Rectangle(670, 50, 15, 15);
        printer2.setFill(Color.RED);      
        Text p1 = new Text("free");
        p1.setX(690);
        p1.setY(42);
        Text p2 = new Text("print");
        p2.setX(690);
        p2.setY(64);
        root.getChildren().addAll(printer1, printer2, p1, p2);
        
        root.getChildren().addAll(divider, user, disk, printer);
        
        // actual shapes
        for (int i = 0; i < userCircles.length; i++)
        {
        	userCircles[i] = new Circle(100, (i+1)*100+50, radius, Color.LIGHTGREEN);
        	userCircles[i].setStroke(Color.BLACK);
        	userCircles[i].setStrokeWidth(3);
        	root.getChildren().add(userCircles[i]);
        }

        for (int i = 0; i < diskRectangles.length; i++)
        {
        	diskRectangles[i] = new Rectangle(250, i*180+60+100, 100, 50);
        	diskRectangles[i].setFill(Color.LIGHTGREEN);
        	diskRectangles[i].setStroke(Color.BLACK);
        	diskRectangles[i].setStrokeWidth(3);
        	root.getChildren().add(diskRectangles[i]);
        }
        for (int i = 0; i < subdiskRectangles.length; i++)
        {
        	subdiskRectangles[i] = new Rectangle(250, i*180+210, 100, 50);
        	subdiskRectangles[i].setFill(Color.LIGHTGREEN);
        	subdiskRectangles[i].setStroke(Color.BLACK);
        	subdiskRectangles[i].setStrokeWidth(3);
        	root.getChildren().add(subdiskRectangles[i]);
        }
        for (int i = 0; i < printerRectangles.length; i++)
        {
        	printerRectangles[i] = new Rectangle(670, i*115+55+100, 60, 60);
        	printerRectangles[i].setFill(Color.LIGHTGREEN);
        	printerRectangles[i].setStroke(Color.BLACK);
        	printerRectangles[i].setStrokeWidth(3);
        	root.getChildren().add(printerRectangles[i]);
        }
    }
    void process()
    {
    	try
    	{
	        button.setText("Processing");
	
	        for (int i = 0; i < users.length; i++)
			{
				users[i].start();
			}
    	}
    	catch (Exception e){}
    }
}