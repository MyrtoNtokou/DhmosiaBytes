package budgetlogic;

import java.io.IOException;
import java.util.List;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

public interface BudgetLoader {

    /**
     * Loads Eggrafi entries from a CSV file.
     *
     * @param path the CSV file path
     * @return list of BasicRecord
     * @throws IOException if file read fails
     */
    List<Eggrafi> loadGeneral(String path) throws IOException;

    /**
     * Loads Ministry entries from a CSV file.
     *
     * @param path the CSV file path
     * @return list of Ministry
     * @throws IOException if file read fails
     */
    List<Ypourgeio> loadMinistries(String path) throws IOException;
}
