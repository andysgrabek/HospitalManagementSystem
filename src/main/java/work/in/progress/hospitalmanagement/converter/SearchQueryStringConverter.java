package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import work.in.progress.hospitalmanagement.model.SearchQuery;

import java.util.List;

@AllArgsConstructor
public class SearchQueryStringConverter extends StringConverter<SearchQuery> {

    private List<SearchQuery> list;

    @Override
    public String toString(SearchQuery object) {
        return object.getExpression();
    }

    @Override
    public SearchQuery fromString(String string) {
        return list.stream().filter(obj -> obj.getLabel().equals(string)).findFirst().orElse(null);
    }

}
