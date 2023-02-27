package br.com.cod3r.cm.model;

import java.util.List;

import br.com.cod3r.cm.exception.ExplosionException;

import java.util.ArrayList;

public class Field {
	private final int line;
	private final int column;
	
	private boolean open;
	private boolean undermined;
	private boolean marked;
	
	private List<Field> neighbors = new ArrayList<>();
	
	public Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public boolean addNeighbor(Field neighbor) {
		boolean differentLine = line != neighbor.line;
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentLine && differentColumn;
		
		int deltaLine = Math.abs(line - neighbor.line);
		int deltaColumn = Math.abs(column - neighbor.column);
		int generalDelta = deltaColumn + deltaLine;
		
		if(generalDelta == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(generalDelta == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else {
			return false;
		}
	}
	
	void handleMarked() {
		if(!open) {
			marked = !marked;
		}
	}
	
	boolean open() {
		if(!open && !marked) {
			open = true;
			
			if(undermined) {
				throw new ExplosionException();
			}
			
			if(neighborSecure()) {
				neighbors.forEach(n -> n.open());
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	boolean neighborSecure() {
		return neighbors.stream().noneMatch(n -> n.undermined);
	}
	
	void undermine() {
		undermined = true;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isClose() {
		return !open;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean goalReached() {
		boolean unraveled = !undermined && open;
		boolean protect = undermined && marked;
		
		return unraveled || protect;
	}
	
	long mineNeighbor() {
		return neighbors.stream().filter(n -> n.undermined).count();
	}
	
	void restart() {
		open = false;
		undermined = false;
		marked = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		} else if(open && undermined) {
			return "*";
		} else if (open && mineNeighbor() > 0) {
			return Long.toString(mineNeighbor());
		} else if(open) {
			return " ";
		} else {
			return "?";
		}
	}
}
