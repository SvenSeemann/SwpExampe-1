package planning;

import planning.Coords.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AreaController {
	private PlanningRepository planningRepository;

	@Autowired
	public AreaController(PlanningRepository planningRepository) {
		this.planningRepository = planningRepository;
	}

	@RequestMapping("/")
	public String getHtml(Model modelMap) {
		modelMap.addAttribute("getCoords", planningRepository);
		return "planer";
	}

	@ModelAttribute("sizeArea")
	public String sizeArea() {
		Coords siize = planningRepository.findByName("Areal");
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width: " + siize.getWidth() + "px;" + "height:"
					+ siize.getHeight() + "px;";
		} else {
			return "width: 0px;" + "height: 0px";
		}
	}

	@ModelAttribute("sizeStage")
	public String sizeStage() {
		Coords stage = planningRepository.findByName("Stage");
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width:" + stage.getWidth() + "px;" 
					+ "height:"+ stage.getHeight() + "px;" 
					+ "left:" + stage.getxPos()+ "px;" 
					+ "top:" + stage.getyPos() + "px;";
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeCamping")
	public String sizeCamping() {
		Coords camp = planningRepository.findByName("Camping");
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width:" + camp.getWidth() + "px;" + "height:"
					+ camp.getHeight() + "px;" + "left:" + camp.getxPos()
					+ "px;" + "top:" + camp.getyPos() + "px;";
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeCatering")
	public String sizeCatering() {
		Coords cate = planningRepository.findByName("Catering");
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width:" + cate.getWidth() + "px;" + "height:"
					+ cate.getHeight() + "px;" + "left:" + cate.getxPos()
					+ "px;" + "top:" + cate.getyPos() + "px;";
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeToilet")
	public String sizeToilet() {
		Coords wc = planningRepository.findByName("Toilet");
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width:" + wc.getWidth() + "px;" + "height:"
					+ wc.getHeight() + "px;" + "left:" + wc.getxPos()
					+ "px;" + "top:" + wc.getyPos() + "px;";
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST)
	public String neuesAreal(@RequestParam("width") int width,
			@RequestParam("height") int height) {
		if (planningRepository.findByName("Areal") == null) {
			planningRepository.save(new Coords("Areal", width, height, 0, 0,
					Type.AREA));
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/newToilet", method = RequestMethod.POST)
	public String neueToilette(@RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			int i = 1;
			while (planningRepository.findByName("Toilet" + i) != null) {
				i++;
			}
			planningRepository.save(new Coords("Toilet" + i, 200, 200, left,
					top, Type.TOILET));
		}
		Coords klo = planningRepository.findByName("Toilet1");
		System.out.println("weite: " + klo.getWidth() + "hoehe: "
				+ klo.getHeight());
		System.out.println("x-Achse: " + klo.getxPos() + "y-achse: "
				+ klo.getyPos());
		return "redirect:/";
	}

	@RequestMapping(value = "/newStage", method = RequestMethod.POST)
	public String neueBuehne(@RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			planningRepository.save(new Coords("Buehne", 300, 150, left, top,
					Type.STAGE));
		}
		Coords stage = planningRepository.findByName("Stage");
		System.out.println("weite: " + stage.getWidth() + "hoehe: "
				+ stage.getHeight());
		System.out.println("x-Achse: " + stage.getxPos() + "y-achse: "
				+ stage.getyPos());
		return "redirect:/";
	}

	@RequestMapping(value = "/newCamping", method = RequestMethod.POST)
	public String neuesCamping(@RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			planningRepository.save(new Coords("Camping", 600, 600, left, top,
					Type.CAMPING));
		}
		Coords stage = planningRepository.findByName("Camping");
		System.out.println("weite: " + stage.getWidth() + "hoehe: "
				+ stage.getHeight());
		System.out.println("x-Achse: " + stage.getxPos() + "y-achse: "
				+ stage.getyPos());
		return "redirect:/";
	}

	@RequestMapping(value = "/newCatering", method = RequestMethod.POST)
	public String neuesCatering(@RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			planningRepository.save(new Coords("Catering", 400, 400, left, top,
					Type.CATERING));
		}
		Coords eat = planningRepository.findByName("Catering");
		System.out.println("weite: " + eat.getWidth() + "hoehe: "
				+ eat.getHeight());
		System.out.println("x-Achse: " + eat.getxPos() + "y-achse: "
				+ eat.getyPos());
		return "redirect:/";
	}
}
