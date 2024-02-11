package com.digiduty.qurancounteradmin.controllers;

import com.digiduty.qurancounteradmin.dto.SearchGenericDTO;
import com.digiduty.qurancounteradmin.elasticsearch.entity.QuestionAnswer;
import com.digiduty.qurancounteradmin.elasticsearch.service.QuestionAnswerElasticService;
import com.digiduty.qurancounteradmin.forms.FileForm;
import com.digiduty.qurancounteradmin.forms.QuestionAnswerForm;
import com.digiduty.qurancounteradmin.model.QuestionAnswerModel;
import com.digiduty.qurancounteradmin.populators.QuestionAnswerPopulator;
import com.digiduty.qurancounteradmin.services.AbstractFormService;
import com.digiduty.qurancounteradmin.services.QuestionAnswerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/questionAnswer")
public class QuestionAnswerController extends AbstractController<QuestionAnswerModel, Long> {
    private final QuestionAnswerService questionAnswerService;

    private final QuestionAnswerElasticService questionAnswerElasticService;

    @Autowired
    public QuestionAnswerController(QuestionAnswerService service, QuestionAnswerElasticService questionAnswerElasticService, QuestionAnswerPopulator questionAnswerPopulator, Validator validator, AbstractFormService<QuestionAnswerForm> abstractFormService) {
        super(service);
        this.questionAnswerService = service;
        this.questionAnswerElasticService = questionAnswerElasticService;
        this.questionAnswerPopulator = questionAnswerPopulator;
        this.validator = validator;
        this.abstractFormService = abstractFormService;
    }

    private final QuestionAnswerPopulator questionAnswerPopulator;
    private final Validator validator;
    private final AbstractFormService<QuestionAnswerForm> abstractFormService;

    @ModelAttribute("entityProperties")
    public List<String> populateEntityProperties() {
        return Arrays.asList("seoUrl", "readCount", "createdDate", "updateDate");
    }

    @RequestMapping
    public ModelAndView list(Model model) {
        model.addAttribute("fileForm", new FileForm());
        return new ModelAndView("questionAnswer");
    }

    @RequestMapping(value = "/createQuestionAnswerPage", method = RequestMethod.GET)
    public String questionAnswerView(ModelMap model) {
        model.addAttribute("questionAnswer", new QuestionAnswerForm());
        return "create/questionAnswerAddView";
    }

    @PostMapping(value = "/importQuestionAnswer")
    public String bulkDataUpload(@ModelAttribute(value = "fileForm") FileForm fileForm, ModelMap model, RedirectAttributes ra) {
        try {
            final List<QuestionAnswerForm> target = abstractFormService.readExcelForAllTypes(fileForm.getFile().getBytes(), fileForm.getFile().getOriginalFilename(), new QuestionAnswerForm());
            for (QuestionAnswerForm targetForm : target) {
                Set<ConstraintViolation<QuestionAnswerForm>> violations = validator.validate(targetForm);
                if (!violations.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<QuestionAnswerForm> constraintViolation : violations) {
                        sb.append(constraintViolation.getMessage());
                    }
                    throw new ConstraintViolationException("Error occurred: " + sb, violations);
                }
            }
            if (CollectionUtils.isEmpty(target)) {
                model.addAttribute("fileForm", new FileForm());
                ra.addFlashAttribute("message", "Empty Excel Data" + fileForm.getFile().getOriginalFilename() + "! ");
                return "redirect:/admin/questionAnswer/questionAnswerAll";
            }
            target.removeIf(ptp -> ptp.getQuestion() == null);
            target.removeIf(ptp -> ptp.getAnswer() == null);
            target.removeIf(ptp -> ptp.getSeoUrl() == null);
            target.removeIf(ptp -> ptp.getSources() == null);
            final List<QuestionAnswerModel> newTargets = new ArrayList<QuestionAnswerModel>();
            questionAnswerPopulator.reversePopulateAll(target, newTargets);
            questionAnswerService.saveAll(newTargets);
            model.addAttribute("fileForm", new FileForm());
            ra.addFlashAttribute("message", "Bulk Upload Finished Successfully..");
            return "redirect:/admin/questionAnswer/questionAnswerAll";
        } catch (Exception e) {
            model.addAttribute("fileForm", new FileForm());
            ra.addFlashAttribute("message", "Bulk Upload Has Errors Please Check Logs...." + e.getMessage());
        }
        return "redirect:/admin/questionAnswer/questionAnswerAll";
    }

    @RequestMapping(value = "/wizard", method = RequestMethod.GET)
    public String showWizardForm(Model model) {
        model.addAttribute(new QuestionAnswerForm());
        return "wizard/questionAnswerWizardAddMainView";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute(value = "questionAnswer") QuestionAnswerForm questionAnswer, BindingResult result, RedirectAttributes ra, ModelMap model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("questionAnswer", questionAnswer);
                return "create/questionAnswerAddView";
            }
            final QuestionAnswerModel target = new QuestionAnswerModel();
            questionAnswerPopulator.reversePopulate(questionAnswer, target);
            questionAnswerService.save(target);
            ra.addFlashAttribute("message", "Başarılı..");
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Başarısız yöneticinize başvurun....");
        }
        return "redirect:/admin/questionAnswer/questionAnswerAll";
    }

    @RequestMapping(value = "/openEditQuestionAnswerPage/{id}", method = RequestMethod.GET)
    public String getEditQuestionAnswerView(@PathVariable Long id, final @RequestParam(required = false) String listAllSearchQueryForm, ModelMap model) {
        final QuestionAnswerModel questionAnswer = questionAnswerService.findOne(id);
        final QuestionAnswerForm target = new QuestionAnswerForm();
        questionAnswerPopulator.populate(questionAnswer, target);
        target.setListAllSearchQueryForm(listAllSearchQueryForm);
        model.addAttribute("activeTab", "basicInfo");
        model.addAttribute("questionAnswer", target);
        return "update/questionAnswerEditView";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute(value = "questionAnswer") QuestionAnswerForm questionAnswer, BindingResult result, RedirectAttributes ra, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("activeTab", "basicInfo");
            model.addAttribute("questionAnswer", questionAnswer);
            return "update/questionAnswerEditView";
        }
        final QuestionAnswerModel target = new QuestionAnswerModel();
        questionAnswerPopulator.reversePopulate(questionAnswer, target);
        questionAnswerService.save(target);
        return "redirect:/admin/questionAnswer/questionAnswerAll";
    }

    @RequestMapping(value = "/removeQuestionAnswer/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            QuestionAnswerModel questionAnswer = questionAnswerService.findOne(id);
            if (questionAnswer != null) questionAnswerService.delete(questionAnswer);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/startFullIndexQuestionAnswerIndex", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> startQuestionAnswerIndex() {

        try {

            List<QuestionAnswerModel> source = questionAnswerService.findAll();

            questionAnswerElasticService.deleteAll();

            for (QuestionAnswerModel sourceForm : source) {

                QuestionAnswer questionAnswer = new QuestionAnswer();
                questionAnswer.setQuestion(sourceForm.getQuestion());
                questionAnswer.setSources(sourceForm.getSources());
                questionAnswer.setSummary(sourceForm.getSummary());
                questionAnswer.setCreatedDate(sourceForm.getCreatedDate().toInstant());
                questionAnswer.setUpdateDate(sourceForm.getUpdateDate().toInstant());
                questionAnswer.setAnswer(sourceForm.getAnswer());
                questionAnswer.setSeoUrl(sourceForm.getSeoUrl());
                questionAnswer.setReadCount(sourceForm.getReadCount());
                questionAnswerElasticService.save(questionAnswer);
            }

            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/questionAnswerAll", method = RequestMethod.GET)
    public String searchAll(HttpServletRequest request, ModelMap model) {
        int page = 0;
        int size = 10;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("fileForm", new FileForm());
        model.addAttribute("paginatedSearchValues", questionAnswerService.findPaginated(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))));
        return "questionAnswer";
    }

    @RequestMapping(value = "/searchAllGeneric", method = RequestMethod.GET)
    public String customSearchEntities(final HttpServletRequest request, @RequestParam(value = "selectedAttribute", required = false) List<String> selectedAttributes, @RequestParam(value = "attributeValues", required = false) List<String> attributeValues, @RequestParam(value = "filterTypes", required = false) List<String> filterTypes, @RequestParam(value = "logicalOperator", required = false) String logicalOperator, final Model model, final RedirectAttributes ra) {
        final SearchGenericDTO searchGenericDTO = new SearchGenericDTO(selectedAttributes, attributeValues, filterTypes, logicalOperator);
        super.searchEntities(request, searchGenericDTO, model, ra);
        model.addAttribute("fileForm", new FileForm());
        return "questionAnswer";
    }
}
