package dev.patika.vetclinic.core.config.modelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService { // This interface is used for ModelMapperService class.
    ModelMapper forRequest();
    ModelMapper forResponse();
}
