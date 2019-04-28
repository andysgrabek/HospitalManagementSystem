package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.in.progress.hospitalmanagement.model.SearchQuery;
import work.in.progress.hospitalmanagement.service.SearchQueryService;

@Component
public class SearchQueryStringConverter extends StringConverter<SearchQuery> {

    private final SearchQueryService searchQueryService;

    @Autowired
    public SearchQueryStringConverter(SearchQueryService searchQueryService) {
        this.searchQueryService = searchQueryService;
    }

    @Override
    public String toString(SearchQuery object) {
        return object.getLabel();
    }

    @Override
    public SearchQuery fromString(String string) {
        return searchQueryService.findAll()
                .stream().filter(obj -> obj.getLabel().equals(string)).findFirst().orElse(null);
    }

}
