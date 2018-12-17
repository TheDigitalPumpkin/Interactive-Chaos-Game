package managers;
import java.awt.*;
import game.ChaosGame;
import ponto.Ponto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EmptyStackException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class InterfaceManager extends JPanel
{
	public JFrame frame;
	private Dimension dimension;
	private ChaosGame cg;
	
	public InterfaceManager(ChaosGame cg)
	{
		this.cg = cg;
		
		frame = new JFrame("Chaos Game!");
		dimension = new Dimension(1280, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(dimension);
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		
		cg.setBackground(Color.BLACK);
		//addCompToPanel(cg);
		
		/*mainScreen = new JPanel();
		mainScreen.setBackground(Color.BLACK);
		mainScreen.setFocusable(true);
		
		cg.setPreferredSize(dimension);
		cg.setBackground(Color.BLACK);
		mainScreen.add(cg);
		mainScreen.setLocation(dimension.width / 2, dimension.height / 2);*/
		
		frame.add(cg, BorderLayout.CENTER);	
		cg.start();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void mostrarInstrucoes(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.drawString("Instructions: ", dimension.width - (dimension.width / 4), 120);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}