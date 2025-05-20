package datos;

import vectores.Vector;

public class Cuantitativo extends Atributo{
	private Vector valores;
	
	public Cuantitativo() {
		this.nombre = "";
		this.valores = new Vector();
	}
	
	public Cuantitativo(String name) {
		this();
		this.nombre = name;
	}
	
	public Cuantitativo(String name, Double valor) {
		this();
		this.nombre = name;
		valores.add(valor);
	}
	
	public Cuantitativo(String name, Vector valor) {
		this();
		this.nombre = name;
		this.valores = valor;
	}

	public Cuantitativo(Cuantitativo otro) {
		this.nombre = otro.nombre;
		this.valores = otro.valores;
	}
	
	public Vector getValores() {
		return this.valores;
	}
	
	public void setValores(Vector nuevos) {
		this.valores = nuevos;
	}
	
	public double minimo() {
		double minimo = this.valores.get(0);
		for(int i = 0; i < this.valores.size(); ++i) {
			if(minimo > this.valores.get(i)) minimo = this.valores.get(i);
		}
		return minimo;
	}
	
	public double maximo() {
		double maximo = this.valores.get(0);
		for(int i = 0; i < this.valores.size(); ++i) {
			if(maximo < this.valores.get(i)) maximo = this.valores.get(i);
		}
		return maximo;
	}
	
	public double media() {
		double media = 0.0;
		for(int i = 0; i < this.valores.size(); ++i) {
			media += this.valores.get(i);
		}
		return media/this.valores.size();
	}

	public double desviacion() {
		if (valores.size() == 0) return 0.0;
		double media = this.media();
		double suma = 0.0;
		for (int i = 0; i < valores.size(); ++i) {
			suma += Math.pow(valores.get(i) - media, 2);
		}
		return Math.sqrt(suma / valores.size());
	}
	
	public int size() {
		return this.valores.size();
	}

	public void estandarizacion() {
		double media = this.media();
		double desviacion = this.desviacion();
		if (desviacion == 0.0) return; // Evitar división por cero
		for (int i = 0; i < valores.size(); ++i) {
			valores.set(i, (valores.get(i) - media) / desviacion);
		}
	}


	@Override
	public void add(Object valor) {
		valores.add((double) valor);
		
	}
	
	@Override
	public Object getValor(int i) {
		return valores.get(i);
		
	}
	
	@Override
	public void delete(int index) {
		valores.remove(index);
		
	}
	
	@Override
	public String toString() {
		return valores.toString();
		
	}
	
	@Override
	public void clear() {
		valores.clear();
	}

}
