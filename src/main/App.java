package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.ChaosGame;
import managers.*;

public class App 
{
	public static void main(String[] args) 
	{
		ChaosGame cg = new ChaosGame(5);
		InterfaceManager iManager = new InterfaceManager(cg);
	}
}
