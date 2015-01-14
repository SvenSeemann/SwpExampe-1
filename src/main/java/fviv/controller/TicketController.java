package fviv.controller;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.location.Location;
import fviv.location.LocationRepository;
import fviv.ticket.TicketRepository;
import fviv.ticket.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 */
@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class TicketController {
	private final TicketRepository ticketRepository;
	private final FestivalRepository festivalRepository;
	private final LocationRepository locationRepository;

	private static long ticketid;
	private String mode = "ticket";
	private static Festival festival;
	private static Location location;
	private String asdf = "";
	@Autowired
	public TicketController(TicketRepository ticketRepository,
			FestivalRepository festivalRepository, 
			LocationRepository locationRepository) {
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
		this.locationRepository = locationRepository;
	}

	@ModelAttribute("ticketmode")
	public String ticketmode() {
		return mode;
	}

	/**
	 * index method and mapping the festival and ticketlist on the weppage
	 * @param modelMap
	 * @return
	 */
	@RequestMapping({ "/ticket" })
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("festivallist", festivalRepository.findAll());
		modelMap.addAttribute("ticketlist", ticketRepository.findAll());
		modelMap.addAttribute("eroor", asdf);

		return "ticket";
	}

	@RequestMapping(value = "/changeMode", method = RequestMethod.POST)
	public String changeMod() {
		mode = "ticket";
		return "redirect:/ticket";
	}

	@RequestMapping({ "/ticketPruefen" })
	public String ticketpruefen() {
		mode = "ticketpruefen";
		return "redirect:/ticket";
	}
	/**
	 * method to controle the ticket if it is already checked 
	 * returns a modelmap attribute
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pruefeTicket", method = RequestMethod.POST)
	// ticketeinchecken methode
	public String pruefeTicket(ModelMap modelMap,
			@RequestParam("numbers") long id) {
		modelMap.addAttribute("ticketlist", ticketRepository.findAll());

		Ticket ticketkontrolle = ticketRepository.findById(id);
		if (ticketRepository.findById(id) == null) {

			return "ticket";
		}
		if (ticketkontrolle.getChecked() == true) {
			modelMap.addAttribute("forhtml", id + "already checked in!");
			ticketRepository.save(ticketkontrolle);

			return "ticket";
		} else {
			ticketkontrolle.setChecked(true);
			modelMap.addAttribute("forhtml", id + "now checked in");
			ticketRepository.save(ticketkontrolle);

			return "ticket";
		}
	}
	/**
	 * loads festivals from the festival list and gets start and end date 
	 * calculates the differenz and creates a list where each date from a festival can be choosen
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/loadfestivalTicket", method = RequestMethod.POST)
	public String loadingFestival(ModelMap modelMap,
			@RequestParam("festivalId") long id) {
		Festival loadingfestival = festivalRepository.findById(id);
		LocalDate startDate = loadingfestival.getStartDatum();
		LocalDate endDate = loadingfestival.getEndDatum();
		DateTime startDatum = DateTime.parse(startDate.toString()); //hadtobedone
		ticketid = id;
		DateTime endDatum = DateTime.parse(endDate.toString()); //hadtobedone
		String[] dateArray;
		int days = Days.daysBetween(startDatum, endDatum).getDays();
		dateArray = new String[days];
		LocalDate hilfsDate = startDate;

		for (int i = 0; i < days; i++) {
			dateArray[i] = hilfsDate.toString();
			hilfsDate = hilfsDate.plusDays(1);
		}
		modelMap.addAttribute("ticketdates", dateArray);
		modelMap.addAttribute("festivallist", festivalRepository.findAll());
		asdf="";
		return "ticket";

	}
	/**
	 * creates new ticket
	 * @param ticketart
	 * @param numbers
	 * @param tagesdate
	 * @return
	 * @throws IOException
	 * @throws BarcodeException
	 */
	@RequestMapping(value = "/newTicket", method = RequestMethod.POST)
	public String newTicket(ModelMap modelMap, @RequestParam("ticketart") boolean ticketart,
			@RequestParam("numbers") String numbers,
			@RequestParam("hilfsDate") String tagesdate) throws IOException,
			BarcodeException {
		long id = ticketid;
		int anzahl;
		if (numbers == "") {
			anzahl = 1;
		} else {
			anzahl = Integer.parseInt(numbers);
		}
		Long longId = id;
		for (int i = 1; i <= anzahl; i++) {
			// Create Ticket
			festival = festivalRepository.findById(longId);
			location = locationRepository.findById(festival.getLocationId());
			String festivalname = festival.getFestivalName();
			long preistag = festival.getPreisTag();
			LocalDate date = null;
			if (ticketart == true) {
				DateTimeFormatter formatter = DateTimeFormatter
						.ofPattern("yyyy-LL-dd");
				date = LocalDate.parse(tagesdate, formatter);
			}
			int max = location.getMaxVisitors();
			List<Ticket> festivalnamelist = ticketRepository
					.findByFestivalName(festival.getFestivalName());
			List<Ticket> datumlist = ticketRepository.findByTagesticketdate(date);
			List<Ticket> datum2list = ticketRepository.findByTagesticketdate(null);

			Collection<Ticket> listone = new ArrayList<Ticket>(festivalnamelist);
			Collection<Ticket> listthree = new ArrayList<Ticket>(datumlist);
			Collection<Ticket> listfourth = new ArrayList<Ticket>(datum2list);
			listthree.addAll(listfourth);
			listone.retainAll(listthree);
			int ticketzahl=listone.size();
			if (max > ticketzahl){
			Ticket ticket = new Ticket(ticketart, false, festivalname, date); // Eins
	
			if (ticketart == true) {
				ticketRepository.save(ticket);
				setTicketid(ticket.getId());
				pdfvorlagebearbeiten(preistag, ticketart, date);
				barcodegen();
				addbarcode();
			} else {
				ticketRepository.save(ticket);
				setTicketid(ticket.getId());
				pdfvorlagebearbeiten(preistag * 7 / 3, ticketart, date);
				barcodegen();
				addbarcode();

			}
			} else {
				asdf = "NICHT GENÜGEND PLÄTZE VERFÜGBAR";
				modelMap.addAttribute("eroor", asdf);

			}
		}
		return "redirect:/ticket";
	}

	// true = tagesticket
	// false == 3tagesticket
	/**
	 * turns ticketart(bool) into a String which is used to creates the pdf - ticket - docuement
	 * @param ticketart
	 * @param date
	 * @return
	 */
	public static String ticketarthelper(boolean ticketart, LocalDate date) {
		if (ticketart == true) {
			return "Tagesticket am " + date;
		} else
			return "3-Tagesticket";
	}
	/**
	 * checks if the input number is aviable to print and if it is it prints the ticket
	 * @param ticketnmr
	 * @return
	 */
	@RequestMapping({ "/ticketDrucken" })
	public String ticketDrucken(
			@RequestParam(value = "ticketnummer", required = false) String ticketnmr) {
		asdf ="";
		if (ticketnmr == "") {
			ticketnmr = "" + ticketid;
		} else {
			long ticketnummer = Long.parseLong(ticketnmr);

			if (ticketnummer > ticketid) {
				return "ticket";
			} else {
				if (ticketnummer > 0) {
					return "redirect:/" + festival.getFestivalName()
							+ ticketnummer + ".pdf";
				} else
					return "redirect:/" + festival.getFestivalName()
							+ ticketnummer + ".pdf";
			}
		}
		return "redirect:/" + festival.getFestivalName() + ticketid + ".pdf";
	}
	/**
	 * edits the pdf document
	 * @param ticketkosten
	 * @param ticketart
	 * @param date
	 * @throws IOException
	 * @throws BarcodeException
	 */
	public static void pdfvorlagebearbeiten(float ticketkosten,
			boolean ticketart, LocalDate date) throws IOException, BarcodeException {
		String price = "" + ticketkosten + "Euro";
		try {

			// (1) Einlesen der PDF-Vorlage
			PdfReader reader = new PdfReader("test_file.pdf");

			// (2) Ziel-PDF bestimmen
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"change.pdf"));

			// (3) Text-Felder holen
			AcroFields acroFields = stamper.getAcroFields();

			// (4) Felder bearbeiten

			acroFields.setField("ticketart", ticketarthelper(ticketart, date));
			acroFields.setField("eventname", festival.getFestivalName());
			acroFields.setField("number1", ticketid + "");
			acroFields.setField("number2", ticketid + "");
			acroFields.setField("actors", festival.getActors());
			
			acroFields.setField("adressofvenue", location.getAdresse());
			acroFields.setField("date", datumshelper(date));
			acroFields.setField("price", price);
			acroFields.setField("eventnamesmall", festival.getFestivalName());
			acroFields.setField("datesmall", datumshelper(date) );

			// (5) Dokumente schließen
			stamper.close();
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		barcodegen();
		addbarcode();
	}
	/**
	 * date into string 
	 * @param date
	 * @return
	 */
	public static String datumshelper( LocalDate date){
		if (date == null){
			return festival.getStartDatum() + "";
		}
		
		return date.toString();
	}
	/**
	 * creates a barcode from the saved festivalname and ticketid
	 * saves the barcode as a png file
	 * @throws IOException
	 * @throws BarcodeException
	 */
	public static void barcodegen() throws IOException, BarcodeException {
		// get a Barcode from the BarcodeFactory 
		Barcode barcode = BarcodeFactory.createCode128B(festival
				.getFestivalName() + ticketid); 
		try {
			File f = new File("out.png");

			// Let the barcode image handler do the hard work
			BarcodeImageHandler.savePNG(barcode, f);
		} catch (Exception e) {
			// Error handling here
		}
	}
	/**
	 * adds barcode(png) into the pdf of the ticket
	 */
	public static void addbarcode() {
		String name;
		name = festival.getFestivalName() + ticketid;
		try {
			PdfReader pdfReader = new PdfReader("change.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("src/main/webapp/" + name + ".pdf"));

			Image image = Image.getInstance("out.png");

			PdfContentByte content = pdfStamper.getUnderContent(1);

			image.setAbsolutePosition(530f, 655f);
			image.setRotationDegrees(90f);
			image.scalePercent(50f);

			content.addImage(image);

			pdfStamper.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();

		}
	}

	public static long getTicketid() {
		return ticketid;
	}

	public static void setTicketid(long ticketid) {
		TicketController.ticketid = ticketid;
	}

	public LocationRepository getLocationRepository() {
		return locationRepository;
	}

}
