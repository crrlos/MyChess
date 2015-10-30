package com.mychess.mychess.Chess;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thoni
 */

import java.util.*;

public class Rey extends Pieza
{
	
	private boolean Jaque;
	private boolean Movida;
	
	public Rey(boolean Blanco, Ubicacion ubi)
	{
		super(Blanco, ubi);
		
		Movida=false;
		
		Jaque=false;		
	}
	
	public boolean getHasMoved()
	{
		return Movida;
	}
	
	public void setHasMoved(boolean input)
	{
		Movida=input;
	}
	
	public boolean Jaque()
	{
		return Jaque;
	}
	
	public void setIsChecked(boolean check)
	{
		Jaque=check;
	}
	
	public void updateIsChecked(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleChecks = getPossibleChecks(board);
		
		for(int a=0; a<possibleChecks.size(); a++)
			if(possibleChecks.get(a).equals(getUbicacion()))
			{
				Jaque = true;
				break;
			}
			else
				Jaque=false;
	}
	
	public ArrayList<Ubicacion> getPossibleChecks(EstadoTablero board)
	{
		ArrayList<Ubicacion> checks = new ArrayList<Ubicacion>();
		for(int y=0; y<board.getState().length; y++)
			for(int x=0; x<board.getState().length; x++)
				if(board.getState()[y][x]!=null && board.getState()[y][x].getColor()!=getColor())
				{
					ArrayList<Ubicacion> tempChecks = board.getState()[y][x].getMovimientos(board);
					if(board.getState()[y][x] instanceof Rey)
					{
						for(int a=0; a<tempChecks.size(); a++)
							if(Math.abs((board.getState()[y][x].getUbicacion().getCol())-(tempChecks.get(a).getCol()))==2)
							{
								tempChecks.remove(a);
								a--;
							}
					}
					for(int a=0; a<tempChecks.size(); a++)
						checks.add(tempChecks.get(a));
				}
		return checks;
	}
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
		int x = getUbicacion().getCol();
		
		Ubicacion[] ubics = new Ubicacion[10];
		
		ubics[0] = new Ubicacion(y-1,x-1);
		ubics[1] = new Ubicacion(y-1,x);
		ubics[2] = new Ubicacion(y-1,x+1);
		ubics[3] = new Ubicacion(y,x-1);
		ubics[4] = new Ubicacion(y,x+1);
		ubics[5] = new Ubicacion(y+1,x+1);
		ubics[6] = new Ubicacion(y+1,x);
		ubics[7] = new Ubicacion(y+1,x-1);
		
		ubics[8] = new Ubicacion(y,x-2);
		ubics[9] = new Ubicacion(y,x+2);
		
		for(Ubicacion z: ubics)
			if(board.isValid(z) && (board.isEmpty(z) || board.isPieceWhite(z)!=getColor()))
				possibleMoves.add(z);
		
		return possibleMoves;
	}

	public void MoverA(Ubicacion moveLoc)
	{
		setUbicacion(moveLoc);
		Movida=true;
	}
	
	public String toString()
	{
		return super.toString()+" Rey";
	}
}
