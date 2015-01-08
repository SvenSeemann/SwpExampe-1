package fviv.controller;

import java.util.LinkedList;
import java.util.List;

import fviv.areaPlanner.*;
import fviv.areaPlanner.Coords.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Maximilian Schwarze
 */

@PreAuthorize("hasRole('ROLE_BOSS')")
@Controller
public class AreaController {
	private PlanningRepository planningRepository;

	private String htmlHelper(int width, int height, int left, int top){
		return "width:" + width + "px;" + "height:"
				+ height + "px;" + "left:" + left
				+ "px;" + "top:" + top + "px;";
	}
	
	@Autowired
	public AreaController(PlanningRepository planningRepository) {
		this.planningRepository = planningRepository;
	}

	@RequestMapping("/planning")
	public String getHtml(Model modelMap) {
		//List<String> toiletStrings = new LinkedList<String>();
		//for(Coords toilet : planningRepository.findByType(Type.TOILET)){
		//	toiletStrings.add(htmlHelper(toilet.getWidth(), toilet.getHeight(), toilet.getxPos(),toilet.getyPos()));
		//}
		modelMap.addAttribute("getCoords", planningRepository);
		//modelMap.addAttribute("toilets", toiletStrings );
		
		return "planning";
	}
	/*@ModelAttribute("sizeArea")
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
		System.out.println("Stage: "+stage);
		if (stage != null) {
			return htmlHelper(stage.getWidth(), stage.getHeight(), stage.getxPos(), stage.getyPos());
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeCamping")
	public String sizeCamping() {
		Coords campi = planningRepository.findByName("Camping");
		if (campi != null) {
			return htmlHelper( campi.getWidth(), campi.getHeight(), campi.getxPos(), campi.getyPos());
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeCatering")
	public String sizeCatering() {
		Coords cate = planningRepository.findByName("Catering");
		if (cate != null) {
			return htmlHelper(cate.getWidth(), cate.getHeight(), cate.getxPos(), cate.getyPos());
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}

	@ModelAttribute("sizeToilet")
	public String sizeToilet(ModelMap modelMap) {
		Coords wc = planningRepository.findByName("Toilet");
		if (wc != null) {
			return htmlHelper(wc.getWidth(), wc.getHeight(), wc.getxPos(), wc.getyPos());
		} else {
			return "width: 0px;" + "height: 0px;" + "left: 0px;" + "top: 0px";
		}
	}*/

	/*@RequestMapping(value = "/newArea", method = RequestMethod.POST)
	public String neuesAreal(@RequestParam("width") int width,
			@RequestParam("height") int height) {
		if (planningRepository.findByName("Areal") == null) {
			planningRepository.save(new Coords("Areal", width, height, 0, 0,
					Type.AREA));
		}
		Coords area = planningRepository.findByName("Areal");
		System.out.println("weite: " + area.getWidth() + "hoehe: "
				+ area.getHeight());
		return "planning";
	}
/*
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
		return "redirect:/planning";
	}

	@RequestMapping(value = "/newStage", method = RequestMethod.POST)
	public String neueBuehne(@RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			planningRepository.save(new Coords("Stage", 300, 150, left, top,
					Type.STAGE));
		}
		Coords stage = planningRepository.findByName("Stage");
		System.out.println("weite: " + stage.getWidth() + "hoehe: "
				+ stage.getHeight());
		System.out.println("x-Achse: " + stage.getxPos() + "y-achse: "
				+ stage.getyPos());
		return "redirect:/planning";
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
		return "redirect:/planning";
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
		return "redirect:/planning";
	}*/
}
