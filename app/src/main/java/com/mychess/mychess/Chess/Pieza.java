package com.mychess.mychess.Chess;



import java.util.ArrayList;


public abstract class Pieza
{
	private boolean Blanco;
	
	private Ubicacion ubi;
	
	public Pieza(boolean Blanco, Ubicacion ubi)
	{
		this.Blanco = Blanco;
		this.ubi = ubi;
	}
	
	public void setUbicacion(Ubicacion ubi)
	{
		this.ubi = ubi;
	}
	
	public Ubicacion getUbicacion()
	{
		return ubi;
	}
	
	public boolean getColor()
	{
		return Blanco;
	}
	
	
	
	public abstract ArrayList<Ubicacion> getMovimientos(EstadoTablero tablero);
	
	public abstract void MoverA(Ubicacion moverUbi);
	
	public static Pieza clone(Pieza Seleccionada)
	{
		if(Seleccionada instanceof Rey)
		{
			Rey pieza = new Rey(Seleccionada.getColor(), Seleccionada.getUbicacion());
			pieza.setHasMoved(((Rey)Seleccionada).getHasMoved());
			pieza.setIsChecked(((Rey)Seleccionada).Jaque());
			return pieza;
		}
		else if(Seleccionada instanceof Dama)
			return new Dama(Seleccionada.getColor(), Seleccionada.getUbicacion());
		else if(Seleccionada instanceof Peon)
		{
			Peon pieza = new Peon(Seleccionada.getColor(), Seleccionada.getUbicacion());
			pieza.setDoubleMove(((Peon)Seleccionada).getDoubleMove());
			return pieza;
		}
		else if(Seleccionada instanceof Torre)
		{
			Torre pieza = new Torre(Seleccionada.getColor(), Seleccionada.getUbicacion());
			pieza.setHasMoved(((Torre)Seleccionada).getHasMoved());
			return pieza;
		}
		else if(Seleccionada instanceof Alfil)
			return new Alfil(Seleccionada.getColor(), Seleccionada.getUbicacion());
		else if(Seleccionada instanceof Caballo)
			return new Caballo(Seleccionada.getColor(), Seleccionada.getUbicacion());
		else
			return null;
	}
	public String toString()
	{
		if(Blanco)
			return "Blanco";
		else
			return "Negro";
	}
}
