package data.repository;

import data.model.Visitor;

import java.util.List;

public interface VisitorRepository {

    Visitor save(Visitor visitor);
    Visitor getVisitorByName(String name);
    List<Visitor> getVisitors();
    long count();

}
