package fviv.areaPlanner;

public class ClassForNiklas {
	public String name;
	private Iterable<AreaItem> elements;
	private Iterable<AreaItem> area;
	private Iterable<AreaItem> bigStages;
	private Iterable<AreaItem> mediumStages;
	private Iterable<AreaItem> smallStages;
	private Iterable<AreaItem> toilets;
	private Iterable<AreaItem> behToilets;
	private Iterable<AreaItem> baths;
	private Iterable<AreaItem> foodstand;
	private Iterable<AreaItem> tables;
	private Iterable<AreaItem> bins;
	private Iterable<AreaItem> campings;

	public ClassForNiklas() {

	}

	public ClassForNiklas(Iterable<AreaItem> elements, String name) {
		this.elements = elements;
		this.name = name;
		
		
	}
	
}
