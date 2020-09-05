import java.awt.Color;

import javax.swing.JFrame;

public class Main
{
	static JFrame frame =  new JFrame();
	
	static int rw = 1000;
	static int rh = 680;
	
	
	public static void main(String args[])
	{
		frame.pack();
		frame.setSize(rw, rh);
		frame.setLocationRelativeTo(null);
		frame.add(new Control());
		frame.setVisible(true);
	}
}