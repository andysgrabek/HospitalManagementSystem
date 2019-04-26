package work.in.progress.hospitalmanagement.converter;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;
import work.in.progress.hospitalmanagement.controller.AdvancedSearchViewController.Query;

import java.util.List;

@AllArgsConstructor
public class QueryStringConverter extends StringConverter<Query> {//TODO: change type to true query type

    private List<Query> list;

    @Override
    public String toString(Query object) {
        return object.getQueryString();
    }

    @Override
    public Query fromString(String string) {
        return list.stream().filter(obj -> obj.getQueryLabel().equals(string)).findFirst().orElse(null);
    }
}
