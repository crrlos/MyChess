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

public class Caballo extends Pieza
{
	
	
	public Caballo(boolean Blanco, Ubicacion loc)
	{
		super(Blanco, loc);
	}
	
	
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
		int x = getUbicacion().getCol();
		
		Ubicacion[] locs = new Ubicacion[8];
		locs[0] = new Ubicacion(y-1,x-2);
		locs[1] = new Ubicacion(y-2,x-1);
		locs[2] = new Ubicacion(y-2,x+1);
		locs[3] = new Ubicacion(y-1,x+2);
		locs[4] = new Ubicacion(y+1,x+2);
		locs[5] = new Ubicacion(y+2,x+1);
		locs[6] = new Ubicacion(y+2,x-1);
		locs[7] = new Ubicacion(y+1,x-2);
		
		if(getColor())
		{
			for(Ubicacion z: locs)
				if(board.isValid(z) && (board.isEmpty(z) || !board.isPieceWhite(z)))
					possibleMoves.add(z);
		}
		else
		{
			for(Ubicacion z: locs)
				if(board.isValid(z) && (board.isEmpty(z) || board.isPieceWhite(z)))
					possibleMoves.add(z);
		}
		return possibleMoves;
	}
	
	public void MoverA(Ubicacion moveLoc)
	{
		setUbicacion(moveLoc);
	}
	
	public String toString()
	{
		return super.toString()+" Caballo";
	}
}
