package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.BankStatement;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementDownloadResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BankStatementControllerMapper {

    @Mapping(target = "movementType.id", source = "movementType")
    BankStatement bankStatementRequestToBankStatement(BankStatementRequest bankStatementRequest);

    BankStatementResponse bankStatementToBankStatementResponse(BankStatement bankStatement);

    List<BankStatementResponse> bankStatementListToBankStatementListResponse(List<BankStatement> bankStatements);

    @Mapping(target = "movementType", source = "movementType.name")
    @Mapping(target = "clientName", source = "account.client_.name")
    @Mapping(target = "clientLastname", source = "account.client_.lastname")
    BankStatementDownloadResponse bankStatementToBankStatementDownloadResponse(BankStatement statement);

    List<BankStatementDownloadResponse> bankStatementListToBankStatementDownloadResponseList(List<BankStatement> statements);
}
