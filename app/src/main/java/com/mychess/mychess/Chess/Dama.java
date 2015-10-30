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
;
import java.util.*;

public class Dama extends Pieza
{
	
	
	public Dama(boolean Blanco, Ubicacion loc)
	{
		super(Blanco, loc);
	}
	
	
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
		int x = getUbicacion().getCol();
		
		boolean[] isRowBlocked = new boolean[8];
		
		Ubicacion[] locs = new Ubicacion[64];
		
		for(int z=1; z<=64; z++)
		{
			if(z<=8)
				locs[z-1] = new Ubicacion(y-z%8, x-z%8);
			else if(z<=16)
				locs[z-1] = new Ubicacion(y-z%8, x);
			else if(z<=24)
				locs[z-1] = new Ubicacion(y-z%8, x+z%8);
			else if(z<=32)
				locs[z-1] = new Ubicacion(y, x+z%8);
			else if(z<=40)
				locs[z-1] = new Ubicacion(y+z%8, x+z%8);
			else if(z<=48)
				locs[z-1] = new Ubicacion(y+z%8, x);
			else if(z<=56)
				locs[z-1] = new Ubicacion(y+z%8, x-z%8);
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
	}
	
	public String toString()
	{
		return super.toString()+" Dama";
	}
}
