package services;

import data.model.AccessCode;
import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.LoginServiceRequestDTO;
import dtos.request.ResidentServicesRequestDTO;
import dtos.request.VisitorInformationRequestDTO;
import dtos.responses.LoginServiceResponseDTO;
import dtos.responses.ResidentServicesResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ResidentServicesImplTest {

    private ResidentRepository residentRepository;
    private ResidentServices residentServices;
    private ResidentServicesRequestDTO registerRequest;
    private LoginServiceRequestDTO loginRequest;

    @BeforeEach
    public void setUp() {
        residentRepository = new Residents();
        residentServices = new ResidentServicesImpl(residentRepository);
        registerRequest = new ResidentServicesRequestDTO();
        loginRequest = new LoginServiceRequestDTO();
        Residents.clear();
    }

    @Test
    void testRegisterOneResidents_countShouldBeOne() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");
        residentServices.register(registerRequest);

        assertEquals(1, residentRepository.count());
    }

    @Test
    void testRegisterMultipleResidents_countShouldBeTwo_residentRegistered() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0701112233");
        residentServices.register(registerRequest);
        assertEquals(1, residentRepository.count());

        ResidentServicesRequestDTO registerRequest2 = new ResidentServicesRequestDTO();
        registerRequest2.setFullName("MD Empire");
        registerRequest2.setHomeAddress("Lagos");
        registerRequest2.setPhoneNumber("0704445566");
        residentServices.register(registerRequest2);

        assertEquals(2, residentRepository.count());
    }

    @Test
    void testRegisterOneResidents_countShouldBeOne_giveResponseToResident() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");

        ResidentServicesResponseDTO response = residentServices.register(registerRequest);

        assertEquals(1, response.getId());
        assertEquals("Moses Idowu", response.getFullName());
        assertEquals("0704445566", response.getPhoneNumber());
        assertEquals(1, residentRepository.count());
    }


    @Test
    public void testResidentRegistrationAndLogin() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Nairobi");
        registerRequest.setPhoneNumber("0721234567");

        Resident resident = new Resident();
        resident.setFullName(registerRequest.getFullName());
        resident.setHomeAddress(registerRequest.getHomeAddress());
        resident.setPhoneNumber(registerRequest.getPhoneNumber());
        residentRepository.save(resident);
        assertEquals(1, resident.getId());

        LoginServiceRequestDTO loginRequest = new LoginServiceRequestDTO();
        loginRequest.setId(1);
        loginRequest.setPhoneNumber("0721234567");
        residentRepository.findById(loginRequest.getId());
        residentRepository.findResidentByPhoneNumber(loginRequest.getPhoneNumber());
        LoginServiceResponseDTO loginResponse = residentServices.login(loginRequest);
        assertEquals("Login successful", loginResponse.getMessage());
    }

    @Test
    void testLoginWithInvalidId_shouldReturnError() {
        loginRequest.setId(9999);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            residentServices.login(loginRequest);
        });
        assertEquals("Invalid Id or Phone Number", exception.getMessage());
    }

    @Test
    void testResidentCanRegister_withDifferentPhoneNumber() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");
        residentServices.register(registerRequest);

        ResidentServicesRequestDTO registerRequest2 = new ResidentServicesRequestDTO();
        registerRequest2.setFullName("Moses Idowu");
        registerRequest2.setHomeAddress("Lagos");
        registerRequest2.setPhoneNumber("0704445568");
        residentServices.register(registerRequest2);
    }

    @Test
    void testResidentCannotRegister_withSamePhoneNumber() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");
        residentServices.register(registerRequest);

        ResidentServicesRequestDTO registerRequest2 = new ResidentServicesRequestDTO();
        registerRequest2.setFullName("Moses Idowu");
        registerRequest2.setHomeAddress("Lagos");
        registerRequest2.setPhoneNumber("0704445566");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            residentServices.register(registerRequest2);
        });
        assertEquals("Phone number already exist", exception.getMessage());
    }

    @Test
    public void testRegister_loginResident_withInvalidResidentId_andPhoneNumber_throwException() {
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");
        residentServices.register(registerRequest);
        assertEquals(1, residentRepository.count());

        Resident resident = residentRepository.findResidentByPhoneNumber("0704445566");
        assertNotNull(resident);
        Long residentId = resident.getId();

        LoginServiceRequestDTO loginRequest = new LoginServiceRequestDTO();
        loginRequest.setId(2);
        loginRequest.setPhoneNumber("10704445566");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            residentServices.login(loginRequest);
        });
        assertEquals("Invalid Id or Phone Number", exception.getMessage());
    }

    @Test
    void testRegisterResident_loginResident_residentInviteVisitor_residentGenerateAccessToken(){
        registerRequest.setFullName("Moses Idowu");
        registerRequest.setHomeAddress("Lagos");
        registerRequest.setPhoneNumber("0704445566");
        residentServices.register(registerRequest);
        assertEquals(1, residentRepository.count());

        Resident resident = residentRepository.findResidentByPhoneNumber("0704445566");
        assertNotNull(resident);
        Long residentId = resident.getId();

        LoginServiceRequestDTO loginRequest = new LoginServiceRequestDTO();
        loginRequest.setId(residentId);
        loginRequest.setPhoneNumber(resident.getPhoneNumber());
        LoginServiceResponseDTO response = residentServices.login(loginRequest);
        assertEquals("Login successful", response.getMessage());

        VisitorInformationRequestDTO visitorRequest = new VisitorInformationRequestDTO();
        visitorRequest.setFullName("Majek Olamilekan");
        visitorRequest.setPhoneNumber("0704445523");
        visitorRequest.setHomeAddress("Lagos");

        AccessCode token = residentServices.inviteVisitorAndGenerateToken(residentId, visitorRequest);
        assertNotNull(token);
        assertEquals("Majek Olamilekan", token.getVisitor().getFullName());
        assertEquals("0704445523", token.getVisitor().getPhoneNumber());
        assertEquals("Lagos", token.getVisitor().getHomeAddress());
        assertEquals(residentId, token.getResident().getId());
    }


}
