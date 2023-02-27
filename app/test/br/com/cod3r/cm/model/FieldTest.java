package br.com.cod3r.cm.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.exception.ExplosionException;

public class FieldTest {
	private Field field;
	
	@BeforeEach
	void startField() {
		field = new Field(3, 3);
	}

	@Test
	void testNeighborDistance1Left() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Right() {
		Field neighbor = new Field(3, 4);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Up() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance1Bottom() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistance2() {
		Field neighbor = new Field(2, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNotNeighborDistance() {
		Field neighbor = new Field(1, 1);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testDefaultValueMarked() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testHandleMarked() {
		field.handleMarked();
		assertTrue(field.isMarked());
	}
	
	@Test
	void testHandleReverseMarked() {
		field.handleMarked();
		field.handleMarked();
		assertFalse(field.isMarked());
	}
	
	@Test
	void testOpenNotUnderminedNotMarked() {
		assertTrue(field.open());
	}
	
	@Test
	void testOpenNotUnderminedMarked() {
		field.handleMarked();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenUnderminedMarked() {
		field.handleMarked();
		field.undermine();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenUnderminedNotMarked() {
		field.undermine();
		
		assertThrows(ExplosionException.class, () -> {
			field.open();
		});
	}
	
	@Test
	void testOpenWithNeighbors1() {
		Field field11 = new Field(1, 1);
		Field field22 = new Field(2, 2);
		
		field22.addNeighbor(field11);
		field.addNeighbor(field22);
		field.open();
		
		assertTrue(field22.isOpen() && field11.isOpen());
	}
	
	@Test
	void testOpenWithNeighbors2() {
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 1);
		field12.undermine();
		
		Field field22 = new Field(2, 2);
		field22.addNeighbor(field11);
		field22.addNeighbor(field12);
		
		field.addNeighbor(field22);
		field.open();
		
		assertTrue(field22.isOpen() && field11.isClose());
	}
}
