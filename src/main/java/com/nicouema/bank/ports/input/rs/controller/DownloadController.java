package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.domain.model.BankStatement;
import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.usecase.AccountService;
import com.nicouema.bank.domain.usecase.BankStatementService;
import com.nicouema.bank.domain.usecase.ClientService;
import com.nicouema.bank.domain.usecase.FileService;
import com.nicouema.bank.ports.input.rs.api.DownloadApi;
import com.nicouema.bank.ports.input.rs.mapper.AccountControllerMapper;
import com.nicouema.bank.ports.input.rs.mapper.BankStatementControllerMapper;
import com.nicouema.bank.ports.input.rs.mapper.ClientControllerMapper;
import com.nicouema.bank.ports.input.rs.response.AccountDownloadResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementDownloadResponse;
import com.nicouema.bank.ports.input.rs.response.ClientDownloadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.DOWNLOAD_URI;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping(DOWNLOAD_URI)
@RequiredArgsConstructor
public class DownloadController implements DownloadApi {

    private final FileService fileService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final BankStatementService statementService;

    private final ClientControllerMapper clientMapper;
    private final AccountControllerMapper accountMapper;
    private final BankStatementControllerMapper statementMapper;

    @Override
    @GetMapping("/clients")
    public ResponseEntity<Resource> downloadClientsCsv() throws IOException, IllegalAccessException {
        List<Client> list = clientService.getAllClients();
        List<ClientDownloadResponse> response = clientMapper.clientListToClientDownloadResponseList(list);
        File file = fileService.createFileCsv(Client.class, response);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, String.format("attachment; filename=\"" + file.getName() + "\""));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Override
    @GetMapping("/accounts")
    public ResponseEntity<Resource> downloadAccountsCsv() throws IOException, IllegalAccessException {
        List<Account> list = accountService.getAllAccounts();
        List<AccountDownloadResponse> response = accountMapper.accountListToAccountDownloadResponse(list);
        File file = fileService.createFileCsv(Account.class, response);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, String.format("attachment; filename=\"" + file.getName() + "\""));

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Override
    @GetMapping("/statements")
    public ResponseEntity<Resource> downloadStatementsCsv() throws IOException, IllegalAccessException {
        List<BankStatement> list = statementService.getAllBankStatements();
        List<BankStatementDownloadResponse> response = statementMapper.bankStatementListToBankStatementDownloadResponseList(list);
        File file = fileService.createFileCsv(BankStatement.class, response);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, String.format("attachment; filename=\"" + file.getName() + "\""));

        return ResponseEntity.ok().headers(headers).body(resource);
    }


}
