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
public class ConvertirAlgebraica {
 
    private String Scolumna;
  
    private int ifila;
   
    
    public ConvertirAlgebraica( String c,int f){
    
        
        this.Scolumna=c;
        this.ifila=f;
    }
    public ConvertirAlgebraica( ){
    
        
       
    }
    
    public String getColumna(){
    
        
    return Scolumna; 
    }
    
    public int getFila(){
    return ifila;
    }
    
    
    
    public int CoordenadaColumn(){
        
        
        String col = getColumna();
        
     
    	int coordCol=0;
    	
    	switch (col)
    	{
    		case "a":
    			coordCol = 0; break;
                case "b":
    			coordCol = 1; break;
    		case "c":
    			coordCol = 2; break;
    		case "d":
    			coordCol = 3; break;
    		case "e":
    			coordCol = 4; break;
    		case "f":
    			coordCol = 5; break;
    		case "g":
    			coordCol = 6; break;
    		case "h":
    			coordCol = 7; break;
    		default:
    			break;
    	}
    	
    
             return coordCol;
    }
    
    
    public int CoordenadaFila(){
        
        
       int fila = getFila();
        
     
    	int coordFila=0;
    	
    	switch (fila)
    	{
    		case 1:
    			coordFila = 7; break;
                case 2:
    			coordFila = 6; break;
    		case 3:
    			coordFila = 5; break;
    		case 4:
    			coordFila = 4; break;
    		case 5:
    			coordFila = 3; break;
    		case 6:
    			coordFila = 2; break;
    		case 7:
    			coordFila = 1; break;
    		case 8:
    			coordFila = 0; break;
    		default:
    			break;
    	}
    	
             return coordFila;
    }
    
    
}
    
    

