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


public class Chess {



	public boolean JuegoTerminado;
	public Ubicacion selectedPiece, promotedPiece,porigen,pdestino;

	public EstadoTablero Eboard;
	public boolean estadovalido;
	private boolean BlancoTurn;
	private int estado;
	private boolean jaque;
	private boolean jaquemate;
	private boolean valido,tablas;

	public ArrayList<Integer> undoMoves;
	public ArrayList<Integer> wasPieceTaken;
	public ArrayList<Ubicacion> moves,clonemoves;
	public ArrayList<String> CoorDestino,posiblesmoves;
	public boolean getJuegoTerminado()
	{
		return JuegoTerminado;
	}

	public boolean isTablas() {
		return tablas;
	}

	public void setTablas(boolean tablas) {
		this.tablas = tablas;
	}



	public void setJuegoTerminado(boolean estadodeljuego)
	{
		JuegoTerminado=estadodeljuego;

	}

	public boolean isBlancoTurn() {
		return BlancoTurn;
	}

	public void setBlancoTurn(boolean blancoTurn) {
		BlancoTurn = blancoTurn;
	}

	public boolean isJaque() {
		return jaque;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}


	public void setJaque(boolean jaque) {
		this.jaque = jaque;
	}

	public boolean isJaquemate() {
		return jaquemate;
	}

	public void setJaquemate(boolean jaquemate) {
		this.jaquemate = jaquemate;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}






	public Chess(){


		Eboard = new EstadoTablero(); //ESTABLECIENDO ESTADO DEL TABLERO

		moves = new ArrayList<Ubicacion>();
		clonemoves=new ArrayList<Ubicacion>();
		CoorDestino=new  ArrayList<String>();
		posiblesmoves=new  ArrayList<String>();
		estado = 0;



	}

	public void newGame()
	{

		Eboard.resetBoardState();
		selectedPiece = null;
		porigen=null;
		pdestino=null;
		BlancoTurn = true;
		Eboard.setTurn(BlancoTurn);


		//gui.enableSide(BlancoTurn);

		undoMoves = new ArrayList<Integer>();
		wasPieceTaken = new ArrayList<Integer>();


		// System.out.println("Juego iniciado");

	}
	public boolean getTurn()
	{
		return BlancoTurn;

	}





	public ArrayList<Ubicacion> getMovimientos(Ubicacion piece)
	{


		ArrayList<Ubicacion> allMoves = Eboard.getState()[piece.getFila()][piece.getCol()].getMovimientos(Eboard);
		for(int a=0; a<allMoves.size(); a++)
			if(!isLegal(piece, allMoves.get(a), 0))
			{
				allMoves.remove(a);
				a--;
			}
		return allMoves;
	}
	public void checkGameOver()
	{


		ArrayList<Ubicacion> moves = new ArrayList<Ubicacion>();
		ArrayList<Ubicacion> temp = new ArrayList<Ubicacion>();
		for(int y=0; y<Eboard.getState().length; y++)
			for(int x=0; x<Eboard.getState().length; x++)
			{
				if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x].getColor()==BlancoTurn)
				{
					temp = getMovimientos(new Ubicacion(y,x));
					for(Ubicacion z : temp)
						moves.add(z);
					if(moves.size()>0)
						break;
				}
				if(moves.size()>0)
					break;
			}
		if(moves.size()==0)
		{
			boolean isCheck = true;
			for(int y=0; y<Eboard.getState().length; y++)
				for(int x=0; x<Eboard.getState().length; x++)
					if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()==BlancoTurn)
					{
						isCheck = ((Rey)Eboard.getState()[y][x]).Jaque();
						break;
					}
			if(isCheck)
			{


				if(BlancoTurn)
				{


					//   System.out.println("JAQUE MATE");
					setJaquemate(true);

				}
				else
				{
					//     System.out.println("JAQUE MATE");
					setJaquemate(true);
				}
//;}
//
			}
			else
			{
				//Tablas



			}
		}

		boolean BlancoKnight = false;
		boolean BlancoBishop = false;
		boolean isBlackKnight = false;
		boolean isBlackBishop = false;
		boolean isDraw = true;
		for(int y=0; y<Eboard.getState().length; y++)
			for(int x=0; x<Eboard.getState().length; x++)
			{
				if(Eboard.getState()[y][x]!=null && (Eboard.getState()[y][x] instanceof Dama || Eboard.getState()[y][x] instanceof Torre || Eboard.getState()[y][x] instanceof Peon))
				{
					isDraw=false;
					break;
				}
				else if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Alfil && Eboard.getState()[y][x].getColor()==BlancoTurn)
					BlancoBishop=true;
				else if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()==BlancoTurn)
					BlancoKnight = true;
				else if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Alfil && Eboard.getState()[y][x].getColor()!=BlancoTurn)
					isBlackBishop=true;
				else if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()!=BlancoTurn)
					isBlackKnight = true;
			}
		if(isDraw)
			if(BlancoBishop && BlancoKnight || isBlackBishop && isBlackKnight || isBlackBishop && BlancoBishop || isBlackKnight && BlancoKnight)
				isDraw = false;
		if(isDraw)
		{


		}
	}

public int mover(String origen, String destino)
{
	int estado=0;
	String cOrigen = String.valueOf(origen.charAt(0));
        String fOrigen =String.valueOf(origen.charAt(1));
        
	String cDestino = String.valueOf(destino.charAt(0));
	String  fDestino = String.valueOf(destino.charAt(1));
        
	ConvertirAlgebraica co=new ConvertirAlgebraica(cOrigen,Integer.parseInt(fOrigen));
	ConvertirAlgebraica cd=new ConvertirAlgebraica(cDestino,Integer.parseInt(fDestino));
	Ubicacion origenUbicacion=new Ubicacion(co.CoordenadaFila(),co.CoordenadaColumn());
	Ubicacion destinoUbicacion = new Ubicacion(cd.CoordenadaFila(),cd.CoordenadaColumn());

	if (Eboard.isEmpty(origenUbicacion))
	{

		return 1;
	}
	else {
		if (getTurn() != isPiezaBlanca(origenUbicacion)) {

			return 2;
		} else {

			if (validarMovimientos(origenUbicacion, destinoUbicacion))
			{

				procesoPrincipal(origenUbicacion,destinoUbicacion);
                                
                                
				if(isJaquemate())
				{
					return 3;
				}
			}
			else {
				return 4;

			}
		}
	}
return estado;


}
	public void checkPromotion(Ubicacion ubicacion)
	{
		if(Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()] instanceof Peon && (ubicacion.getFila()==0 || ubicacion.getFila()==7))
		{
			//MENSAJE PARA PROMOCIONAR PIEZA , MOSTRAR OPCIONES.
			promotedPiece = ubicacion;
		}
	}


	public boolean isLegal(Ubicacion start, Ubicacion end, int restraint)
	{


		boolean isLegal = true;
		boolean complete = true;
		int queue;

		if(restraint!=1 && Eboard.getState()[start.getFila()][start.getCol()] instanceof Rey)
		{
			if(start.getFila()==end.getFila() && Math.abs(start.getCol()-end.getCol())==2)
			{
				((Rey)Eboard.getState()[start.getFila()][start.getCol()]).updateIsChecked(Eboard);
				if(((Rey)Eboard.getState()[start.getFila()][start.getCol()]).getHasMoved() || ((Rey)Eboard.getState()[start.getFila()][start.getCol()]).Jaque())
				{
					isLegal = false;
					complete = false;
				}
				else if(end.getCol()==2 && Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()-1)) && Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()-2)) && Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()-3)) && !Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()-4)) && Eboard.getState()[start.getFila()][start.getCol()-4] instanceof Torre && !((Torre)Eboard.getState()[start.getFila()][start.getCol()-4]).getHasMoved())
				{
					if(isLegal(start, new Ubicacion(start.getFila(), start.getCol()-1), 1) && isLegal(start, end, 1))
					{
						isLegal = true;
						complete = false;
					}
					else
					{
						isLegal = false;
						complete = false;
					}
				}
				else if(end.getCol()==6 && Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()+1)) && Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()+2)) && !Eboard.isEmpty(new Ubicacion(start.getFila(),start.getCol()+3)) && Eboard.getState()[start.getFila()][start.getCol()+3] instanceof Torre && !((Torre)Eboard.getState()[start.getFila()][start.getCol()+3]).getHasMoved())
				{
					if(isLegal(start, new Ubicacion(start.getFila(), start.getCol()+1), 1) && isLegal(start, end, 1))
					{
						isLegal = true;
						complete = false;
					}
					else
					{
						isLegal = false;
						complete = false;
					}
				}
				else
				{
					isLegal = false;
					complete = false;
				}
			}
		}

		if(complete)
		{
			queue = Eboard.saveState();
			Eboard.moveFrom_To(start, end);
			for(int y=0; y<Eboard.getState().length; y++)
				for(int x=0; x<Eboard.getState().length; x++)
					if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()==BlancoTurn)
					{
						((Rey)Eboard.getState()[y][x]).updateIsChecked(Eboard);
						isLegal = !((Rey)Eboard.getState()[y][x]).Jaque();
						break;
					}
			Eboard.undoMove(queue);
		}

		return isLegal;
	}

	public void movePiece(Ubicacion ubicacion)
	{



		//   System.out.println("movepiece destino:"+ubicacion.toString());
		//   System.out.println("pieza seleccionada: " +selectedPiece.toString());




		undoMoves.add(Eboard.saveState());

		if(Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()]!=null)
		{
			wasPieceTaken.add(1);
			// System.out.println("fue tomada 1");

		}
		else
		{
			wasPieceTaken.add(0);
			//System.out.println("no hay capturas");

		}


		Eboard.moveFrom_To(selectedPiece, ubicacion);

		BlancoTurn = !BlancoTurn;
		Eboard.setTurn(BlancoTurn);
		selectedPiece=null;

		for(int y=0; y<Eboard.getState().length; y++)
			for(int x=0; x<Eboard.getState().length; x++)
				if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()==BlancoTurn)
				{
					((Rey)Eboard.getState()[y][x]).updateIsChecked(Eboard);
					if(((Rey)Eboard.getState()[y][x]).Jaque())
//
						break;
				}


		if(selectedPiece!=null)
		{

			processMoves();


		}
	}

	public void processMoves()
	{


		boolean isCheck = true;
		moves = getMovimientos(selectedPiece);
		//System.out.println("processMoves:"+ selectedPiece.Columna+" " + selectedPiece.Fila);
		//System.out.println(" algebraica: "+selectedPiece.toString());
		//
		if(moves.size()==0)
		{
			for(int y=0; y<Eboard.getState().length; y++)
				for(int x=0; x<Eboard.getState().length; x++)
					if(Eboard.getState()[y][x]!=null && Eboard.getState()[y][x] instanceof Rey && Eboard.getState()[y][x].getColor()==BlancoTurn)
					{
						isCheck = ((Rey)Eboard.getState()[y][x]).Jaque();
						break;
					}
			if(isCheck)
			{

				//System.out.println("Estas en Jaque!");

			}
			else
			{

				//    System.out.println(	"NO SE PUEDE MOVER ESTA VAINA MOVE OTRA WE.");
			}

		}
		else
		{
			//MOSTRAR POSIBLES MOVES}

			for(Ubicacion z : moves)
			{
				//System.out.println("posibls movs: "+" "+z.toString()+"mov: "+moves);
				CoorDestino.add(z.toString());
			}
		}
	}

	public boolean validarMovimientos(Ubicacion o, Ubicacion d)
	{
		boolean state;
		clonemoves=getMovimientos(o);

		for(Ubicacion z : clonemoves)
		{
			//System.out.println("posibls movs: "+" "+z.toString()+"mov: "+moves);
			posiblesmoves.add(z.toString());
			//   System.out.println(" pm: "+clonemoves);
		}

		state=clonemoves.toString().contains(d.toString());
		return state;
	}

     /*   public void muestraDestino(Ubicacion ubicacion)
        {
        moves = getMovimientos(selectedPiece);
         for(Ubicacion z : moves)
                  {
            CoorDestino.contains(z);
            boolean estado=false;
            estado = CoorDestino.toString().contains(z.toString());
        System.out.println("valido: " + z.toString() + estado );
        //System.out.println(z.getCol() +" "+ z.getFila()+" "+ ubicacion.toString());


                  }
        }*/

	public boolean  validarTurnos(Ubicacion ubicacion){

		boolean estadoTurno=true;

		if((Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()].getColor() && BlancoTurn))
		{
		estadoTurno=true;



		}
		else if( (!Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()].getColor() && !BlancoTurn))
		{

		estadoTurno=false;
		}
		return estadoTurno;
	}
public boolean isPiezaBlanca(Ubicacion origen){




	return Eboard.isPieceWhite(origen);
}

	public Ubicacion coordenadasOrigen( int fila, int columna)
	{
		Ubicacion origen= new Ubicacion(fila,columna);
		return origen;
	}

	public Ubicacion coordenadasDestino( int fila, int columna)
	{
		Ubicacion origen= new Ubicacion(fila,columna);
		return origen;
	}

	


	public void procesoPrincipal(Ubicacion ubicacion, Ubicacion destino)
	{
/*            try{    if(Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()] instanceof Dama )
            {
             //   System.out.println("es la damicela");
            }
            }
        catch(Exception e)
        {} */

		// System.out.println("processOne:"+ubicacion.Columna +""+ ubicacion.Fila+" "+ubicacion.toString());

		if(Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()]!=null)
		{
			if((Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()].getColor() && BlancoTurn) || (!Eboard.getState()[ubicacion.getFila()][ubicacion.getCol()].getColor() && !BlancoTurn))
			{
				selectedPiece = ubicacion; // se especifica la ubicacion de la pieza seleccionada
				porigen=ubicacion;
				processMoves(); //verifica jaques y mov posibles
				if (BlancoTurn==true)
				{
					//  System.out.println("TURNO BLANCO");
				}
				else
				{
					// System.out.println("TURNO NEGRO");
				}
				// System.out.println("else 0");
			}
			else
			{
                 /*  try{  if(cont==true)
                     {setValido(true);
                      //System.out.println("valido");
                           System.out.println(isValido());}
                   else if(cont==false){
                       setValido(false);
                      // System.out.println("no valido "+ubicacion.toString() + " " + CoorDestino);
                        System.out.println(isValido());
                   }}catch(Exception e )
                   {}*/
				movePiece(ubicacion);
				// System.out.println(ubicacion.toString());
				Eboard.resetOtherPawns(ubicacion);
				checkPromotion(ubicacion);

				checkGameOver();
			}

		}
		/*else
		{*/
		// System.out.println("else 2");
		movePiece(destino);
		//muestraDestino(ubicacion);
		Eboard.resetOtherPawns(destino);
		checkPromotion(destino);
		checkGameOver();
		/*}*/
	}
}