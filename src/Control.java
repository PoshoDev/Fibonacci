import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Control extends JPanel implements ActionListener
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
		int fib = 0;
		int v1 = 1;
		int v2 = 0;
		
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
		fibonacciDraw(g);
	}
	
	enum ori { NW, NE, SE, SW, LENGTH };
	
	public void fibonacciDraw(Graphics g)
	{
		if (fib != 0)
		{
			int stx = 16;
			int sty = 32;
			
			int curr = (int) fib;
			
			int px = stx;
			int py = sty;
			
			int w;
			int max = (int) fibonacciCycle(inp);
			
			ori pivot = ori.NW;
			
			for (int i=inp; i>0; i--)
			{
				
				w = width(i, max);
				g.drawRect(px, py, w, w);
				
				System.out.println("FIB "+i+": "+w);
				
				switch(pivot)
				{
					case NW: px += w ; break;
					case NE:
						px += width(i-2, max);
						py += w;
						
					break;
					case SE:
						px -= width(i-1, max);
						py += width(i-2, max);
					break;
					case SW: py -= width(i-1, max); break;
				}
				
				pivot = (pivot==ori.SW) ? ori.NW : ori.values()[pivot.ordinal() + 1];
				
				//curr = (int) fibonacciCycle(i-1);
				
				
			}
		}
	}
	
	public int width(int i, int max)
	{
		int len = 500;//(Main.rw / 2) - 16;
		
		int fib = (int) fibonacciCycle(i);
		int result = (int) ((float)len * ((float)fib / (float)max));
		System.out.println(len+" * ("+fib+" / "+max+") = "+result);
		return result;
	}
}
