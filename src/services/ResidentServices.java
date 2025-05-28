package services;

import data.model.AccessCode;
import dtos.request.LoginServiceRequestDTO;
import dtos.request.ResidentServicesRequestDTO;
import dtos.request.VisitorInformationRequestDTO;
import dtos.responses.LoginServiceResponseDTO;
import dtos.responses.ResidentServicesResponseDTO;

public interface ResidentServices {

        ResidentServicesResponseDTO register(ResidentServicesRequestDTO residentServicesRequest);
        LoginServiceResponseDTO login(LoginServiceRequestDTO loginServiceRequest);
        AccessCode inviteVisitorAndGenerateToken(Long residentId, VisitorInformationRequestDTO visitorRequest);

}
