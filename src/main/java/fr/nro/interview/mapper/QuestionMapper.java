package fr.nro.interview.mapper;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections4.CollectionUtils;

import fr.nro.interview.dto.interview.ItemDTO;
import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.entity.interview.Question;

@ApplicationScoped
public class QuestionMapper implements Function<Question, QuestionDTO>{

  @Override
  public QuestionDTO apply(Question question) {
    QuestionDTO questDto = new QuestionDTO();
    questDto.setId(question.id);
    questDto.setLabel(question.getLabel());
    questDto.setItems(new ArrayList<>(CollectionUtils.emptyIfNull(question.getItems())
        .stream()
        .map(item -> {
          ItemDTO itemDto = new ItemDTO();
          itemDto.setLabel(item.getLabel());
          itemDto.setOrder(item.getOrder());
          itemDto.setReference(item.getFunctionalReference());
          return itemDto;
        }).collect(toList())));
    questDto.setMultiple(question.getMultiple());
    return questDto;
  }

}
