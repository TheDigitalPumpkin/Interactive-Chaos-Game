/**
* Chaos Game!
*
*
* @author Bruno Bacelar
* @version 1.0
* @since 17/12/2018
*/

package game;

import java.awt.*;
import managers.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.Timer;
import ponto.*;
import misc.Ratio;

public class ChaosGame extends JPanel
{
	private int prev;
	private Dimension dimension;
	private GameManager gameManager;
	private int numVertices;
	private Stack<PontoColorido> stack = new Stack<>();
	private Ponto[] points;
	private Color[] colors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.CYAN, Color.MAGENTA};
	private Ratio[] stdRatios = {new Ratio(1, 2), new Ratio(1, 4), new Ratio(1, 3), new Ratio(3, 8), new Ratio(3, 4)};
	private int[] gameSpeeds = {1301, 901, 451, 201, 101, 51, 1};
	private Random r = new Random();
	private Timer t;
	private int currentRatio;
	private int currentTurn;
	private int currentSpeed;
	private int lastSpeed;
	private int simulationSpeed;
	private Font font1;
	private Font font2;
	private boolean isPaused;
	private boolean resetIssued;
	private boolean coloredModeOn;
	private boolean specialRuleOn;

	public ChaosGame()
	{
		points = new Ponto[3];	
		numVertices = 3;
		currentTurn = 1;	
		currentSpeed = 0;
		lastSpeed = 0;
		currentRatio = 0;
		isPaused = false;
		resetIssued = false;
		coloredModeOn = false;
		specialRuleOn = false;
		
		gameManager = new GameManager();
		KeyListener listener = gameManager;
		
		this.addKeyListener(listener);
		this.setFocusable(true);
		
		font1 = new Font("Oswald", Font.BOLD, 20);
		font2 = new Font("Inconsolata", Font.BOLD, 14);
		
		int rx = r.nextInt((380 - 200) + 1) + 250;
		int ry = r.nextInt((320 - 100) + 1) + 140;

		PontoColorido tracePoint = new PontoColorido(rx, ry, 0);
		stack.push(tracePoint);

		dimension = new Dimension(640, 640);
		
		createVertices(3);
	}
	
	private void createVertices(int nVertices)
	{
		int margin = 60;
		int size = dimension.width - margin * 2;
		
		points = new Ponto[nVertices];
		
		points[0] = new Ponto(dimension.width / 2, dimension.height / 2 - 150);
		
		switch(nVertices)
		{
			case 2:
				points[1] = new Ponto(dimension.width / 2, dimension.height - margin - 70);
				break;
		
			case 3:		
				points[1] = new Ponto(margin + 50, size - 50);
		        	points[2] = new Ponto(530, size - 50);
		        	break;
		        
			case 4:
		        	points[1] = new Ponto(dimension.width - margin - 80, (dimension.height / 2));
		       	 	points[2] = new Ponto(dimension.width / 2, dimension.height - margin - 110);
		        	points[3] = new Ponto(margin + 80, (dimension.height / 2));
		        	break;
		        
			case 5:
				points[1] = new Ponto(dimension.width / 2 + 180, dimension.height / 2 - 55);
				points[2] = new Ponto(dimension.width / 2 + 120, dimension.height / 2 + 120);
				points[3] = new Ponto(dimension.width / 2 - 120, dimension.height / 2 + 120);
				points[4] = new Ponto(dimension.width / 2 - 180, dimension.height / 2 - 55);
				break;
				
			case 6:
				points[1] = new Ponto(dimension.width / 2 + 160, dimension.height / 2 - 65);
				points[2] = new Ponto(dimension.width / 2 + 160, dimension.height / 2 + 110);
				points[3] = new Ponto(dimension.width / 2, dimension.height - margin - 80);
				points[4] = new Ponto(dimension.width / 2 - 160, dimension.height / 2 + 110);
				points[5] = new Ponto(dimension.width / 2 - 160, dimension.height / 2 - 65);
				break;
				
			case 7:
				points[1] = new Ponto(dimension.width / 2 + 160, dimension.height / 2 - 65);
				points[2] = new Ponto(dimension.width / 2 + 160, dimension.height / 2 + 80);
				points[3] = new Ponto(dimension.width / 2 + 70, dimension.height - margin - 80);
				points[4] = new Ponto(dimension.width / 2 - 70, dimension.height - margin - 80);
				points[5] = new Ponto(dimension.width / 2 - 160, dimension.height / 2 + 80);
				points[6] = new Ponto(dimension.width / 2 - 160, dimension.height / 2 - 65);
				break;
		}
	}
	
	private Ponto nextPoint(Ponto currentPoint, Ponto nextVertex, int currentRatio)
	{
		Ponto p = null;
		Ratio current = stdRatios[currentRatio];
		
		if(currentRatio == 1)
		p = new Ponto( (nextVertex.getX() + currentPoint.getX()) / current.getDenominator() + 170, (nextVertex.getY() + currentPoint.getY()) / current.getDenominator() + 170 );
			
		else if(currentRatio == 2)
		p = new Ponto(current.getNumerator() * ((nextVertex.getX() + currentPoint.getX()) / current.getDenominator()) + 107, current.getNumerator() * ((nextVertex.getY() + currentPoint.getY()) / current.getDenominator()) + 110);
		
		else if(currentRatio == 3)
		p = new Ponto( current.getNumerator() * ((nextVertex.getX() + currentPoint.getX()) / current.getDenominator()) + 90, current.getNumerator() * ((nextVertex.getY() + currentPoint.getY()) / current.getDenominator()) + 90);
		
		else
		p = new Ponto((nextVertex.getX() + currentPoint.getX()) / 2, (nextVertex.getY() + currentPoint.getY()) / 2);
		
		return p;
	}

	private void addPoint()
	{
		try
		{
			int nextVertex = r.nextInt(numVertices);
			
			if(!coloredModeOn)
			{
				if(specialRuleOn)
				{
					if(nextVertex != prev)
					{
						Ponto currPoint = stack.peek();
						Ponto next = points[nextVertex];
						stack.add(new PontoColorido(nextPoint(currPoint, next, currentRatio), 0));
						currentTurn++;
					}
							
					prev = nextVertex;
				}
				
				else
				{
					Ponto currPoint = stack.peek();
					Ponto next = points[nextVertex];
					stack.add(new PontoColorido(nextPoint(currPoint, next, currentRatio), 0));
					currentTurn++;
				}
			}

			else
			{
				if(specialRuleOn)
				{
					if(nextVertex != prev)
					{
						Ponto currPoint = stack.peek();
						Ponto next = points[nextVertex];
						stack.add(new PontoColorido(nextPoint(currPoint, next, currentRatio), nextVertex));
						currentTurn++;
					}
					
					prev = nextVertex;
				}
				
				else
				{
					Ponto currPoint = stack.peek();
					Ponto next = points[nextVertex];
					stack.add(new PontoColorido(nextPoint(currPoint, next, currentRatio), nextVertex));
					currentTurn++;
				}
			}
			
		} catch(EmptyStackException e)
		{
			System.out.println(e);
		}
	}
	
	private void displayCommands(Graphics2D g)
	{
		g.setFont(new Font("Oswald", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Simulation Commands: ", 630, 50);
		g.drawString("Press 'Spacebar' to end the simulation.", 630, 80);
		g.drawString("Press the right arrow key to speed up the simulation.", 630, 110);
		g.drawString("Press the left arrow key to slow down the simulation.", 630, 140);
		g.drawString("Press 'R' to reset the simulation.", 630, 170);
		g.drawString("Press 'P' to turn on 'Colored Point mode' (This will reset the game).", 630, 200);
		g.drawString("Press numbers 2-7 to change the number of vertices (This will reset the game).", 630, 230);
		g.drawString("Press Z to make the tracepoint go 1/2 of the way (This will reset the game).", 630, 260);
		g.drawString("Press X to make the tracepoint go 1/4 of the way (This will reset the game).", 630, 290);
		g.drawString("Press C to make the tracepoint go 1/3 of the way (This will reset the game).", 630, 320);
		g.drawString("Press V to make the tracepoint go 3/8 of the way (This will reset the game).", 630, 350);
		g.drawString("Press 'Shift' to activate a special rule! (This will reset the game)", 630, 410);
	}
	
	private void showInfo(Graphics2D gg)
	{
		gg.setColor(Color.WHITE);
		gg.setFont(font1);
		gg.drawString("Iterations: " + currentTurn, 20, 50);
		
		simulationSpeed = (int) Math.pow(2, currentSpeed);
		
		gg.drawString("Speed: " + simulationSpeed + "X", 20, 80);		
		gg.drawString("Tracepoint currently at: ", 630, 470);
		gg.drawString("x = " + stack.peek().getX() + ", y = " + stack.peek().getY(), 630, 500);	
		gg.drawString("Currently going " + stdRatios[currentRatio].getNumerator() + "/" + 
		stdRatios[currentRatio].getDenominator() + " of the way through.", 630, 530);
	}

	private void drawVertices(Graphics2D g)
	{
		int i = 1;

		for(Ponto p : points)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Oswald", Font.PLAIN, 12));
			g.fillOval(p.getX() - 4, p.getY() - 7, 10, 10);
			
			if(i == 1)
			g.drawString("Point " + i++, p.getX(), p.getY() - 5);					
			
			else if(i > 1)
			g.drawString("Point " + i++, p.getX(), p.getY() + 25);
		}
	}
	
	private void drawTracepoint(Graphics2D g)
	{
		Graphics2D gg = (Graphics2D) g;
		
		if(!coloredModeOn)
		{
			for(Ponto p : stack)
			{
				try
				{
					gg.setColor(Color.WHITE);
					gg.fillOval(p.getX(), p.getY(), 2, 2);
					
				} catch(EmptyStackException e)
				{
					System.out.println(e);
				}
			}
		}
		
		else
		{
			for(PontoColorido p : stack)
			{
				try
				{
					gg.setColor(colors[p.getColorIndex()]);
					gg.fillOval(p.getX(), p.getY(), 2, 2);
					gg.setColor(Color.WHITE);
				} catch(EmptyStackException e) 
				{
					System.out.println(e);
				}
			}
		}
	}

	private void reset(int nVertices)
	{		
		currentTurn = 0;
		currentSpeed = 0;
		stack.clear();	
		stack.add(new PontoColorido(points[0].getX(), points[0].getY() - 7, 0));
		createVertices(nVertices);
		numVertices = nVertices;
		resetIssued = true;
		t.setDelay(gameSpeeds[currentSpeed]);
	}
	
	public void start()
	{		
		t = new Timer(gameSpeeds[0], (ActionEvent e) -> 
        {       	
        	if(stack.size() < 50000);
        	{	      		
        		if(gameManager.spaceWasPressed())
        		t.stop();
        		
        		if(gameManager.slowDownIssued())
        		{
        			if(currentSpeed >= 1)
        			{
	        			t.setInitialDelay(gameSpeeds[--currentSpeed]);
	        			t.setDelay(gameSpeeds[currentSpeed]);;
        			}
        		}
        		
        		if(gameManager.speedUpIssued())
        		{
        			if(currentSpeed <= 5)
        			{
        				t.setInitialDelay(gameSpeeds[++currentSpeed]);   
        				t.setDelay(gameSpeeds[currentSpeed]);
        			}
        		}
        		
        		if(gameManager.colorModeChangeIssued())
        		{
        			coloredModeOn = !coloredModeOn;
        			reset(numVertices);
        		}
        		
        		if(gameManager.resetIssued())
        		reset(numVertices);      
        		
        		if(gameManager.specialRuleIssued())
        		{
        			specialRuleOn = !specialRuleOn;
        			reset(numVertices);
        		}
        		
        		if(gameManager.ratioChangeIssued)
        		{
        			gameManager.ratioChangeIssued = false;
        			setRatio(gameManager.newRatioRequested());
        		}
        		
        		if(gameManager.numVerticesChangeIssued)
        		{
        			gameManager.numVerticesChangeIssued = false;
        			changeNumberOfVertices(gameManager.newNumberVerticesRequested());
        		}
        		     		
        		addPoint();
        		repaint();
        	}
        });
		
		t.start();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		showInfo(gg);
		displayCommands(gg);
		drawVertices(gg);
		drawTracepoint(gg);
		
		if(resetIssued)
		{
			gg.setColor(Color.BLACK);
			gg.fillRect(0,  0, 1280, 640);
			
			resetIssued = false;
			repaint();
		}
	}
	
	private void changeNumberOfVertices(int newNumber)
	{	
		if(newNumber == 2)
		{
			if(numVertices != 2)
			{
				reset(2);
				return;
			}
		}
		
		if(newNumber == 3)
		{
			if(numVertices != 3)
			{
				reset(3);
				return;
			}
		}
		
		else if(newNumber == 4)
		{
			if(numVertices != 4)
			{
				reset(4);
				return;
			}
		}
		
		else if(newNumber == 5)
		{
			if(numVertices != 5)
			{
				reset(5);
				return;
			}
		}
		
		else if(newNumber == 6)
		{
			if(numVertices != 6)
			{
				reset(6);
				return;
			}
		} 
		
		else if(newNumber == 7)
		{
			if(numVertices != 7)
			{
				reset(7);
				return;
			}
		}
	}
	
	public void setRatio(int newRatio)
	{
		currentRatio = newRatio;
		reset(numVertices);
	}
	
	public Stack<PontoColorido> getStack()
	{
		return stack;
	}
	
	public int getCurrentTurn()
	{
		return currentTurn;
	}
	
	public Ponto[] getPointsArray()
	{
		return points;
	}
	
	public int getNumberOfVertices()
	{
		return numVertices;
	}
	
	public Timer getTimer() 
	{
		return t;
	}
}
