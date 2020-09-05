import java.awt.Color;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;

enum ori { NW, NE, SE, SW, LENGTH };

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
	
	

	public static class Control extends JPanel implements ActionListener
	{
		// ID-lization or something.
		private static final long serialVersionUID = 1L;
		
		long fib;
		int inp;
		int[] box;
		double time;
		
		Label label, result;
		TextField input;
		Button but;
		CheckboxGroup check;
		Checkbox ch1, ch2;
			
		public Control()
		{
			int h = 24;
			
			label = new Label("Fibonacci de:");
			label.setBounds(0, 0, 84, h);
			Main.frame.add(label);
			
			input = new TextField("0");
			input.setBounds(84, 0, 64, h);
			Main.frame.add(input);
			
			check = new CheckboxGroup();
			ch1 = new Checkbox("Ciclo", check, true);
			ch1.setBounds(84+64, 0, 48, h);
			Main.frame.add(ch1);
			ch2 = new Checkbox("Recursivo", check, false);
			ch2.setBounds(84+64+48, 0, 84, h);
			Main.frame.add(ch2);
			
			but = new Button("Calcular");
			but.setBounds(84+64+48+84, 0, 64, h);
			but.addActionListener(this);
			Main.frame.add(but);
			
			result = new Label("");
			result.setBounds(84+64+48+84+64, 0, 546, h);
			Main.frame.add(result);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			result.setText(" Calculando...");
			
			inp = Integer.parseInt(input.getText());
			long timeTotal;
			
			if (ch1.getState())
			{
				long timeStart = System.currentTimeMillis();
				fib = fibonacciCycle(inp);
				timeTotal = System.currentTimeMillis() - timeStart;
			}
			else
			{
				long timeStart = System.currentTimeMillis();
				fib = fibonacciRecursive(inp);
				timeTotal = System.currentTimeMillis() - timeStart;
			}
			
			
			result.setText("es: "+fib+" (resultado en "+timeTotal+" milisegundos).");
			System.out.println(fib+" (resultado en "+timeTotal+" milisegundos).");
			
			removeAll();
			repaint();
		}
		
		
		public long fibonacciRecursive(int n)
		{
			if (n == 1 || n == 2)
				return 1;
			return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
		}
		
		
		public long fibonacciCycle(int n)
		{
			long fib = 0;
			long v1 = 1;
			long v2 = 0;
			
			while (n > 0)
			{
				fib = v1 + v2;
				v1 = v2;
				v2 = fib;
				n--;
			}
			return fib;
		}
		
		
		public void paint(Graphics g)
		{
			super.paintComponent(g);
			fibonacciDraw(g);
		}
		
		
		
		public void fibonacciDraw(Graphics g)
		{
			if (fib != 0)
			{
				int px = 16;
				int py = 32;
				
				long max = fibonacciCycle(inp);
				
				ori pivot = ori.NW;
				
				for (int i=inp; i>0; i--)
				{
					int w = width(i, max);
					
					g.setColor(randomColor());
					g.fillRect(px, py, w, w);
					g.setColor(Color.black);
					g.drawRect(px, py, w, w);
					
					if (w > 64)
						g.drawString(""+fibonacciCycle(i), px+4, py+g.getFont().getSize()+4);
					
					
					switch(pivot)
					{
						case NW:
							g.drawArc(px, py, w*2, w*2, 90, 90);
							px += w;
						break;
						
						case NE:
							g.drawArc(px-w, py, w*2, w*2, 0, 90);
							px += width(i-2, max);
							py += w;						
						break;
						
						case SE:
							g.drawArc(px-w, py-w, w*2, w*2, 270, 90);
							px -= width(i-1, max);
							py += width(i-2, max);
						break;
						
						case SW:
							g.drawArc(px, py-w, w*2, w*2, 180, 90);
							py -= width(i-1, max);
						break;
					}
					
					pivot = (pivot==ori.SW) ? ori.NW : ori.values()[pivot.ordinal() + 1];
				}
			}
		}
		
		public int width(int i, long max)
		{
			float len = (float) (((float)Main.rw / 1.65) - 16);
			int fib = (int) fibonacciCycle(i);
			return (int) (len * ((float)fib / (float)max));
		}
		
		public Color randomColor()
		{
			int max = 5;
			int min = 0;
			int rand = (int) ((Math.random() * ((max - min) + 1)) + min);
			
			switch(rand)
			{
				case 0:	return Color.red;	
				case 1:	return Color.blue;
				case 2:	return Color.yellow;
				case 3:	return Color.green;
				case 4:	return Color.orange;
				case 5:	return Color.pink;
			}
			return Color.black;
		}
	}
}


