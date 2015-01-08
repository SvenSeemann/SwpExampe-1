package fviv.areaPlanner;

public class ClassForNiklas {
	public String name;
	private Iterable<Coords> elements;
	private Iterable<Coords> area;
	private Iterable<Coords> bigStages;
	private Iterable<Coords> mediumStages;
	private Iterable<Coords> smallStages;
	private Iterable<Coords> toilets;
	private Iterable<Coords> behToilets;
	private Iterable<Coords> baths;
	private Iterable<Coords> foodstand;
	private Iterable<Coords> tables;
	private Iterable<Coords> bins;
	private Iterable<Coords> campings;

	public ClassForNiklas() {

	}

	public ClassForNiklas(Iterable<Coords> elements, String name) {
		this.elements = elements;
		this.name = name;
		
		
	}
	
}
