package model;

import java.util.List;
import java.util.ArrayList;

public class Field {
	private final int line;
	private final int column;
	
	private boolean open;
	private boolean undermined;
	private boolean marked;
	
	private List<Field> neighbor = new ArrayList<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
}
