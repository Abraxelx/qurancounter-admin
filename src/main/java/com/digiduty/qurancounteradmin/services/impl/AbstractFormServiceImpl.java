package com.digiduty.qurancounteradmin.services.impl;

import com.digiduty.qurancounteradmin.forms.ItemForm;
import com.digiduty.qurancounteradmin.services.AbstractFormService;
import com.digiduty.qurancounteradmin.util.IObjectUtil;
import com.digiduty.qurancounteradmin.util.YDateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AbstractFormServiceImpl<T extends ItemForm> implements AbstractFormService<T> {
    static Logger logger = Logger.getLogger(AbstractFormServiceImpl.class.getName());
    @Override
    public List<T> readExcelForAllTypes(final byte[] bytes, final String fileResources, final T entity) {
        try {
            final Workbook workbook = createWorkbookFromExcelFile(bytes, fileResources);
            return fillObjectDataFromWorkbook(entity, workbook);
        } catch (Exception e) {
           logger.log(Level.SEVERE, e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    protected List<T> fillObjectDataFromWorkbook(T entity, Workbook workbook) throws InstantiationException, IllegalAccessException, IOException {
        final List<T> objectList = new ArrayList<>();
        final DataFormatter dataFormatter = new DataFormatter();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            final Sheet sheet = workbook.getSheetAt(i);
            for (Row row : sheet) {
                final Object object = entity.getClass().newInstance();
                final List<Field> fields = new ArrayList<>();
                fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
                for (Cell cell : row) {
                    setGenericFieldValues(dataFormatter, sheet, object, fields, cell);
                }
                if (!IObjectUtil.areAllFieldsNullOrEmpty(object)) {
                    objectList.add((T) object);
                }
            }
            workbook.close();
        }
        return objectList;
    }

    protected static void setCellDateValue(Object object, Cell cell, Field field, String cellValue) throws IllegalAccessException {
        if (cell.getCellType() == CellType.NUMERIC) {
            Date cellDateValue = cell.getDateCellValue();
            if (cellDateValue == null) {
                cellDateValue = YDateUtil.stringToddMMyyyy(cellValue);
                field.set(object, cellDateValue);
            } else {
                cellDateValue = YDateUtil.stringToddMMyyyyForwardSlash(cellValue);
                field.set(object, cellDateValue);
            }
        }
    }

    protected static Workbook createWorkbookFromExcelFile(byte[] bytes, String fileResources) throws IOException {
        final String rootPath = System.getProperty("catalina.home");
        final File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists()) dir.mkdirs();
        final File serverFile = new File(dir.getAbsolutePath() + File.separator + fileResources);
        final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        final Workbook workbook = WorkbookFactory.create(new File(serverFile.getPath()));
        return workbook;
    }

    protected static void setGenericFieldValues(DataFormatter dataFormatter, Sheet sheet, Object object, List<Field> fields, Cell cell) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            if (StringUtils.equalsIgnoreCase(field.getName(), (sheet.getRow(0) != null && sheet.getRow(0).getCell(cell.getColumnIndex()) != null) ? sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue() : "")) {
                if (!StringUtils.contains(field.getName(), dataFormatter.formatCellValue(cell).trim())) {
                    switch (field.getType().getName()) {
                        case "java.util.Date":
                            setCellDateValue(object, cell, field, dataFormatter.formatCellValue(cell).trim());
                            break;
                        case "java.lang.String":
                            field.set(object, dataFormatter.formatCellValue(cell).trim());
                            break;
                        case "java.lang.Double":
                            field.set(object, StringUtils.isNotEmpty(dataFormatter.formatCellValue(cell).trim()) ? Double.parseDouble(dataFormatter.formatCellValue(cell).trim().trim().replace(",", ".")) : null);
                            break;
                        case "java.lang.Integer", "int":
                            field.setInt(object, Integer.parseInt(dataFormatter.formatCellValue(cell).trim().trim()));
                            break;
                        case "java.lang.Boolean":
                            field.set(object, BooleanUtils.toBoolean(dataFormatter.formatCellValue(cell).trim()));
                            break;
                    }
                }
            }
        }
    }
}
