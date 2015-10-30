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


public class Ubicacion {
	
	int Fila;
	int Columna;
	
	public Ubicacion()
	{
		Fila = 0;
		Columna = 0;
	}
	
	public Ubicacion(int Fila, int Columna)
	{
		this.Fila = Fila;
		this.Columna = Columna;
	}
	
	public int getFila()
	{
		return Fila;
	}
	
	public int getCol()
	{
		return Columna;
	}
	
    public boolean equals(Ubicacion ubi)
    {
        return ubi.getFila() == this.getFila() && ubi.getCol() == this.getCol();
    }
    
    public String toString()
    {
    	int col = getCol();
    	int row = getFila();
    	char pngCol;
    	int pngFila;
    	switch (col)
    	{
    		case 0:
    			pngCol = 'a'; break;
    		case 1:
    			pngCol = 'b'; break;
    		case 2:
    			pngCol = 'c'; break;
    		case 3:
    			pngCol = 'd'; break;
    		case 4:
    			pngCol = 'e'; break;
    		case 5:
    			pngCol = 'f'; break;
    		case 6:
    			pngCol = 'g'; break;
    		case 7:
    			pngCol = 'h'; break;
    		default:
    			pngCol = ' '; break;
    	}
    	
    	switch (row)
    	{
    		case 0:
    			pngFila = 8; break;
    		case 1:
    			pngFila = 7; break;
    		case 2:
    			pngFila = 6; break;
    		case 3:
    			pngFila = 5; break;
    		case 4:
    			pngFila = 4; break;
    		case 5:
    			pngFila = 3; break;
    		case 6:
    			pngFila = 2; break;
    		case 7:
    			pngFila = 1; break;
    		default:
    			pngFila = 0; break;
    	}
    	
    	return ""+pngCol+""+pngFila;
    }
}
