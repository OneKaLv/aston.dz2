package by.oneka.aston.mapper.interfaces;

public interface Mapper <T,E>{
    T entityToDTO(E entity);

    E DTOToEntity(T dto);
}
