package com.rocketseat.certification_nlw.modules.questions.controllers;

import com.rocketseat.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.rocketseat.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.rocketseat.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.rocketseat.certification_nlw.modules.questions.entities.QuestionsEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology){

        var result = this.questionRepository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionDTO(question)).collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDTO mapQuestionDTO(QuestionsEntity question){
        var questionResultDTO = QuestionResultDTO.builder().id(question.getId()).technology(question.getTechnology())
                .description(question.getDescription()).build();

        List<AlternativesResultDTO> alternativesResultDTOS = question.getAlternativesEntities()
                .stream().map(alternative -> mapAlternativeDTO(alternative)).collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTOS);
        return questionResultDTO;
    }

    static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternativesResultDTO){
        return AlternativesResultDTO.builder().id(alternativesResultDTO.getId())
                .description(alternativesResultDTO.getDescription()).build();
    }
}
