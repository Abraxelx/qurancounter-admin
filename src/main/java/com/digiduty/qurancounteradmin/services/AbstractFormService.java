package com.digiduty.qurancounteradmin.services;

import com.digiduty.qurancounteradmin.forms.ItemForm;

import java.util.List;

public interface AbstractFormService<T extends ItemForm> {
    List<T> readExcelForAllTypes(final byte[] bytes, final String fileResources, final T entity);
}
