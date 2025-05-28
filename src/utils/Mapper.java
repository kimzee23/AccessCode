package utils;

import data.model.AccessCode;
import data.model.Resident;
import data.model.Visitor;
import data.repository.*;
import dtos.request.LoginServiceRequestDTO;
import dtos.request.ResidentServicesRequestDTO;
import dtos.request.VisitorInformationRequestDTO;
import dtos.responses.LoginServiceResponseDTO;
import dtos.responses.ResidentServicesResponseDTO;
import services.AccessCodeImpl;
import services.AccessCodeService;

import java.util.Optional;

public class Mapper {

    private static ResidentRepository residentRepository = new Residents();
    private static VisitorRepository visitorRepository = new Visitors();
    private static AccessCodeService accessTokenService = new AccessCodeImpl();

    public static ResidentServicesResponseDTO map(ResidentServicesRequestDTO request) {
        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setHomeAddress(request.getHomeAddress());
        resident.setPhoneNumber(request.getPhoneNumber());

        verifyPhoneNumber(request);
        return response(resident);
    }

    private static ResidentServicesResponseDTO response (Resident resident) {
        Resident registerResident = residentRepository.save(resident);

        ResidentServicesResponseDTO response = new ResidentServicesResponseDTO();
        response.setId(registerResident.getId());
        response.setFullName(registerResident.getFullName());
        response.setPhoneNumber(registerResident.getPhoneNumber());

        return response;
    }


    public static LoginServiceResponseDTO loginMap(LoginServiceRequestDTO loginServiceRequest) {
        Optional<Resident>confirmResidentID = residentRepository.findById(loginServiceRequest.getId());
        Resident confirmResidentPhoneNumber = residentRepository.findResidentByPhoneNumber(loginServiceRequest.getPhoneNumber());

        if (confirmResidentID.isEmpty() || confirmResidentPhoneNumber == null) {
            throw new RuntimeException("Invalid Id or Phone Number");
        }
        LoginServiceResponseDTO response = new LoginServiceResponseDTO();
        response.setMessage("Login successful");
        return response;
    }


    public static AccessCode map(Long residentId, VisitorInformationRequestDTO visitorRequest) {
        Resident resident = validateResidentId(residentId);

        Visitor visitor = new Visitor();
        visitor.setFullName(visitorRequest.getFullName());
        visitor.setPhoneNumber(visitorRequest.getPhoneNumber());
        visitor.setHomeAddress(visitorRequest.getHomeAddress());
        visitorRepository.save(visitor);

        AccessCode token = accessTokenService.generateAccessToken(visitor);
        token.setResident(resident);
        token.setVisitor(visitor);
        token.setOtpCode(token.getOtpCode());
        token.setOtpCreatedOn(token.getOtpCreatedOn());
        token.setOtpExpiredDate(visitorRequest.getOtpExpiryDate());

        return token;
    }

    private static Resident validateResidentId(Long residentId) {
        return residentRepository.findById(residentId)
               .orElseThrow(() -> new IllegalArgumentException("Resident not found"));
    }

    private static void verifyPhoneNumber(ResidentServicesRequestDTO residentServicesRequest) {
        boolean phoneNumberExist = residentRepository.confirmPhoneNumber(residentServicesRequest.getPhoneNumber());
        if (phoneNumberExist) throw new IllegalArgumentException("Phone number already exist");
    }

}
