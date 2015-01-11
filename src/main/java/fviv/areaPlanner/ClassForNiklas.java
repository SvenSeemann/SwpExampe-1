package fviv.areaPlanner;

import org.springframework.beans.factory.annotation.Autowired;

import fviv.areaPlanner.Coords.Type;

// Unbedingt umbenennen, mir fiel kein besserer name und ich wollte die Klasse weder Penis, noch Fuck noch sonst irgendwas dummes nennen
public class ClassForNiklas {
	public String name;
	private PlanningRepository planningRepository;
	private Iterable<Coords> area;
	private Iterable<Coords> Stages;
	private Coords bigStages;
	private Coords mediumStages;
	private Coords smallStages;
	private Coords toilets;
	private Coords behToilets;
	private Coords baths;
	private Coords foodstand;
	private Coords tables;
	private Coords bins;
	private Iterable<Coords> campings;

	public ClassForNiklas() {
	}

	@Autowired
	public ClassForNiklas(PlanningRepository planningRepository, String name) {
		this.planningRepository = planningRepository;
		this.name = name;
		this.area = planningRepository.findByType(Type.AREA);
		this.bigStages = planningRepository.findByName("grosse Buehne");
		this.mediumStages = planningRepository.findByName("mittlere Buehne");
		this.smallStages = planningRepository.findByName("kleine Buehne");
		this.Stages = planningRepository.findByType(Type.STAGE);
		this.toilets = planningRepository.findByName("WC");
		this.behToilets = planningRepository.findByName("Beh WC");
		this.baths = planningRepository.findByName("Bad");
		this.foodstand = planningRepository.findByName("Bierwagen");
		this.tables = planningRepository.findByName("Essplatz");
		this.bins = planningRepository.findByName("Muell");
		this.campings = planningRepository.findByType(Type.CAMPING);
	}

	@Override
	public String toString() {
		return "ClassForNiklas [name=" + name + ", planningRepository="
				+ planningRepository + ", area=" + area + ", Stages=" + Stages
				+ ", bigStages=" + bigStages + ", mediumStages=" + mediumStages
				+ ", smallStages=" + smallStages + ", toilets=" + toilets
				+ ", behToilets=" + behToilets + ", baths=" + baths
				+ ", foodstand=" + foodstand + ", tables=" + tables + ", bins="
				+ bins + ", campings=" + campings + "]";
	}
}
