package com.register.login.shared.mapper;

import java.util.List;

public interface EntityToDto <E,O>{

    E toEntity(O o);
    List<E> toEntity(List<O> o);

    O toDto(E o);
    List<O> toDto(List<E> o);

}
