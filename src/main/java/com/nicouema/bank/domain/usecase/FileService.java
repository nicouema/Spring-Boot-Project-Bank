package com.nicouema.bank.domain.usecase;


import java.io.File;
import java.util.List;

public interface FileService {

    File createFileCsv(Class<?> c, List<?> objects) throws IllegalAccessException;
}
