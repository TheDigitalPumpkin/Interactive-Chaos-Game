package main;
import game.ChaosGame;
import managers.*;

public class App 
{
	public static void main(String[] args) 
	{
		ChaosGame cg = new ChaosGame();
		InterfaceManager iManager = new InterfaceManager(cg);
	}
}
