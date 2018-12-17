package managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener
{
	public boolean numVerticesChangeIssued;
	public boolean ratioChangeIssued;
	private boolean spacePressed;
	private boolean slowDownIssued;
	private boolean speedUpIssued;
	private boolean resetIssued;
	private boolean colorModeChangeIssued;
	private boolean specialRuleIssued;
	private int newRatio;
	private int newNumberVertices;
	
	public GameManager()
	{
		spacePressed = false;
		slowDownIssued = false;
		speedUpIssued = false;
		resetIssued = false;
		ratioChangeIssued = false;
		colorModeChangeIssued = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{					
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		spacePressed = !spacePressed;
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		speedUpIssued = true;
			
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		slowDownIssued = true;		
		
		if(e.getKeyCode() == KeyEvent.VK_R)
		resetIssued = true;
		
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			ratioChangeIssued = true;
			newRatio = 0;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			ratioChangeIssued = true;
			newRatio = 1;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_C)
		{
			ratioChangeIssued = true;
			newRatio = 2;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		specialRuleIssued = true;
				
		if(e.getKeyCode() == KeyEvent.VK_P)
		colorModeChangeIssued = true;
		
		if(e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 2;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 3;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 4;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 5;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 6;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7)
		{
			numVerticesChangeIssued = true;
			newNumberVertices = 7;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		//System.out.println("Corno solto = " + KeyEvent.getKeyText(e.getKeyCode()));
	}
	
	public boolean slowDownIssued()
	{
		boolean returned = slowDownIssued;
		slowDownIssued = false;
		return returned;
	}
	
	public boolean speedUpIssued()
	{
		boolean returned = speedUpIssued;
		speedUpIssued = false;
		return returned;
	}
	
	public boolean spaceWasPressed()
	{
		return spacePressed;
	}
	
	public boolean resetIssued()
	{
		boolean returned = resetIssued;
		resetIssued = false;
		return returned;
	}
	
	public boolean colorModeChangeIssued()
	{
		boolean returned = colorModeChangeIssued;
		colorModeChangeIssued = false;
		return returned;
	}
	
	public boolean specialRuleIssued()
	{
		boolean returned = specialRuleIssued;
		specialRuleIssued = false;
		return returned;
	}
	
	public int newRatioRequested()
	{
		return newRatio;
	}
	
	public int newNumberVerticesRequested()
	{
		return newNumberVertices;
	}
}
