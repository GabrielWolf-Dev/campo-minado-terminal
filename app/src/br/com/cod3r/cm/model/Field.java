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
}
