package game;

import java.awt.*;
import javax.swing.undo.*;
import managers.*;
import javax.swing.event.UndoableEditEvent;
import java.util.TimerTask;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.IOException;
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
	private Ponto[] pontos;
	private Color[] colors = {Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE};
	private Ratio[] stdRatios = {new Ratio(1, 2), new Ratio(1, 4), new Ratio(3, 4)};
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

	public ChaosGame(int nVertices)
	{
		pontos = new Ponto[nVertices];	
		numVertices = nVertices;
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
		
		int rx = r.nextInt(380);
		int ry = r.nextInt(320);

		while(rx < 200)
			rx = r.nextInt(380);

		while(ry < 100)
			ry = r.nextInt(320);

		PontoColorido tracePoint = new PontoColorido(rx, ry, 0);
		stack.push(tracePoint);

		dimension = new Dimension(640, 640);
		
		createVertices(nVertices);
	}
	
	private void createVertices(int nVertices)
	{
		int margin = 60;
		int size = dimension.width - margin * 2;
		
		pontos = new Ponto[nVertices];
		
		pontos[0] = new Ponto(dimension.width / 2, dimension.height / 2 - 150);
		
		switch(nVertices)
		{
			case 2:
				pontos[1] = new Ponto(dimension.width / 2, dimension.height - margin - 70);
				break;
		
			case 3:		
				pontos[1] = new Ponto(margin + 50, size - 50);
		        pontos[2] = new Ponto(530, size - 50);
		        break;
		        
			case 4:
				pontos[1] = new Ponto(margin + 80, (dimension.height / 2));
		        pontos[2] = new Ponto(dimension.width - margin - 80, (dimension.height / 2));
		        pontos[3] = new Ponto(dimension.width / 2, dimension.height - margin - 110);
		        break;
		        
			case 5:
				pontos[1] = new Ponto(dimension.width / 2 + 180, dimension.height / 2 - 55);
				pontos[2] = new Ponto(dimension.width / 2 - 180, dimension.height / 2 - 55);
				pontos[3] = new Ponto(dimension.width / 2 + 120, dimension.height / 2 + 120);
				pontos[4] = new Ponto(dimension.width / 2 - 120, dimension.height / 2 + 120);
				break;
		}
	}
	
	private Ponto proximoPonto(Ponto currentPoint, Ponto proxVertice, int currentRatio)
	{
		Ponto p;
		Ratio current = stdRatios[currentRatio];
		
		if(currentRatio == 1)
		p = new Ponto( (proxVertice.getX() + currentPoint.getX()) / current.getDenominator() + 170, (proxVertice.getY() + currentPoint.getY()) / current.getDenominator() + 170 );
			
		else if(currentRatio == 2)
		p = new Ponto(current.getNumerator() * ((proxVertice.getX() + currentPoint.getX()) / current.getDenominator()) - 240, current.getNumerator() * ((proxVertice.getY() + currentPoint.getY()) / current.getDenominator())  - 200);
		
		else
		p = new Ponto( (proxVertice.getX() + currentPoint.getX()) / 2, (proxVertice.getY() + currentPoint.getY()) / 2 );
		
		return p;
	}

	private void addPoint()
	{
		try
		{
			int proxVertice = r.nextInt(numVertices);
			
			if(!coloredModeOn)
			{
				if(specialRuleOn)
				{
					if(proxVertice != prev)
					{
						Ponto pontoAtual = stack.peek();
						Ponto prox = pontos[proxVertice];
						stack.add(new PontoColorido(proximoPonto(pontoAtual, prox, currentRatio), 0));
						currentTurn++;
					}
							
					prev = proxVertice;
				}
				
				else
				{
					Ponto pontoAtual = stack.peek();
					Ponto prox = pontos[proxVertice];
					stack.add(new PontoColorido(proximoPonto(pontoAtual, prox, currentRatio), 0));
					currentTurn++;
				}
			}

			else
			{
				if(specialRuleOn)
				{
					if(proxVertice != prev)
					{
						Ponto pontoAtual = stack.peek();
						Ponto prox = pontos[proxVertice];
						stack.add(new PontoColorido(proximoPonto(pontoAtual, prox, currentRatio), proxVertice));
						currentTurn++;
					}
					
					prev = proxVertice;
				}
				
				else
				{
					Ponto pontoAtual = stack.peek();
					Ponto prox = pontos[proxVertice];
					stack.add(new PontoColorido(proximoPonto(pontoAtual, prox, currentRatio), proxVertice));
					currentTurn++;
				}
			}
			
		} catch(EmptyStackException e)
		{
			System.out.println(e);
		}
	}
	
	public void mostrarInstrucoes(Graphics2D g)
	{
		g.setFont(new Font("Oswald", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Simulation Commands: ", 630, 50);
		g.drawString("Press 'Spacebar' to end the simulation.", 630, 80);
		g.drawString("Press the right arrow key to speed up the simulation.", 630, 110);
		g.drawString("Press the left arrow key to slow down the simulation.", 630, 140);
		g.drawString("Press 'R' to reset the simulation.", 630, 170);
		g.drawString("Press 'P' to turn on 'Colored Point mode' (This will reset the game).", 630, 200);
		g.drawString("Press numbers 2-5 to change the number of vertices (This will reset the game).", 630, 230);
		g.drawString("Press Z to make the tracepoint go 1/2 of the way (This will reset the game).", 630, 260);
		g.drawString("Press X to make the tracepoint go 1/4 of the way (This will reset the game).", 630, 290);
		g.drawString("Press C to make the tracepoint go 3/4 of the way (This will reset the game).", 630, 320);
		g.drawString("Press 'Shift' to activate a special rule! (This will reset the game)", 630, 350);
	}
	
	private void showInfo(Graphics2D gg)
	{
		gg.setColor(Color.WHITE);
		gg.setFont(font1);
		gg.drawString("Iterations: " + currentTurn, 20, 100);
		
		simulationSpeed = (int) Math.pow(2, currentSpeed);
		
		gg.drawString("Speed: " + simulationSpeed + "X", 20, 130);
		
		gg.drawString("Tracepoint currently at: ", 630, 410);
		gg.drawString("x = " + stack.peek().getX() + ", y = " + stack.peek().getY(), 630, 440);
		
		gg.drawString("Currently going " + stdRatios[currentRatio].getNumerator() + "/" + 
		stdRatios[currentRatio].getDenominator() + " of the way through.", 630, 470);
	}

	private void desenharVertices(Graphics2D g)
	{
		int i = 1;

		for(Ponto p : pontos)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Oswald", Font.PLAIN, 12));
			g.fillOval(p.getX(), p.getY(), 10, 10);
			
			if(i == 1)
			g.drawString("Point " + i++, p.getX(), p.getY() - 5);					
			
			else if(i > 1)
			g.drawString("Point " + i++, p.getX(), p.getY() + 25);
		}
	}
	
	private void desenharTracepoint(Graphics2D g)
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
	
	public void reset(int nVertices)
	{		
		currentTurn = 0;		
		stack.clear();	
		stack.add(new PontoColorido(323, 173, 0));
		currentSpeed = 0;
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
        				//gameManager.speedUpIssued = !gameManager.speedUpIssued;
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
        			specialRuleOn = true;
        			reset(numVertices);
        		}
        		
        		if(gameManager.ratioChangeIssued) //TODO: Replace this with a method, instead of a public attribute.
        		{
        			gameManager.ratioChangeIssued = false;
        			setRatio(gameManager.newRatioRequested());
        		}
        		
        		if(gameManager.numVerticesChangeIssued) //TODO: Replace this with a method, instead of a public attribute.
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
		//TODO: Por tudo isso aqui no InterfaceManager.
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		showInfo(gg);
		mostrarInstrucoes(gg);
		desenharVertices(gg);
		desenharTracepoint(gg);
		
		if(resetIssued)
		{
			gg.setColor(Color.BLACK);
			gg.fillRect(0,  0, 1280, 640);
			
			resetIssued = false;
			repaint();
		}
	}
	
	public void changeNumberOfVertices(int newNumber)
	{	
		if(newNumber == 2)
		{
			if(numVertices != 3)
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
	
	public Ponto[] getVetorPontos()
	{
		return pontos;
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
