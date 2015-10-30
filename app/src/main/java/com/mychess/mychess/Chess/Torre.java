package com.mychess.mychess.Chess;


import java.util.ArrayList;
public class Torre extends Pieza
{
	
	private boolean FueMovida;
	
	public Torre(boolean Blanco, Ubicacion loc)
	{
		super(Blanco, loc);
		
		FueMovida=false;
	}
	
	
	public boolean getHasMoved()
	{
		return FueMovida;
	}
	
	public void setHasMoved(boolean input)
	{
		FueMovida=input;
	}
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
		int x = getUbicacion().getCol();
		
		boolean[] isRowBlocked = new boolean[4];
		
		Ubicacion[] locs = new Ubicacion[32];
		
		for(int z=1; z<=32; z++)
		{
			if(z<=8)
				locs[z-1] = new Ubicacion(y-z%8, x);
			else if(z<=16)
				locs[z-1] = new Ubicacion(y, x+z%8);
			else if(z<=24)
				locs[z-1] = new Ubicacion(y+z%8, x);
			else
				locs[z-1] = new Ubicacion(y, x-z%8);
		}

		if(getColor())
		{
			for(int i=0; i<locs.length; i++)
				if(board.isValid(locs[i]) && !isRowBlocked[i/8] && (board.isEmpty(locs[i]) || !board.isPieceWhite(locs[i])))
				{
					possibleMoves.add(locs[i]);
					if(!board.isEmpty(locs[i]) && !board.isPieceWhite(locs[i]))
						isRowBlocked[i/8]=true;
				}
				else
					isRowBlocked[i/8]=true;
		}
		else
		{
			for(int i=0; i<locs.length; i++)
				if(board.isValid(locs[i]) && !isRowBlocked[i/8] && (board.isEmpty(locs[i]) || board.isPieceWhite(locs[i])))
				{
					possibleMoves.add(locs[i]);
					if(!board.isEmpty(locs[i]) && board.isPieceWhite(locs[i]))
						isRowBlocked[i/8]=true;
				}
				else
					isRowBlocked[i/8]=true;
		}
		return possibleMoves;
	}
	
	public void MoverA(Ubicacion moveLoc)
	{
		setUbicacion(moveLoc);
		FueMovida=true;
	}
	
	public String toString()
	{
		return super.toString()+" Torre";
	}
}
