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


public class Peon extends Pieza
{
	private boolean doubleMove;
	
	public Peon(boolean Blanco, Ubicacion loc)
	{
		super(Blanco, loc);
	}
	
	
	public boolean getDoubleMove()
	{
		return doubleMove;
	}
	
	public void setDoubleMove(boolean input)
	{
		doubleMove=input;
	}
	
	public ArrayList<Ubicacion> getMovimientos(EstadoTablero board)
	{
		ArrayList<Ubicacion> possibleMoves = new ArrayList<Ubicacion>();
		int y = getUbicacion().getFila();
		int x = getUbicacion().getCol();
		
		Ubicacion whiteOne = new Ubicacion(y-1,x);
		Ubicacion whiteTwo = new Ubicacion(y-2,x);
		Ubicacion whiteLeft = new Ubicacion(y-1,x-1);
		Ubicacion whiteRight = new Ubicacion(y-1,x+1);
		
		Ubicacion enPassantLeft = new Ubicacion(y,x-1);
		Ubicacion enPassantRight = new Ubicacion(y,x+1);
				
		Ubicacion blackOne = new Ubicacion(y+1,x);
		Ubicacion blackTwo = new Ubicacion(y+2,x);
		Ubicacion blackLeft = new Ubicacion(y+1,x-1);
		Ubicacion blackRight = new Ubicacion(y+1,x+1);
		
		if(getColor())
		{
			if(y!=0)
			{
				if(board.isValid(whiteOne) && board.isEmpty(whiteOne))
					possibleMoves.add(whiteOne);
				if(board.isValid(whiteTwo) && getUbicacion().getFila()==6 && board.isEmpty(whiteTwo) && board.isEmpty(whiteOne))
					possibleMoves.add(whiteTwo);
				if(board.isValid(whiteLeft) && !board.isEmpty(whiteLeft) && !board.isPieceWhite(whiteLeft))
					possibleMoves.add(whiteLeft);
				if(board.isValid(whiteRight) && !board.isEmpty(whiteRight) && !board.isPieceWhite(whiteRight))
					possibleMoves.add(whiteRight);
				if(board.isValid(whiteRight) && board.isEmpty(whiteRight) && board.isValid(enPassantRight) && !board.isEmpty(enPassantRight) && !board.isPieceWhite(enPassantRight) && board.getState()[enPassantRight.getFila()][enPassantRight.getCol()] instanceof Peon)
					if(enPassantRight.getFila()==3 && ((Peon)board.getState()[enPassantRight.getFila()][enPassantRight.getCol()]).getDoubleMove())
						possibleMoves.add(whiteRight);
				if(board.isValid(whiteLeft) && board.isEmpty(whiteLeft) && board.isValid(enPassantLeft) && !board.isEmpty(enPassantLeft) && !board.isPieceWhite(enPassantLeft) && board.getState()[enPassantLeft.getFila()][enPassantLeft.getCol()] instanceof Peon)
					if(enPassantLeft.getFila()==3 && ((Peon)board.getState()[enPassantLeft.getFila()][enPassantLeft.getCol()]).getDoubleMove())
						possibleMoves.add(whiteLeft);
			}
		}
		else
		{
			if(y!=7)
			{
				if(board.isValid(blackOne) && board.isEmpty(blackOne))
					possibleMoves.add(blackOne);
				if(board.isValid(blackTwo) && getUbicacion().getFila()==1 && board.isEmpty(blackTwo) && board.isEmpty(blackOne))
					possibleMoves.add(blackTwo);
				if(board.isValid(blackLeft) && !board.isEmpty(blackLeft) && board.isPieceWhite(blackLeft))
					possibleMoves.add(blackLeft);
				if(board.isValid(blackRight) && !board.isEmpty(blackRight) && board.isPieceWhite(blackRight))
					possibleMoves.add(blackRight);
				if(board.isValid(blackRight) && board.isEmpty(blackRight) && board.isValid(enPassantRight) && !board.isEmpty(enPassantRight) && board.isPieceWhite(enPassantRight) && board.getState()[enPassantRight.getFila()][enPassantRight.getCol()] instanceof Peon)
					if(enPassantRight.getFila()==4 && ((Peon)board.getState()[enPassantRight.getFila()][enPassantRight.getCol()]).getDoubleMove())
						possibleMoves.add(blackRight);
				if(board.isValid(blackLeft) && board.isEmpty(blackLeft) && board.isValid(enPassantLeft) && !board.isEmpty(enPassantLeft) && board.isPieceWhite(enPassantLeft) && board.getState()[enPassantLeft.getFila()][enPassantLeft.getCol()] instanceof Peon)
					if(enPassantLeft.getFila()==4 && ((Peon)board.getState()[enPassantLeft.getFila()][enPassantLeft.getCol()]).getDoubleMove())
						possibleMoves.add(blackLeft);
			}
		}
		return possibleMoves;
	}
	
	public void MoverA(Ubicacion moveLoc)
	{
		if((int)Math.abs(getUbicacion().getFila()-moveLoc.getFila()) > 1)
			doubleMove = true;
		else
			doubleMove = false;
		setUbicacion(moveLoc);
	}
	
	public String toString()
	{
		return super.toString()+" Peon";
	}
}
