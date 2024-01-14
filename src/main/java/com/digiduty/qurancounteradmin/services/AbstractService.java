package com.digiduty.qurancounteradmin.services;

import com.digiduty.qurancounteradmin.dto.SearchGenericDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AbstractService<T, Long> {
    T save(T entity);

    void delete(T entity);

    List<T> findAll();

    T findOne(Long entityId);

    Page<T> findPaginated(PageRequest of);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> readExcelForAllTypes(final byte[] bytes, final String fileResources, final T entity);

    Page<T> searchEntity(SearchGenericDTO searchGenericDTO, Pageable pageable);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
}
