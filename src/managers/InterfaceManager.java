package managers;
import java.awt.*;
import game.ChaosGame;
import javax.swing.*;

public class InterfaceManager extends JFrame
{
	private JFrame frame;
	private Dimension dimension;
	private ChaosGame cg;
	private JPanel mainScreen;
	
	public InterfaceManager(ChaosGame cg)
	{
		this.cg = cg;
		
		setTitle("Chaos Game!");	
		dimension = new Dimension(1280, 640);
		setPreferredSize(dimension);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
		
		cg.setBackground(Color.BLACK);		
		add(cg, BorderLayout.CENTER);

		cg.start();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
