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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.ticket.TicketRepository;
import fviv.ticket.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class TicketController {
	private final TicketRepository ticketRepository;
	private final FestivalRepository festivalRepository;
	private static long ticketid;
	private String mode = "ticket";
	private static Festival festival;

	@Autowired
	public TicketController(TicketRepository ticketRepository,
			FestivalRepository festivalRepository) {
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
	}

	@ModelAttribute("ticketmode")
	public String ticketmode() {
		return mode;
	}

	@RequestMapping({ "/ticket" })
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("festivallist", festivalRepository.findAll());
		modelMap.addAttribute("ticketlist", ticketRepository.findAll());

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

	@RequestMapping(value = "/newTicket", method = RequestMethod.POST)
	public String newTicket(@RequestParam("ticketart") boolean ticketart,
			@RequestParam("festivalId") String id,
			@RequestParam("numbers") String numbers) throws IOException,
			BarcodeException {
		int anzahl;
 if (numbers == ""){
	 anzahl = 1;
 } else {
		 anzahl = Integer.parseInt(numbers);
		 }
		Long longId = Long.parseLong(id);
		for (int i = 1; i <= anzahl; i++) {
			// Create Ticket
			festival = festivalRepository.findById(longId);
			String festivalname = festival.getFestivalName();
			long preistag = festival.getPreisTag();

			Ticket ticket = new Ticket(ticketart, false, festivalname); // Eins
																		// ist
																		// gleich
			// Tagesticket //
			// Null
			// ist gleich
			// 3Tagesticket
			if (ticketart == true) {
				ticketRepository.save(ticket);
				setTicketid(ticket.getId());
				pdfvorlagebearbeiten(preistag, ticketart);
				barcodegen();
				addbarcode();
			} else {
				ticketRepository.save(ticket);
				setTicketid(ticket.getId());
				pdfvorlagebearbeiten(preistag * 7 / 3, ticketart);
				barcodegen();
				addbarcode();

			}
		}
		return "redirect:/ticket";
	}

	// true = tagesticket
	// false == 3tagesticket

	public static String ticketarthelper(boolean ticketart) {
		if (ticketart == true) {
			return "Tagesticket";
		} else
			return "3-Tagesticket";
	}

	@RequestMapping({ "/ticketDrucken" })
	public String ticketDrucken(
			@RequestParam(value = "ticketnummer", required = false) String ticketnmr) {
		if (ticketnmr == "") {
			ticketnmr = "" + ticketid;
		} else {
			long ticketnummer = Long.parseLong(ticketnmr);

			if (ticketnummer > ticketid) {
				return "ticket";
			} else {
				if (ticketnummer > 0) {
					return "redirect:/ticket" + festival.getFestivalName()
							+ ticketnummer + ".pdf";
				} else
					return "redirect:/ticket" + festival.getFestivalName()
							+ ticketnummer + ".pdf";
			}
		}
		return "redirect:/" + festival.getFestivalName() + ticketid + ".pdf";
	}

	public static void pdfvorlagebearbeiten(float ticketkosten,
			boolean ticketart) throws IOException, BarcodeException {
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

			acroFields.setField("ticketart", ticketarthelper(ticketart));
			acroFields.setField("eventname", festival.getFestivalName());
			acroFields.setField("number1", ticketid + "");
			acroFields.setField("number2", ticketid + "");
			acroFields.setField("actors", festival.getActors());
			acroFields.setField("adressofvenue", festival.getLocation());
			acroFields.setField("date", festival.getStartDatum() + "");
			acroFields.setField("price", price);
			acroFields.setField("eventnamesmall", festival.getFestivalName());
			acroFields.setField("datesmall", festival.getStartDatum() + "");

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

	public static void barcodegen() throws IOException, BarcodeException {
		// get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createCode128B(festival
				.getFestivalName() + ticketid); // hier
		// der
		// code
		// später
		// rein
		try {
			File f = new File("out.png");

			// Let the barcode image handler do the hard work
			BarcodeImageHandler.savePNG(barcode, f);
		} catch (Exception e) {
			// Error handling here
		}
	}

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

}
