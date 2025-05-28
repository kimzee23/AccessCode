package data.repository;

import data.model.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Visitors implements VisitorRepository {

    private List<Visitor> visitors = new ArrayList<>();

    @Override
    public Visitor save(Visitor visitor) {
        visitors.add(visitor);
        return visitor;
    }

    @Override
    public Visitor getVisitorByName(String name) {
        for (Visitor visitor : visitors) {
            if (visitor.getFullName().equals(name)) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public List<Visitor> getVisitors() {
        return visitors;
    }

    @Override
    public long count() {
        return visitors.size();
    }
}
