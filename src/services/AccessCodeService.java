package services;

import data.model.AccessCode;
import data.model.Visitor;

public interface AccessCodeService {

    AccessCode generateAccessToken(Visitor visitor);

}
