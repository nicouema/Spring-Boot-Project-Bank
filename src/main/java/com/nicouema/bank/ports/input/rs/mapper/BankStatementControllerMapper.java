package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.BankStatement;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BankStatementControllerMapper {

    BankStatement bankStatementRequestToBankStatement(BankStatementRequest bankStatementRequest);

    BankStatementResponse bankStatementToBankStatementResponse(BankStatement bankStatement);

    List<BankStatementResponse> bankStatementListToBankStatementListResponse(List<BankStatement> bankStatements);
}
