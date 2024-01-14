package com.digiduty.qurancounteradmin.controllers;

import com.digiduty.qurancounteradmin.dto.SearchGenericDTO;
import com.digiduty.qurancounteradmin.services.AbstractService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractController<T, ID extends Serializable> {
    private AbstractService<T, ID> service;

    public AbstractController(AbstractService<T, ID> service) {
        this.service = service;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody T save(@RequestBody T entity) {
        T result = service.save(entity);
        return result;
    }

    @RequestMapping(value = "saveAll", method = RequestMethod.POST)
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        Iterable<S> result = service.saveAll(entities);
        return result;
    }

    @RequestMapping(value = "findOne/{entityId}", method = RequestMethod.GET)
    public T findOne(@PathVariable ID entityId) {
        return service.findOne(entityId);
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public @ResponseBody List<T> findAll() {
        return (List<T>) service.findAll();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody void delete(@RequestBody T entity) {
        service.delete(entity);
    }

    public static String quote(String s) {
        return new StringBuilder().append('\'').append(s).append('\'').toString();
    }

    public void searchEntities(final HttpServletRequest request, SearchGenericDTO searchGenericDTO, final Model model, final RedirectAttributes ra) {
        int page = 0;
        int size = 10;
        model.addAttribute("selectedAttributes", searchGenericDTO.getSelectedAttributes());
        model.addAttribute("attributeValues", searchGenericDTO.getAttributeValues());
        model.addAttribute("filterTypes", searchGenericDTO.getFilterTypes());
        model.addAttribute("logicalOperator", searchGenericDTO.getLogicalOperator());
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) != 0 ? Integer.parseInt(request.getParameter("page")) - 1 : 0;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        if (CollectionUtils.isEmpty(searchGenericDTO.getSelectedAttributes())) {
            ra.addFlashAttribute("message", "You have to select one column");
            model.addAttribute("paginatedSearchValues", service.findPaginated(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))));
            return;
        }
        model.addAttribute("paginatedSearchValues", service.searchEntity(searchGenericDTO, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))));
    }
}
