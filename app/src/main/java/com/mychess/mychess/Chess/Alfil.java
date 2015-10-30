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

public class Alfil extends Pieza
{
	
	
	public Alfil(boolean Blanco, Ubicacion loc)
	{
		super(Blanco, loc);
	}
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
                
		int x = getUbicacion().getCol();
		
		boolean[] isRowBlocked = new boolean[4];
		
		Ubicacion[] ubics = new Ubicacion[32];
		
		for(int z=1; z<=32; z++)
		{
			if(z<=8)
				ubics[z-1] = new Ubicacion(y-z%8, x-z%8);
			else if(z<=16)
				ubics[z-1] = new Ubicacion(y-z%8, x+z%8);
			else if(z<=24)
				ubics[z-1] = new Ubicacion(y+z%8, x+z%8);
			else
				ubics[z-1] = new Ubicacion(y+z%8, x-z%8);
		}

		if(getColor())
		{
			for(int i=0; i<ubics.length; i++)
				if(board.isValid(ubics[i]) && !isRowBlocked[i/8] && (board.isEmpty(ubics[i]) || !board.isPieceWhite(ubics[i])))
				{
					possibleMoves.add(ubics[i]);
					if(!board.isEmpty(ubics[i]) && !board.isPieceWhite(ubics[i]))
						isRowBlocked[i/8]=true;
				}
				else
					isRowBlocked[i/8]=true;
		}
		else
		{
			for(int i=0; i<ubics.length; i++)
				if(board.isValid(ubics[i]) && !isRowBlocked[i/8] && (board.isEmpty(ubics[i]) || board.isPieceWhite(ubics[i])))
				{
					possibleMoves.add(ubics[i]);
					if(!board.isEmpty(ubics[i]) && board.isPieceWhite(ubics[i]))
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
		return super.toString()+" Alfil";
	}
}
