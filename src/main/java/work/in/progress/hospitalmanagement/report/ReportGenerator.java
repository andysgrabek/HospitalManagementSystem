package work.in.progress.hospitalmanagement.report;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Defines common interface for all reports that can be generated in the system.
 *
 * @param <T> the entry type for which a report is generated
 * @author jablonskiba
 */
public interface ReportGenerator<T> {

    /**
     * Returns a report representation located in the default path {@see createDefaultPath}.
     *
     * @param entries the report records
     * @return the representation of file and directory pathname
     * @throws IOException       produced by failed or interrupted I/O operations
     * @throws DocumentException when a document isn't open yet, or has been closed
     */
    File generate(Collection<T> entries) throws IOException, DocumentException;

    /**
     * Additionally to {@link ReportGenerator#generate(Collection)} allows default location overwriting.
     *
     * @param path the custom path used to locate a file
     */
    File generate(Collection<T> entries, String path) throws IOException, DocumentException;

    /**
     * @return default path used to locate a file
     */
    String createDefaultPath();

}
