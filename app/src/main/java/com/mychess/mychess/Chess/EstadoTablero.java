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

import java.util.ArrayList;


public class EstadoTablero 
{
	private Pieza[][] estadotablero;
	private Pieza capturada;
	private boolean BlancoTurn;
	private ArrayList<Pieza[][]> guardarEstados;
	private int savedStateQueue;
	
	public EstadoTablero()
	{
		estadotablero = new Pieza[8][8];
		guardarEstados = new ArrayList<Pieza[][]>();
		savedStateQueue = 0;
		resetBoardState();
	}
	
        
	public Pieza[][] getState()
	{
		return estadotablero;
	}
	
	public boolean isPieceWhite(Ubicacion ubi)
	{
		return estadotablero[ubi.getFila()][ubi.getCol()].getColor();
	}
	
	public void setTurn(boolean Blanco)
	{
		BlancoTurn = Blanco;
	}
	
	public boolean getTurn()
	{
		return BlancoTurn;
	}
	
	public void resetOtherPawns(Ubicacion currentPiece)
	{
		for(int y=0; y<estadotablero.length; y++)
			for(int x=0; x<estadotablero.length; x++)
			{
				Ubicacion ubi = new Ubicacion(y,x);
				if(isValid(ubi) && !isEmpty(ubi) && estadotablero[y][x] instanceof Peon)
					if(!currentPiece.equals(ubi))
						((Peon)estadotablero[y][x]).setDoubleMove(false);
			}
	}
	
	public int saveState()
	{
		guardarEstados.add(new Pieza[8][8]);
		for(int y=0; y<estadotablero.length; y++)
			for(int x=0; x<estadotablero.length; x++)
				guardarEstados.get(savedStateQueue)[y][x]=Pieza.clone(estadotablero[y][x]);
		savedStateQueue++;
		return savedStateQueue-1;
	}
	
	public void undoMove(int queueNumber)
	{
		for(int y=0; y<estadotablero.length; y++)
			for(int x=0; x<estadotablero.length; x++)
					estadotablero[y][x]=guardarEstados.get(queueNumber)[y][x];
	}
	
	public void moveFrom_To(Ubicacion origen, Ubicacion destino)
	{  
            
           
		estadotablero[origen.getFila()][origen.getCol()].MoverA(destino);
                
		Pieza temp = estadotablero[origen.getFila()][origen.getCol()];
		capturada = estadotablero[destino.getFila()][destino.getCol()];
		estadotablero[origen.getFila()][origen.getCol()] = null;
		estadotablero[destino.getFila()][destino.getCol()] = temp;
		
                
                
                
		if(capturada == null && origen.getCol()!=destino.getCol() && temp instanceof Peon)
			if(temp.getColor())
			{
				Ubicacion EP = new Ubicacion(destino.getFila()+1, destino.getCol());
				if(!isEmpty(EP) && !estadotablero[EP.getFila()][EP.getCol()].getColor() && estadotablero[EP.getFila()][EP.getCol()] instanceof Peon)
					estadotablero[EP.getFila()][EP.getCol()] = null;
			}
			else
			{
				Ubicacion EP = new Ubicacion(destino.getFila()-1, destino.getCol());
				if(!isEmpty(EP) && estadotablero[EP.getFila()][EP.getCol()].getColor() && estadotablero[EP.getFila()][EP.getCol()] instanceof Peon)
					estadotablero[EP.getFila()][EP.getCol()] = null;
			}
		if(capturada == null && temp instanceof Rey)
			if(origen.getCol()-destino.getCol() == 2)
			{
				estadotablero[destino.getFila()][destino.getCol()-2].MoverA(new Ubicacion(destino.getFila(), destino.getCol()+1));
				moveFrom_To(new Ubicacion(destino.getFila(), destino.getCol()-2), new Ubicacion(destino.getFila(), destino.getCol()+1));
			}
			else if(origen.getCol()-destino.getCol() == -2)
			{
				estadotablero[destino.getFila()][destino.getCol()+1].MoverA(new Ubicacion(destino.getFila(), destino.getCol()-1));
				moveFrom_To(new Ubicacion(destino.getFila(), destino.getCol()+1), new Ubicacion(destino.getFila(), destino.getCol()-1));	
			}
	}
		
	public boolean isValid(Ubicacion ubi)
	{
		if( (ubi.getFila() < estadotablero.length && ubi.getFila() >= 0) && (ubi.getCol() < estadotablero.length && ubi.getCol() >=0) )
			return true;
		return false;
	}
	
	public boolean isEmpty(Ubicacion ubi)
	{
		if(!isValid(ubi))
			return false;
		if(estadotablero[ubi.getFila()][ubi.getCol()]==null)
			return true;
		return false;
	}
	
	public void addQueen(Ubicacion ubi)
	{
		estadotablero[ubi.getFila()][ubi.getCol()] = new Dama(estadotablero[ubi.getFila()][ubi.getCol()].getColor(), new Ubicacion(ubi.getFila(),ubi.getCol()));
	}
	
	public void addRook(Ubicacion ubi)
	{
		estadotablero[ubi.getFila()][ubi.getCol()] = new Torre(estadotablero[ubi.getFila()][ubi.getCol()].getColor(), new Ubicacion(ubi.getFila(),ubi.getCol()));
	}
	
	public void addBishop(Ubicacion ubi)
	{
		estadotablero[ubi.getFila()][ubi.getCol()] = new Alfil(estadotablero[ubi.getFila()][ubi.getCol()].getColor(), new Ubicacion(ubi.getFila(),ubi.getCol()));
	}
	
	public void addKnight(Ubicacion ubi)
	{
		estadotablero[ubi.getFila()][ubi.getCol()] = new Rey(estadotablero[ubi.getFila()][ubi.getCol()].getColor(), new Ubicacion(ubi.getFila(),ubi.getCol()));
	}
	
	public void resetBoardState()
	{
		capturada=null;
		guardarEstados = new ArrayList<Pieza[][]>();
		savedStateQueue = 0;
		for(int y=0; y<estadotablero.length; y++)
			for(int x=0; x<estadotablero.length; x++)
			{
				if(y==0)
					if(x==0 || x==7)
						estadotablero[y][x] = new Torre(false, new Ubicacion(y,x));
					else if(x==1 || x==6)
						estadotablero[y][x] = new Caballo(false, new Ubicacion(y,x));
					else if(x==2 || x==5)
						estadotablero[y][x] = new Alfil(false, new Ubicacion(y,x));
					else if(x==3)
						estadotablero[y][x] = new Dama(false, new Ubicacion(y,x));
					else
						estadotablero[y][x] = new Rey(false, new Ubicacion(y,x));
				else if(y==1)
					estadotablero[y][x] = new Peon(false, new Ubicacion(y,x));
				else if(y==6)
					estadotablero[y][x] = new Peon(true, new Ubicacion(y,x));
				else if(y==7)
					if(x==0 || x==7)
						estadotablero[y][x] = new Torre(true, new Ubicacion(y,x));
					else if(x==1 || x==6)
						estadotablero[y][x] = new Caballo(true, new Ubicacion(y,x));
					else if(x==2 || x==5)
						estadotablero[y][x] = new Alfil(true, new Ubicacion(y,x));
					else if(x==3)
						estadotablero[y][x] = new Dama(true, new Ubicacion(y,x));
					else
						estadotablero[y][x] = new Rey(true, new Ubicacion(y,x));
				else
					estadotablero[y][x]=null;
			}
	}
}
