package data.repository;
import data.model.Resident;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface ResidentRepository {

    Resident save(Resident resident);
    void deleteById(long id);
    Optional<Resident> findById(long id);
    List<Resident> findByFullName(String fullName);
    List<Resident> findAll();
    long count();
    boolean existsById(long id);
    Resident findResidentByPhoneNumber(String name);
    boolean checkPassword(long password);
    boolean confirmPhoneNumber(String phoneNumber);
}
