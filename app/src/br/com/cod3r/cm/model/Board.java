package br.com.cod3r.cm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.exception.ExplosionException;

public class Board {
	private int lines;
	private int columns;
	private int mines;
	
	private final List<Field> fields = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		raffleMines();
	}
	
	public void open(int line, int column) {
		try {
			fields.parallelStream()
				.filter(f -> f.getLine() == line && f.getColumn() == column)
				.findFirst()
				.ifPresent(f -> f.open());
		} catch(ExplosionException e) {
			fields.forEach(f -> f.setOpen(true));
			throw e;
		}
	}
	
	public void handleMarked(int line, int column) {
		fields.parallelStream()
			.filter(f -> f.getLine() == line && f.getColumn() == column)
			.findFirst()
			.ifPresent(f -> f.handleMarked());
	}

	private void generateFields() {
		for(int line = 0; line < lines; line++) {
			for(int column = 0; column < columns; column++) {
				fields.add(new Field(line, column));
			}
		}
	}
	
	private void associateNeighbors() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbor(f2);
			}
		}
	}
	
	private void raffleMines() {
		long armedMines = 0;
		Predicate<Field> undermined = f -> f.isUndermine();
		
		do {
			armedMines = fields.stream().filter(undermined).count();
			int random = (int) (Math.random() * fields.size());
			fields.get(random).undermine();
		} while(armedMines < mines);
	}
	
	public boolean goalReached() {
		return fields.stream().allMatch(f -> f.goalReached());
	}
	
	public void restart() {
		fields.stream().forEach(f -> f.restart());
		raffleMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		for(int l = 0; l < lines; l++) {
			for(int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
