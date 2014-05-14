package uy.edu.ort.arqliv.obligatorio.client.formatter.list;

import java.io.Serializable;
/**
 * 
 * @author rodrigo
 *
 * @param <E>
 * @param <T>
 */
public class Duple<E,T>  implements Serializable{
	
	private static final long serialVersionUID = 857709431699242319L;
	
	private E first;
	private T second;
	
	public E getFirst() {
		return first;
	}
	public void setFirst(E first) {
		this.first = first;
	}
	public T getSecond() {
		return second;
	}
	public void setSecond(T second) {
		this.second = second;
	}
	
	public Duple() {
		super();
		this.first = null;
		this.second = null;
	}
	
	public Duple(E first, T second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "<"+ first +", "+ second +">";
	}

	/**
	 * Retorna una dupla en formato String de la forma:
	 * "leftMarker + v0 + separator + v + rightMarker"
	 * @param leftMarker
	 * @param separator
	 * @param rigthMarker
	 * @return
	 */
	public String toString(String leftMarker, String separator, String rigthMarker) {
		return leftMarker + first + separator + second + rigthMarker;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Duple)) {
			return false;
		}
		Duple<E, T> other = (Duple<E, T>)obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}
}