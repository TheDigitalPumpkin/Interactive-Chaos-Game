package ponto;

public class PontoColorido extends Ponto
{
	private int colorIndex;
	
	public PontoColorido(int x, int y, int idx)
	{
		super(x, y);
		colorIndex = idx;
	}
	
	public PontoColorido(Ponto p, int idx)
	{
		super(p.getX(), p.getY());
		colorIndex = idx;
	}
	
	public int getColorIndex()
	{
		return colorIndex;
	}
}
