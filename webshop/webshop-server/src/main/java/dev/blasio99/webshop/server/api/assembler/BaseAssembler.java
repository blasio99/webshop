package dev.blasio99.webshop.server.api.assembler;

import java.util.ArrayList;
import java.util.List;

import dev.blasio99.webshop.common.dto.BaseDTO;
import dev.blasio99.webshop.server.model.BaseModel;


public interface BaseAssembler<D extends BaseDTO, M extends BaseModel> {
    
    M createModel(D dto);
    D createDTO(M model);

    public default List<M> createModelList(List<D> dtoList) {
        List<M> models = new ArrayList<>(dtoList.size());
        for (D dto : dtoList) {
            models.add(createModel(dto));
        }
        return models;
    }

    public default List<D> createDTOList(List<M> models) {
        List<D> dtoList = new ArrayList<>(models.size());
        for (M model : models) {
            dtoList.add(createDTO(model));
        }
        return dtoList;
    }

}