package com.digiduty.qurancounteradmin.services.impl;

import com.digiduty.qurancounteradmin.dao.CustomGenericDao;
import com.digiduty.qurancounteradmin.dto.SearchGenericDTO;
import com.digiduty.qurancounteradmin.services.AbstractService;
import com.digiduty.qurancounteradmin.util.YDateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractServiceImpl<T, Long extends Serializable> implements AbstractService<T, Long> {
    CustomGenericDao<T, Long> customGenericDao;

    public AbstractServiceImpl(CustomGenericDao<T, Long> customGenericDao) {
        this.customGenericDao = customGenericDao;
    }

    @Override
    public T save(T entity) {
        T result = customGenericDao.save(entity);
        return result;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> result = customGenericDao.saveAll(entities);
        return result;
    }

    @Override
    public void delete(T entity) {
        customGenericDao.delete(entity);
    }

    @Override
    public T findOne(Long entityId) {
        Optional<T> byId = customGenericDao.findById(entityId);
        return byId.get();
    }

    @Override
    public List<T> findAll() {
        List<T> result = customGenericDao.findAll();
        return result;
    }

    @Override
    public Page<T> findPaginated(PageRequest of) {
        Page<T> result = customGenericDao.findAll(of);
        return result;
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return customGenericDao.findAll(spec, pageable);
    }

    @Override
    public List<T> readExcelForAllTypes(final byte[] bytes, final String fileResources, final T entity) {
        try {
            final Workbook workbook = createWorkbookFromExcelFile(bytes, fileResources);
            return fillObjectDataFromWorkbook(entity, workbook);
        } catch (Exception e) {
            e.printStackTrace();
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
                objectList.add((T) object);
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
                        case "java.lang.Integer":
                            field.setInt(object, Integer.parseInt(dataFormatter.formatCellValue(cell).trim().trim()));
                            break;
                        case "int":
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

    @Override
    public Page<T> searchEntity(final SearchGenericDTO searchGenericDTO, Pageable pageable) {
        List<Specification<T>> specifications = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(searchGenericDTO.getAttributeValues())) {
            for (int i = 0; i < searchGenericDTO.getSelectedAttributes().size(); i++) {
                String attribute = searchGenericDTO.getSelectedAttributes().get(i);
                String attributeValue = searchGenericDTO.getAttributeValues().get(i);
                String filterType = searchGenericDTO.getFilterTypes().get(i);
                Specification<T> attributeSpecification = createAttributeSpecification(attribute, attributeValue, filterType);
                specifications.add(attributeSpecification);
            }
        } else {
            for (int i = 0; i < searchGenericDTO.getSelectedAttributes().size(); i++) {
                String attribute = searchGenericDTO.getSelectedAttributes().get(i);
                String filterType = searchGenericDTO.getFilterTypes().get(i);
                Specification<T> attributeSpecification = createAttributeSpecification(attribute, "", filterType);
                specifications.add(attributeSpecification);
            }
        }
        Specification<T> combinedSpecification = specifications.get(0);
        if (searchGenericDTO.getLogicalOperator().equalsIgnoreCase("or")) {
            for (int i = 1; i < specifications.size(); i++) {
                combinedSpecification = Specification.where(combinedSpecification).or(specifications.get(i));
            }
        } else {
            for (int i = 1; i < specifications.size(); i++) {
                combinedSpecification = Specification.where(combinedSpecification).and(specifications.get(i));
            }
        }
        return customGenericDao.findAll(combinedSpecification, pageable);
    }

    private Specification<T> createAttributeSpecification(String attribute, String attributeValue, String filterType) {
        return (root, query, criteriaBuilder) -> {
            switch (filterType.toLowerCase()) {
                case "equals":
                    return criteriaBuilder.equal(root.get(attribute), attributeValue);
                case "contains":
                    return criteriaBuilder.like(root.get(attribute), "%" + attributeValue + "%");
                case "startswith":
                    return criteriaBuilder.like(root.get(attribute), attributeValue + "%");
                case "endswith":
                    return criteriaBuilder.like(root.get(attribute), "%" + attributeValue);
                case "isempty":
                    return criteriaBuilder.or(criteriaBuilder.isNull(root.get(attribute)), criteriaBuilder.equal(root.get(attribute), ""));
                case "isnotempty":
                    return criteriaBuilder.and(criteriaBuilder.isNotNull(root.get(attribute)), criteriaBuilder.notEqual(root.get(attribute), ""));
                case "notcontains":
                    return criteriaBuilder.notLike(root.get(attribute), "%" + attributeValue + "%");
                default:
                    return null;
            }
        };
    }
}
