package work.in.progress.hospitalmanagement.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.Patient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Provides a {@link ReportGenerator} implementation using iText {@see com.itextpdf} library
 *
 * @author jablonskiba
 */
@Component
public class PatientReportGenerator implements ReportGenerator<Patient> {

    private static final int COLUMNS_NUMBER = 6;
    private static final int TEXT_LEFT_INDENTATION = 50;
    private static final int IMAGE_SCALE_FACTOR = 100;

    private static final String DEFAULT_NAME = "Patient_Participation_List";
    private static final String IMAGE_PATH = "images/logo_transparent2.png";

    private static final DateTimeFormatter FILE_NAME_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmm").withLocale(Locale.ENGLISH);
    private static final DateTimeFormatter TITLE_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM y, H:mm:ss").withLocale(Locale.ENGLISH);

    /**
     * Creates the report name with appended date in <code>yyyyMMddHHmm</code> format.
     *
     * @return the default report location
     */
    @Override
    public String createDefaultPath() {
        return String.format("%s_%s.pdf", DEFAULT_NAME, FILE_NAME_DATE_FORMATTER.format(LocalDateTime.now()));
    }

    @Override
    public File generate(Collection<Patient> entries) throws IOException, DocumentException {
        return generate(entries, createDefaultPath());
    }

    @Override
    public File generate(Collection<Patient> entries, String path) throws IOException, DocumentException {
        /* Document doesn't implement AutoClosable */
        final Document document = new Document();
        try {
            final File file = new File(path);
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            addContent(document, entries);
            document.addCreationDate();
            document.close();

            return file;
        } finally {
            document.close();
        }
    }

    private void addContent(Document document, Collection<Patient> entries) throws DocumentException, IOException {
        final PdfPTable table = new PdfPTable(COLUMNS_NUMBER);

        addTitleImage(document);
        addDescription(document);
        addTableHeader(table);
        entries.forEach(patient -> addTableRow(table, patient));

        document.add(table);
    }

    private void addDescription(Document document) throws DocumentException {
        final Paragraph title = new Paragraph(String.format(
                "Patient report: %s", TITLE_DATE_FORMATTER.format(LocalDateTime.now())));

        title.setIndentationLeft(TEXT_LEFT_INDENTATION);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addTitleImage(Document document) throws DocumentException, IOException {
        final Image image = Image.getInstance(
                new ClassPathResource(IMAGE_PATH).getFile().getAbsolutePath());

        image.scalePercent(((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * IMAGE_SCALE_FACTOR);
        document.add(image);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Surname", "Birth date", "Alive", "Department")
                .forEach(columnTitle -> {
                    final PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTableRow(PdfPTable table, Patient patient) {
        table.addCell(String.valueOf(patient.getId()));
        table.addCell(patient.getName());
        table.addCell(patient.getSurname());
        table.addCell(patient.getBirthDate().toString());
        table.addCell(patient.isAlive() ? "Yes" : "No");
        table.addCell(patient.getAdmission()
                .map(admission -> admission.getDepartment().getName())
                .orElseGet(() -> "None"));
    }

}
