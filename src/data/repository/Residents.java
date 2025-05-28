package data.repository;

import data.model.Resident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Residents implements ResidentRepository {

    private static int residentCounter = 0;
    private static List<Resident> residents = new ArrayList<>();

    @Override
    public Resident save(Resident resident) {
        if(isNew(resident)) saveNew(resident); else update(resident);

        return resident;
    }

    private void saveNew(Resident resident) {
        resident.setId(generateId());
        residents.add(resident);
    }

    private boolean isNew(Resident resident) {
        return resident.getId() == 0;
    }

    private void update(Resident resident) {
        for (int count = 0; count < residents.size(); count++) {
            if (residents.get(count).getId() == resident.getId()) {
                residents.set(count, resident);
                return;
            }
        }
    }


    private long generateId() {
        return ++residentCounter;
    }

    @Override
    public void deleteById(long id) {
        for (Resident resident : residents) {
            if (resident.getId() == id) {
                residents.remove(resident);
                return;
            }
        }
    }

    @Override
    public Optional<Resident> findById(long id) {
        return residents.stream()
                .filter(resident -> resident.getId() == id)
                .findFirst();
    }

    @Override
    public List<Resident> findByFullName(String fullName) {
        return residents.stream()
                .filter(resident -> resident.getFullName().equals(fullName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Resident> findAll() {
        return residents;
    }

    @Override
    public long count() {
        return residents.size();
    }

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    @Override
    public Resident findResidentByPhoneNumber(String phoneNumber) {
        for (Resident resident : residents) {
            if (resident.getPhoneNumber().equals(phoneNumber)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public boolean checkPassword(long password) {
        for (Resident resident : residents) {
            if (resident.getId() == password) {
                resident.setId(password);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean confirmPhoneNumber(String phoneNumber) {
        for (Resident resident : residents) {
            if (resident.getPhoneNumber().contains(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    public static void clear() {
        residents.clear();
        residentCounter = 0;
    }
}
