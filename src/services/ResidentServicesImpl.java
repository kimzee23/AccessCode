package services;

import data.model.AccessCode;
import data.repository.*;
import dtos.request.LoginServiceRequestDTO;
import dtos.request.ResidentServicesRequestDTO;
import dtos.request.VisitorInformationRequestDTO;
import dtos.responses.LoginServiceResponseDTO;
import dtos.responses.ResidentServicesResponseDTO;

import static utils.Mapper.*;

public class ResidentServicesImpl implements ResidentServices {

    private ResidentRepository residentRepository;
    private AccessCodeService accessTokenService;
    private VisitorRepository visitorRepository;


    public ResidentServicesImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
        this.accessTokenService = new AccessCodeImpl();
        this.visitorRepository = new Visitors();
    }

    @Override
    public ResidentServicesResponseDTO register(ResidentServicesRequestDTO residentServicesRequest) {
        return map(residentServicesRequest);
    }

    @Override
    public LoginServiceResponseDTO login(LoginServiceRequestDTO request) {
        return loginMap(request);
    }

    @Override
    public AccessCode inviteVisitorAndGenerateToken(Long residentId, VisitorInformationRequestDTO visitorRequest) {
        return map(residentId, visitorRequest);
    }



}
